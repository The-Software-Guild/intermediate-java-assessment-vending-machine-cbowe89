package VendingMachine.service;

import VendingMachine.dao.*;
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
        VendingMachineDao dao = new VendingMachineDaoFileImpl();
        AuditDao auditDao = new AuditDaoImpl();
    }

    public ServiceLayerImpl(VendingMachineDao vendingMachineDao,
                            AuditDao auditDao)
            throws PersistenceException {
        this.vendingMachineDao = vendingMachineDao;
        this.auditDao = auditDao;
    }

    @Override
    public Item getItem(Item item) throws
            PersistenceException,
            ItemInventoryException {
        // Validate that item quantity is greater than 1
        validateItemQuantity(item);

        return vendingMachineDao.getItem(item.getItemName());
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
        auditDao.writeAuditEntry(item.getItemName() + " quantity changed to "
                + newCount);
        return vendingMachineDao.changeItemQuantity(item, newCount);
    }

    @Override
    public BigDecimal sellItem(BigDecimal totalFunds, Item item) throws
            PersistenceException,
            ItemInventoryException,
            InsufficientFundsException {
        // Validate that item quantity is 1 or more
        validateItemQuantity(item);

        // Validate that totalFunds are greater than itemCost
        validateSufficientFunds(totalFunds, item.getItemCost());

        // Allow dao to sell item: subtract 1 from quantity
        // and subtract item cost from totalFunds
        changeInventoryQuantity(item, item.getItemQuantity()-1);
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

    private void validateSufficientFunds (BigDecimal balance, BigDecimal itemCost) throws
            InsufficientFundsException {
        if (itemCost.compareTo(balance) > 0) {
            throw new InsufficientFundsException("ERROR: $" + balance +
                    " is insufficient to purchase selected item.");
        }
    }
}
