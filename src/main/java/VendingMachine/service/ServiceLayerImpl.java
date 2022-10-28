package VendingMachine.service;

import VendingMachine.dao.*;
import VendingMachine.dto.Item;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceLayerImpl implements
        ServiceLayer {

    private final VendingMachineDao vendingMachineDao;
    private final AuditDao auditDao;

    public ServiceLayerImpl() throws
            PersistenceException {
        vendingMachineDao = new VendingMachineDaoFileImpl();
        auditDao = new AuditDaoImpl();
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
        auditDao.writeAuditEntry(item.getItemName() + " added.");
        return vendingMachineDao.addItem(item.getItemName(), item);
    }

    @Override
    public Item removeItem(Item item) throws PersistenceException {
        auditDao.writeAuditEntry(item.getItemName() + " removed.");
        return vendingMachineDao.removeItem(item);
    }

    @Override
    public Item changeInventoryQuantity(Item item, int newCount) throws
            PersistenceException {
        auditDao.writeAuditEntry(item.getItemName() + " quantity updated to "
                + newCount + ".");
        return vendingMachineDao.changeInventoryQuantity(item, newCount);
    }

    @Override
    public BigDecimal sellItem(BigDecimal totalFunds, Item item) throws
            PersistenceException,
            ItemInventoryException,
            InsufficientFundsException {
        // Validate that item quantity is 1 or more
        validateItemQuantity(item.getItemName());

        // Validate that totalFunds are greater than itemCost
        validateSufficientFunds(totalFunds, item.getItemCost());

        // Allow dao to sell item: subtract 1 from quantity
        // and subtract item cost from totalFunds
        auditDao.writeAuditEntry("1 " + item.getItemName() + " sold.");
        //changeInventoryQuantity(item, item.getItemQuantity()-1);
        return totalFunds = totalFunds.subtract(item.getItemCost());
    }

    private void validateItemQuantity (String itemName) throws
            ItemInventoryException, PersistenceException {
        Item toValidate = vendingMachineDao.getItem(itemName);
        if (toValidate.getItemQuantity() < 1) {
            throw new ItemInventoryException(
                    "ERROR: " + itemName +
                            " Unavailable! Quantity is less than 1.");
        }
    }

    private void validateSufficientFunds (BigDecimal balance, BigDecimal itemCost) throws
            InsufficientFundsException {
        if (itemCost.compareTo(balance) > 0) {
            throw new InsufficientFundsException("ERROR: $" + balance +
                    " is insufficient to purchase selected item.");
        }
    }
}
