package VendingMachine.service;

import VendingMachine.dao.AuditDao;
import VendingMachine.dao.VendingMachineDao;
import VendingMachine.dao.PersistenceException;
import VendingMachine.dto.Item;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceLayerImpl implements
        ServiceLayer {

    private VendingMachineDao vendingMachineDao;
    private AuditDao auditDao;

    public ServiceLayerImpl() throws
            PersistenceException {
        //implement
    }

    public ServiceLayerImpl(VendingMachineDao vendingMachineDao,
                            AuditDao auditDao)
            throws PersistenceException {
        this.vendingMachineDao = vendingMachineDao;
        this.auditDao = auditDao;
    }

    @Override
    public Item getItem(String itemName) throws
            PersistenceException,
            ItemInventoryException {
        return vendingMachineDao.getItem(itemName);
    }

    @Override
    public List<Item> getAllItems() throws PersistenceException {
        return vendingMachineDao.getAllItems()
                .stream()
                .filter(item->item.getItemQuantity()>0)
                .collect(Collectors.toList());
    }

    @Override
    public Item addItem(Item item) throws PersistenceException {
        //implement
    }

    @Override
    public Item removeItem(Item item) throws PersistenceException {
        //implement
    }

    @Override
    public Item changeInventoryQuantity(Item item, int newCount) throws
            PersistenceException {
        //implement
    }

    @Override
    public BigDecimal sellItem(BigDecimal totalFunds, Item item) throws
            PersistenceException,
            ItemInventoryException,
            InsufficientFundsException {
        // Validate that item quantity is 1 or more
        validateItemQuantity(item);

        // Allow dao to sell item
        return totalFunds = totalFunds.subtract(item.getItemCost());
    }

    private void validateItemQuantity (Item item) throws
            ItemInventoryException {
        if (item.getItemQuantity() < 1) {
            throw new ItemInventoryException(
                    "ERROR: " + item.getItemName() +
                            " Unavailable! Quantity is less than 1.");
        }
    }
}
