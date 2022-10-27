package VendingMachine.service;

import VendingMachine.dao.AuditDao;
import VendingMachine.dao.AuditDaoImpl;
import VendingMachine.dao.VendingMachineDao;
import VendingMachine.dao.VendingMachineDaoFileImpl;
import VendingMachine.dao.PersistenceException;
import VendingMachine.dto.Item;
import VendingMachine.service.ServiceLayer;
import VendingMachine.service.ServiceLayerImpl;
import VendingMachine.service.ItemInventoryException;
import VendingMachine.service.InsufficientFundsException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ServiceImplTest {

    public static ServiceLayer service;

    public ServiceImplTest() {
    }

    @BeforeAll
    public static void setUpClass() throws PersistenceException {
        //implement
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() throws PersistenceException {
        //implement
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getItem method, of class VendingMachineServiceImpl.
     */
    @Test
    public void testGetItem() throws Exception {
        //implement
    }

    /**
     * Test of getAllItems method, of class VendingMachineServiceImpl.
     */
    @Test
    public void testListAllItems() throws Exception {
        assertEquals(8, service.getAllItems().size(), "8 items");
    }

    /**
     * Test of changeInventoryCount method, of class VendingMachineServiceImpl.
     */
    @Test
    public void testChangeInventoryCount() {
        Item testItem = new Item("Cheetos",
                new BigDecimal(2.99).setScale(2,RoundingMode.FLOOR),
                18);
        try {
            service.changeInventoryQuantity(testItem, 100);
            assertNotNull(testItem, "Item should not be null");
            assertEquals(100, testItem.getItemQuantity(),
                    "Inventory item should be 100");
        } catch (PersistenceException e) {
            fail("No way it will go wrong");
        }

        try {
            service.changeInventoryQuantity(testItem, -100);
        } catch (PersistenceException e) {
            System.out.println("the value should not be negative");
        }
    }

    /**
     * Test of sellItem method, of class VendingMachineServiceImpl.
     */
    @Test
    public void testSellItem() {
        //implement
    }
}
