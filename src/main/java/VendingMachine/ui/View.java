package VendingMachine.ui;

import VendingMachine.dto.Coins;
import VendingMachine.dto.Item;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class View {
    // Declare UserIO object
    private UserIO io;

    public View(UserIO io) {
        this.io = io;
    }

    /*
        public int getStartMenuSelection() throws PersistenceException {
        io.print("1. Continue");
        io.print("2. Quit");

        return io.readInt("Please select an option.", 1, 2);
    }
     */

    // Print Main Menu
    public int printMenuAndGetSelection() {
        mainMenuBanner();
        io.print("1. Add Money");
        io.print("2. Purchase Item");
        io.print("3. Quit");

        return io.readInt("Please select an operation.", 1, 3);
    }

    // Add Money
    public void addFundsDisplay(BigDecimal balance) {
        addFundsBanner();
        displayBalance(balance);
        BigDecimal fundsAdded = io.readBigDecimal(
                "Enter funds to add ($0 - $100): ",
                new BigDecimal("0.0"), new BigDecimal("100.0"));
        fundsAddedMessage();
        displayBalance(balance);
        io.readString("Please hit enter to continue.");
    }

    // Display balance
    public void displayBalance(BigDecimal balance) {
        io.print("Current balance: $" +
                balance.setScale(2, RoundingMode.HALF_UP));
    }

    // Get selection for item that user wants to purchase
    public int getItemSelection(int itemListSize) {
        return io.readInt("Please select an item", 1, itemListSize);
    }

    /*
    public void displayAllItems(List<Item> itemList) {
        displayAllItemsBanner();

        int itemNum = 1;

        for (Item i : itemList) {
            io.printf(itemNum, i.getItemName(), i.getItemCost());
            itemNum++;
        }
    }
     */

    // Print all items
    public void displayAllItems(List<Item> itemList) {
        displayAllItemsBanner();
        int j = 1;
        ArrayList<String> list = new ArrayList<>();
        list.add(0,"null");
        for(Item i : itemList) {
            io.print(j + ". " + i.toString());
            list.add(j, i.getItemName());
            j++;
        }
    }


    public void displayPurchase(Item item, BigDecimal balance) {
        io.print("Purchased: " + item.getItemName() +
                "\nCost: " + item.getItemCost() +
                "\nRemaining Balance: " + balance);
    }

    /*
    public int getItemSelection(int itemListSize) {
        return io.readInt("Please select an item", 1, itemListSize);
    }
     */

    // Print change to be returned to user
    public void printChange(HashMap<Coins, Integer> map) {
        for(Coins c : map.keySet()) {
            io.print(c.name() + "($" + c.getValue() + "): " + map.get(c) + "\n");
        }
    }

    public void displayWelcomeBanner() {
        io.print("Welcome to the Vending Machine!");
        io.print("-------------------------------");
    }

    public void displayAllItemsBanner() {
        io.print("===== Vending Machine Items =====");
    }

    // banner and message
    public void mainMenuBanner() {
        io.print("===== MAIN MENU =====");
    }

    public void addFundsBanner() {
        io.print("===== ADD FUNDS =====");
    }

    public BigDecimal addFundsPrompt() {
        return io.readBigDecimal("Enter amount to add to current balance.");
    }

    public void fundsAddedMessage() {
        io.print("Money added! Thank you.");
    }


    public void purchaseItemBanner() {
        io.print("===== PURCHASE ITEM(S) =====");
    }

    // Display successful purchase message
    public void purchaseSuccessBanner() {
        //io.print("Purchase Succeeded!");
        io.print("Item successfully purchased!");
    }

    // Display Exit message
    public void displayExitMessage() {
        io.print("Exiting Vending Machine... Goodbye!!!");
    }

    // Display Unknown Command message
    public void displayUnknownCommand() {
        //io.print("Invalid input. Please input 1, 2 or 3.\n");
        io.print("===== UNKNOWN COMMAND =====");
    }

    // Display Error message
    public void displayErrorMessage(String errorMsg) {
        io.print("===== ERROR =====");
        io.print(errorMsg); // removed + '\n'
        //io.readString("Please hit enter to continue.");
    }
}
