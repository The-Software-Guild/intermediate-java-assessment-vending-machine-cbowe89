package VendingMachine.service;

import VendingMachine.dao.AuditDao;
import VendingMachine.dao.PersistenceException;
import VendingMachine.dao.VendingMachineDao;
import VendingMachine.dto.Item;
import java.math.BigDecimal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The {@code ServiceImplTest} class is responsible for
 * testing the ServiceLayerImpl class.
 */
public class ServiceImplTest {
    // Declare ServiceLayer object
    public static ServiceLayer service;

    /**
     * No-args constructor for ServiceImplTest
     */
    public ServiceImplTest() throws PersistenceException {
        VendingMachineDao dao = new DaoStubImpl();
        AuditDao auditDao = new AuditDaoStubImpl();

        service = new ServiceLayerImpl(dao, auditDao);
    }

    @BeforeEach
    public void setUp() throws PersistenceException {
        //Item testItem = new Item("Doritos", new BigDecimal("1.23"), 9);
        //service.addItem(testItem);
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getItem method, of class VendingMachineServiceImpl.
     */
    @Test
    public void testGetItem() throws Exception {
        // ARRANGE
        Item testClone = new Item("Doritos");
        testClone.setItemCost(new BigDecimal("1.23"));
        testClone.setItemQuantity(9);

        // ACT & ASSERT
        Item shouldBeDoritos = service.getItem("Doritos");
        assertNotNull(shouldBeDoritos,
                "Getting item 'shouldBeDoritos' should not be null.");
        assertEquals(testClone, shouldBeDoritos,
                "Item stored in 'shouldBeDoritos' should be Doritos.");
        Item shouldBeNull = service.getItem("Peanuts");
        assertNull(shouldBeNull, "Getting item 'Peanuts' should be null.");
    }

    /**
     * Test of getAllItems method, of class VendingMachineServiceImpl.
     */
    @Test
    public void testGetAllItems() throws Exception {
        // ARRANGE
        Item testClone = new Item("Doritos");
        testClone.setItemCost(new BigDecimal("1.23"));
        testClone.setItemQuantity(9);

        // ACT & ASSERT
        assertEquals(1, service.getAllItems().size(),
                "Should only have 1 item.");
        assertTrue(service.getAllItems().contains(testClone),
                "The 1 item should be Doritos.");
    }

    /**
     * Test of changeInventoryCount method of VendingMachineServiceImpl
     */
    @Test
    public void testChangeInventoryQuantity() throws PersistenceException,
            ItemInventoryException, InsufficientFundsException {

        // ARRANGE
        Item testClone = new Item("Doritos");
        testClone.setItemCost(new BigDecimal("1.23"));
        testClone.setItemQuantity(9);

        // ACT & ASSERT
        service.changeInventoryQuantity(testClone, 20);
        Item itemAfterSale = service.getItem("Doritos");
        assertNotNull(testClone,"Item should not be null.");
        assertEquals(20, itemAfterSale.getItemQuantity());
    }

    /**
     * Test of sellItem method, of class VendingMachineServiceImpl.
     */
    @Test
    public void testSellItem() throws PersistenceException,
            InsufficientFundsException,
            ItemInventoryException {
        // ARRANGE
        Item testClone = new Item("Doritos");
        testClone.setItemCost(new BigDecimal("1.23"));
        testClone.setItemQuantity(9);
        BigDecimal testBalance = new BigDecimal("10.00");

        // ACT & ASSERT
        assertEquals(testClone.getItemQuantity(), 9,
                "testClone quantity before sale should be 9.");
        BigDecimal balanceAfterSale = service.sellItem(testBalance, testClone);
        assertTrue(testClone.getItemQuantity() >= 0,
                "Item quantity must be greater than 0 to sell item.");
        assertEquals(testClone.getItemQuantity(), 8,
                "testClone quantity after sale should be 8");
        assertTrue(testBalance.compareTo(testClone.getItemCost()) >= 0,
                "Balance must be greater than or equal to item cost.");
        assertEquals(balanceAfterSale, testBalance.subtract(testClone.getItemCost()),
                "Balance returned should equal the testBalance - itemCost.");
    }
}
