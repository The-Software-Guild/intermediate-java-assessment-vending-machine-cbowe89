package VendingMachine.dao;

import VendingMachine.dto.Item;
import java.util.*;

public class VendingMachineDaoFileImpl implements VendingMachineDao {
    private String ITEM_FILE;
    private FileDao fileDao;
    private Map<String, Item> itemMap;

    public VendingMachineDaoFileImpl() throws PersistenceException {
        ITEM_FILE = "items.txt";
        fileDao = new FileDaoImpl();
        itemMap = fileDao.readFile(ITEM_FILE);
    }

    public VendingMachineDaoFileImpl(String vendingMachineTextFile)
            throws PersistenceException{
        ITEM_FILE = vendingMachineTextFile;
        fileDao = new FileDaoImpl(ITEM_FILE);
        itemMap = fileDao.readFile(ITEM_FILE);
    }

    @Override
    public Item addItem(String itemName, Item item) throws
            PersistenceException {
        Item result = itemMap.put(item.getItemName(), item);
        fileDao.writeFile(new ArrayList<>(itemMap.values()));
        return result;
    }

    @Override
    public List<Item> getAllItems() throws
            PersistenceException {
        return new ArrayList<>(itemMap.values());
    }

    @Override
    public Item getItem(String name) throws
            PersistenceException {
        return itemMap.get(name);
    }

    @Override
    public Item removeItem(Item item) throws
            PersistenceException {
        Item result = itemMap.remove(item.getItemName());
        fileDao.writeFile(new ArrayList<>(itemMap.values()));
        return result;
    }

    @Override
    public Item changeInventoryQuantity(Item item, int newCount) throws
            PersistenceException {
        item.setItemQuantity(newCount);
        Item result = itemMap.replace(item.getItemName(), item);
        fileDao.writeFile(new ArrayList<>(itemMap.values()));
        return result;
    }
}
