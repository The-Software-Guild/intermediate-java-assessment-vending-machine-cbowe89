package VendingMachine.controller;

import VendingMachine.dao.PersistenceException;
import VendingMachine.dto.Item;
import VendingMachine.service.InsufficientFundsException;
import VendingMachine.service.ItemInventoryException;
import VendingMachine.service.ServiceLayer;
import VendingMachine.ui.View;
import java.math.BigDecimal;
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
    public Controller(View view,
                      ServiceLayer serviceLayer)
            throws PersistenceException {
        // Initialize View and Service
        this.view = view;
        this.serviceLayer = serviceLayer;
    }

    /**
     * Method controls the application when the menu system is displayed
     */
    public void run() throws PersistenceException {
        // Declare and initialize beginning balance of $0.00
        BigDecimal balance = new BigDecimal("0.0").setScale(2, HALF_UP);

        boolean runApplication = true;
        int menuSelection = 0;

        // Display Welcome Banner
        view.displayWelcomeBanner();
        // Display All Items
        view.displayAllItemsBanner();
        List<Item> itemList = serviceLayer.getAllItems();
        view.displayAllItems(itemList);

        try {
            while (runApplication) {
                // Display the menu, update the menu selection from user input
                menuSelection = getMenuSelection(balance);

                switch (menuSelection) {
                    case 1 ->  // Add funds to balance
                            addFunds(balance);
                    case 2 ->  // Buy Items
                            purchaseItems(balance, itemList);
                    case 3 -> { // Quit VendingMachine
                        try {
                            // Display end balance/change due and quit
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
        } catch (PersistenceException | ItemInventoryException
                 | InsufficientFundsException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    /**
     * Method asks the View to display the menu, displays the user's
     * current balance, prompts the user to make a menu selection,
     * and returns the menu selection to the Controller.
     * @param balance User's current balance
     * @return Menu selection
     */
    private int getMenuSelection(BigDecimal balance) {
        return view.printMenuAndGetSelection();
    }

    public void addFunds(BigDecimal balance) {
        //view.addFundsDisplay(balance);
        view.addFundsBanner();
        view.displayBalance(balance);
        BigDecimal updatedBalance =
                balance.add(view.addFundsPrompt()).setScale(2, HALF_UP);
        view.displayBalance(updatedBalance);
    }

    public BigDecimal purchaseItems(BigDecimal balance, List<Item> itemList)
            throws PersistenceException,
            ItemInventoryException,
            InsufficientFundsException {
        boolean buyItem = true;
        int itemSelection = 0;

        view.purchaseItemBanner();

        try {
            while (buyItem) {
                itemSelection = view.getItemSelection(itemList.size());
                Item purchasedItem = itemList.get(itemSelection);

                balance = balance.subtract(purchasedItem.getItemCost());
                view.displayPurchase(purchasedItem, balance);

                int newItemQuantity = purchasedItem.getItemQuantity() - 1;
                serviceLayer.changeInventoryQuantity(purchasedItem, newItemQuantity);
            }
        } catch (ItemInventoryException e) {
            view.displayErrorMessage("ERROR: Item unavailable.");
        } catch (InsufficientFundsException e) {
            view.displayErrorMessage("ERROR: Insufficient funds.");
        }
    }

    /*
    private void dispenseChangeAndQuit(BigDecimal balance) {
    }
     */

    public void quit(BigDecimal balance)
            throws InsufficientFundsException {
        //implement
    }
}
