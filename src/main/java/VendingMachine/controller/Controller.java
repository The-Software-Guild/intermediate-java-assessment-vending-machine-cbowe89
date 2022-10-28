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

/**
 * The {@code VendingMachineController} class orchestrates the actions
 * of the other components in the application to accomplish the
 * application's goals.
 * It controls the flow of the application, allows for navigation via
 * the menu, and provides methods to interact with the VendingMachine.
 */
public class Controller {

    // Declare VendingMachineView and VendingMachineServiceLayer objects
    private View view;
    private ServiceLayer serviceLayer;

    public Controller() throws PersistenceException {
        // implement
    }

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
        BigDecimal balance = new BigDecimal(0);

        // Declare and initialize variables
        boolean runApplication = true;
        int startMenuSelection = 0, mainMenuSelection = 0;
        List<Item> itemList = serviceLayer.getAllItems(); // List of all items

        // Display welcome banner and item list
        view.displayWelcomeBanner();
        view.displayAllItems(itemList);

        // Determine if user wants to use vending machine
        startMenuSelection = view.getStartMenuSelection();
        switch (startMenuSelection) {
            case 1 -> view.displayAddFundsReminder();
            case 2 -> runApplication = false;
            default -> view.displayUnknownCommand();
        }

        // Run Vending Machine or Exit
        try {
            // While runApplication is true
            while (runApplication) {
                // Display the menu, update the menu selection from user input
                mainMenuSelection = view.getMainMenuSelection();

                switch (mainMenuSelection) {
                    case 1 ->  // Add funds to balance
                            balance = addFunds(balance);
                    case 2 -> // Buy Items
                        {
                            try {
                                balance = purchaseItems(balance, itemList);
                            } catch (ItemInventoryException | InsufficientFundsException e) {
                                view.displayBalance(balance);
                                view.displayErrorMessage(e.getMessage());
                            }
                        }
                    case 3 -> { // Quit VendingMachine
                        try {
                            // Display end balance/change due and exit
                            quit(balance);
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
        } catch (PersistenceException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private BigDecimal addFunds(BigDecimal balance) {
        return view.addFundsDisplay(balance);
    }

    private BigDecimal purchaseItems(BigDecimal balance, List<Item> itemList)
            throws PersistenceException,
            ItemInventoryException,
            InsufficientFundsException {
        // Declare and initialize variables
        int itemSelection = 0;

        if (balance.compareTo(new BigDecimal(0)) < 1) {
            throw new InsufficientFundsException("Add money to make a purchase!");
        }
        else {
            view.purchaseItemBanner();

            // Re-display items for ease of use
            view.displayAllItems(itemList);

            // Get itemSelection, store as purchasedItem
            itemSelection = view.getItemSelection(itemList.size());
            Item purchasedItem = itemList.get(itemSelection - 1);

            // Sell Item
            balance = serviceLayer.sellItem(balance, purchasedItem);
            purchasedItem = serviceLayer.changeInventoryQuantity(purchasedItem,
                    purchasedItem.getItemQuantity()-1);

            // Display successful purchase info
            view.purchaseSuccessBanner();
            view.displayPurchase(purchasedItem, balance);

            HashMap<Coins, Integer> changeMap = Change.getChange(balance);
            view.printChange(changeMap);
        }

        return balance = new BigDecimal(0);
    }

    private void quit(BigDecimal balance)
            throws InsufficientFundsException {
        // Dispense change before exiting if balance is greater than $0.00
        if (balance.compareTo(new BigDecimal(0)) > 0) {
            HashMap<Coins, Integer> changeMap = Change.getChange(balance);
            view.printChange(changeMap);
        }
    }
}
