package VendingMachine.service;

import VendingMachine.dao.PersistenceException;
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
            service.changeInventoryCount(testItem, 100);
            assertNotNull(testItem, "Item should not be null");
            assertEquals(100, testItem.getItemQuantity(),
                    "Inventory item should be 100");
        } catch (PersistenceException e) {
            fail("No way it will go wrong");
        }

        try {
            service.changeInventoryCount(testItem, -100);
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
