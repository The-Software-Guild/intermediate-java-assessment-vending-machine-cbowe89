package VendingMachine.dao;

import VendingMachine.dto.Item;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class VendingMachineDaoFileImplTest {

    public static VendingMachineDao testVendingMachineDao;

    public VendingMachineDaoFileImplTest() {
    }

    @BeforeAll
    public static void setUpClass() throws Exception {
        testVendingMachineDao = new VendingMachineDaoFileImpl();
    }

    @AfterAll
    public static void tearDownClass() throws Exception {
    }

    @BeforeEach
    public void setUp() throws Exception {
        testVendingMachineDao = new VendingMachineDaoFileImpl();
    }

    @AfterEach
    public void tearDown() throws Exception {
    }

    /**
     * Test of getItem method, of class VendingMachineDaoImpl.
     */
    @Test
    public void testGetItem() throws Exception {
        //implement
    }

    /**
     * Test of getAllItems method, of class VendingMachineDaoImpl.
     */
    @Test
    public void testListAllItems() throws Exception {
        List<Item> testList = testVendingMachineDao.getAllItems();

        //implement
    }

    /**
     * Test of changeInventoryCount method, of class VendingMachineDaoImpl.
     */
    @Test
    public void testChangeInventoryCount() throws Exception {
        //implement
    }
}
