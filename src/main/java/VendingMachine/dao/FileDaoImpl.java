package VendingMachine.dao;

import VendingMachine.dto.Item;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class FileDaoImpl implements FileDao {
    private static final Map<String, Item> itemMap = new HashMap<>();
    private final String ITEM_FILE;
    private static final String DELIMITER = "::";

    public FileDaoImpl() {
        ITEM_FILE = "items.txt";
    }

    public FileDaoImpl(String ITEM_FILE) {
        this.ITEM_FILE = ITEM_FILE;
    }

    @Override
    public Item unmarshallItem(String line) {
        // Split line from items.txt file into tokens,
        // store tokens in array
        String[] itemTokens = line.split(DELIMITER);

        // Retrieve item name from array element[0]
        String itemName = itemTokens[0];

        // Instantiate new Item object
        Item itemFromFile = new Item(itemName);

        // Set cost from array element[1]
        itemFromFile.setItemCost(new BigDecimal(itemTokens[1])
                .setScale(2, RoundingMode.DOWN));

        // Set item quantity from array element[2]
        itemFromFile.setItemQuantity(Integer.parseInt(itemTokens[2]));

        // Return Item object
        return itemFromFile;
    }

    @Override
    public String marshallItem (Item item) {
        return item.getItemName() + DELIMITER + item.getItemCost() + DELIMITER
                + item.getItemQuantity();
    }

    @Override
    public Map<String, Item> readFile(String fileName) throws PersistenceException {
        // Declare Scanner object
        Scanner sc;

        try {
            // Initialize Scanner object
            sc = new Scanner(new BufferedReader(new FileReader(fileName)));
            //return itemMap;
        } catch (FileNotFoundException e) {
            throw new PersistenceException("File not found.", e);
        }

        // Declare variable to store the most recent line read from the file
        String currentLine;
        // Declare variable to store the most recent item unmarshalled
        Item currentItem;

        // Go through each line of the file. Decode each line into an Item
        // object by calling the unmarshallItem method.
        // Process while file has more lines
        while (sc.hasNextLine()) {
            // Get the next line in the file
            currentLine = sc.nextLine();

            // Unmarshall the line into an Item object
            currentItem = unmarshallItem(currentLine);

            // Using the itemName as the map key, put the currentItem
            // into the item map
            itemMap.put(currentItem.getItemName(), currentItem);
        }
        // Close Scanner
        sc.close();

        // Return Map of Items
        return itemMap;
    }

    @Override
    public void writeFile(List<Item> itemList) throws PersistenceException {
        // Declare PrintWriter Object
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(ITEM_FILE));
            String itemAsText;

            for (Item currentItem : itemList) {
                // Turn an Item object into a string
                itemAsText = marshallItem(currentItem);
                //Write the Item object to the file
                out.println(itemAsText);
                // Force PrintWriter to write line to the file
                out.flush();
            }
        } catch (IOException e) {
            throw new PersistenceException
                    ("Could not save item data", e);
        }

        // Close PrintWriter
        out.close();
    }
}
