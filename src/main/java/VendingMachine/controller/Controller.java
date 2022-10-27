package VendingMachine.controller;

import VendingMachine.dao.PersistenceException;
import VendingMachine.dto.Change;
import VendingMachine.dto.Coins;
import VendingMachine.dto.Item;
import VendingMachine.service.InsufficientFundsException;
import VendingMachine.service.ItemInventoryException;
import VendingMachine.service.ServiceLayer;
import VendingMachine.ui.View;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import static java.math.RoundingMode.HALF_UP;

/**
 * The {@code VendingMachineController} class orchestrates the actions
 * of the other components in the application to accomplish the
 * application's goals.
 * It controls the flow of the application, allows for navigation via
 * the menu, and provides methods to interact with the VendingMachine.
 */
public class Controller {

    // Declare VendingMachineView and VendingMachineServiceLayer objects
    private final View view;
    private final ServiceLayer serviceLayer;

    /**
     * Constructor initializes the view and dao objects
     * via dependency injection.
     * @param view VendingMachineView object
     * @param serviceLayer VendingMachineService object
     * @throws PersistenceException Checked exception
     */
    public Controller(View view, ServiceLayer serviceLayer)
            throws PersistenceException {
        // Initialize View and Service
        this.view = view;
        this.serviceLayer = serviceLayer;
    }

    /**
     * Method controls the application
     */
    public void run() throws PersistenceException {
        // Declare and initialize beginning balance of $0.00
        BigDecimal balance =
                new BigDecimal("0.0").setScale(2, HALF_UP);

        // Declare and initialize variables
        boolean runApplication = true;
        int mainMenuSelection = 0;
        List<Item> itemList = serviceLayer.getAllItems(); // List of all items

        // Determine if user wants to use vending machine
        runApplication = startUp(itemList);

        // Run Vending Machine or Exit
        try {
            // While runApplication is true
            while (runApplication) {
                // User must add funds before continuing
                if (balance.equals(new BigDecimal("0.0")))
                    view.addFundsDisplay(balance);

                // Display the menu, update the menu selection from user input
                mainMenuSelection = view.getMainMenuSelection();

                switch (mainMenuSelection) {
                    case 1 ->  // Add funds to balance
                            addFunds(balance);
                    case 2 ->  // Buy Items
                            purchaseItems(balance, itemList);
                    case 3 -> { // Quit VendingMachine
                        try {
                            // Display end balance/change due and exit
                            dispenseChangeAndQuit(balance);
                        } catch (InsufficientFundsException e) {
                            view.displayBalance(balance);
                            view.displayErrorMessage(e.getMessage());
                        }
                        runApplication = false;
                    }
                    default -> // Menu selection is not valid
                            view.displayUnknownCommand();
                }
            }
            view.displayExitMessage();
        } catch (PersistenceException | ItemInventoryException
                 | InsufficientFundsException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private boolean startUp(List<Item> itemList) {
        int startMenuSelection = 0;

        // Display Welcome Banner
        view.displayWelcomeBanner();

        // Display All Items
        view.displayAllItemsBanner();
        view.displayAllItems(itemList);

        // Provide option to continue or exit program
        // Get selection: 1 to use the vending machine, 2 to exit
        startMenuSelection = view.getStartMenuSelection();

        // Return true for 1 (use vending machine)
        // or false for 2 (exit)
        return startMenuSelection == 1;
    }

    private void addFunds(BigDecimal balance) {
        //view.addFundsDisplay(balance);
        view.addFundsBanner();
        view.displayBalance(balance);
        BigDecimal updatedBalance =
                balance.add(view.addFundsPrompt()).setScale(2, HALF_UP);
        view.displayBalance(updatedBalance);
    }

    private void purchaseItems(BigDecimal balance, List<Item> itemList)
            throws PersistenceException,
            ItemInventoryException,
            InsufficientFundsException {
        boolean buyItem = true;
        int itemSelection = 0, buyAnother = 0;

        view.purchaseItemBanner();

        while (buyItem) {
            itemSelection = view.getItemSelection(itemList.size());
            Item purchasedItem = itemList.get(itemSelection - 1);

            balance = balance.subtract(purchasedItem.getItemCost());
            view.purchaseSuccessBanner();
            view.displayPurchase(purchasedItem, balance);

            int newItemQuantity = purchasedItem.getItemQuantity() - 1;
            serviceLayer.changeInventoryQuantity(purchasedItem, newItemQuantity);

            buyAnother = view.getContinueBuyingSelection();

            buyItem = (buyAnother == 1);
        }
    }

    private void dispenseChangeAndQuit(BigDecimal balance)
            throws InsufficientFundsException {
        //implement
        HashMap<Coins, Integer> changeMap = Change.getChange(balance);
        view.printChange(changeMap);
    }
}
