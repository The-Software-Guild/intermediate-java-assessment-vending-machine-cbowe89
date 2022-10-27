package VendingMachine.dao;

import VendingMachine.dto.Item;
import java.util.*;

public class VendingMachineDaoFileImpl implements VendingMachineDao {
    private final String ITEM_FILE;
    private final FileDao fileDao;
    private Map<String, Item> itemMap;

    public VendingMachineDaoFileImpl() throws PersistenceException {
        ITEM_FILE = "items.txt";
        fileDao = new FileDaoImpl();
        itemMap = fileDao.readFile(ITEM_FILE);
    }

    public VendingMachineDaoFileImpl(String vendingMachineTextFile)
            throws PersistenceException{
        ITEM_FILE = vendingMachineTextFile;
        fileDao = new FileDaoImpl();
        itemMap = fileDao.readFile(ITEM_FILE);
    }

    @Override
    public Item addItem(String itemName, Item item) throws
            PersistenceException {
        itemMap = fileDao.readFile(ITEM_FILE);
        Item result = itemMap.put(item.getItemName(), item);
        fileDao.writeFile(new ArrayList<>(itemMap.values()));
        return result;
    }

    @Override
    public List<Item> getAllItems() throws
            PersistenceException {
        itemMap = fileDao.readFile(ITEM_FILE);
        return new ArrayList<>(itemMap.values());
    }

    @Override
    public Item getItem(String name) throws
            PersistenceException {
        itemMap = fileDao.readFile(ITEM_FILE);
        return itemMap.get(name);
    }

    @Override
    public Item removeItem(Item item) throws
            PersistenceException {
        itemMap = fileDao.readFile(ITEM_FILE);
        Item result = itemMap.remove(item.getItemName());
        fileDao.writeFile(new ArrayList<>(itemMap.values()));
        return result;
    }

    @Override
    public Item changeItemQuantity(Item item, int newCount) throws
            PersistenceException {
        item.setItemQuantity(newCount);
        Item result = itemMap.put(item.getItemName(), item);
        fileDao.writeFile(new ArrayList<>(itemMap.values()));
        return result;
    }
}
