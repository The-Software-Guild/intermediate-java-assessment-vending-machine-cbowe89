package VendingMachine.service;

import VendingMachine.dao.AuditDao;
import VendingMachine.dao.PersistenceException;
import VendingMachine.dao.VendingMachineDao;
import VendingMachine.dto.Item;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ServiceImplTest {

    public static ServiceLayer service;

    public ServiceImplTest() throws PersistenceException {
        VendingMachineDao dao = new DaoStubImpl();
        AuditDao auditDao = new AuditDaoStubImpl();

        service = new ServiceLayerImpl(dao, auditDao);
    }

    @BeforeAll
    public static void setUpClass() throws PersistenceException {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() throws PersistenceException {
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
        Item testItem = new Item("Cheetos",
                new BigDecimal("2.99").setScale(2,RoundingMode.FLOOR),
                18);
        try{
            service.changeInventoryQuantity(testItem, 100);
            assertNotNull(testItem, "Item should not be null");
            assertEquals(100, testItem.getItemQuantity(),
                    "Inventory item should be 100");
        }catch(PersistenceException e){
            fail("No way it will go wrong");
        }

        try{
            service.changeInventoryQuantity(testItem, -100);
        }catch(PersistenceException e){
            System.out.println("the value should not be negative");
        }
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
        int testQuantityAfterChange = testClone.getItemQuantity() - 1;

        // ACT & ASSERT
        BigDecimal balanceAfterSale = service.sellItem(testBalance, testClone);
        Item itemAfterSale = service.getItem(testClone.getItemName());
        assertTrue(testQuantityAfterChange >= 0,
                "Item quantity must be greater than 0 to sell item.");
        assertTrue(testBalance.compareTo(testClone.getItemCost()) >= 0,
                "Balance must be greater than or equal to item cost.");
        assertEquals(balanceAfterSale, testBalance.subtract(testClone.getItemCost()),
                "Balance returned should equal the testBalance - itemCost.");
    }
}
