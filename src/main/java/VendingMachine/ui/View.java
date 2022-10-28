package VendingMachine.ui;

import VendingMachine.dto.Coins;
import VendingMachine.dto.Item;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;

public class View {
    // Declare UserIO object
    private final UserIO io;

    public View(UserIO io) {
        this.io = io;
    }

        public int getStartMenuSelection() {
        return io.readInt("Enter 1 to CONTINUE or 2 to QUIT.",
                1, 2);
    }

    // Print Main Menu
    public int getMainMenuSelection() {
        mainMenuBanner();
        io.print("1. Add Money");
        io.print("2. Purchase Item");
        io.print("3. Quit");

        return io.readInt("Please select an operation.", 1, 3);
    }

    // Add Money
    public BigDecimal addFundsDisplay(BigDecimal balance) {
        addFundsBanner();
        displayBalance(balance);
        balance = balance.add(io.readBigDecimal(
                "Enter funds to add ($0.01 - $100.00): ",
                new BigDecimal("0.01"), new BigDecimal("100.00")));
        fundsAddedMessage();
        displayBalance(balance);
        io.readString("Please hit enter to continue.");
        return balance;
    }

    // Display balance
    public void displayBalance(BigDecimal balance) {
        io.print("Current balance: $" +
                balance.setScale(2, RoundingMode.HALF_UP));
    }

    // Get selection for item that user wants to purchase
    public int getItemSelection(int itemListSize) {
        return io.readInt("Please select an item",
                1, itemListSize);
    }

    // Display list of all Items
    public void displayAllItems(List<Item> itemList) {
        displayAllItemsBanner();

        int itemNum = 1;

        for (Item i : itemList) {
            io.printf(itemNum, i.getItemName(), i.getItemCost());
            itemNum++;
        }

        // Print blank line after list for readability
        io.print("");
    }

    public void displayPurchase(Item item, BigDecimal balance) {
        io.print("Purchased: " + item.getItemName() +
                "\nCost: $" + item.getItemCost() +
                "\nRemaining Balance: $" + balance);
    }

    // Print change to be returned to user
    public void printChange(HashMap<Coins, Integer> map) {
        io.print("\nDispensing Change:");
        for(Coins c : map.keySet()) {
            io.print("\t"+ c.name() + "($" + c.getValue() + "): " + map.get(c));
        }
    }

    public void displayWelcomeBanner() {
        io.print("\n-------------------------------");
        io.print("Welcome to the Vending Machine!");
        io.print("-------------------------------");
    }

    public void displayAddFundsReminder() {
        io.print("\n*** Remember to add funds before selecting an item. ***");
    }

    public void displayAllItemsBanner() {
        io.print("\n===== Vending Machine Items =====");
    }

    // banner and message
    public void mainMenuBanner() {
        io.print("\n===== MAIN MENU =====");
    }

    public void addFundsBanner() {
        io.print("\n===== ADD FUNDS =====");
    }

    public void fundsAddedMessage() {
        io.print("Money added! Thank you.");
    }


    public void purchaseItemBanner() {
        io.print("\n===== PURCHASE ITEM(S) =====");
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
        io.print("\n===== UNKNOWN COMMAND =====");
        io.readString("Please hit enter to continue.");
    }

    // Display Error message
    public void displayErrorMessage(String errorMsg) {
        io.print("\n===== ERROR =====");
        io.print(errorMsg);
        io.readString("Please hit enter to continue.");
    }
}
