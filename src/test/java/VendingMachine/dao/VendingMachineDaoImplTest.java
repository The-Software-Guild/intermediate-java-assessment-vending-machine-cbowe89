package VendingMachine.dao;

import VendingMachine.dao.VendingMachineDao;
import VendingMachine.dao.VendingMachineDaoFileImpl;
import VendingMachine.dto.Item;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VendingMachineDaoImplTest {

    public static VendingMachineDao testDao;

    public VendingMachineDaoImplTest() {
    }

    @BeforeAll
    public static void setUpClass() throws Exception {
        testDao = new VendingMachineDaoFileImpl();
    }

    @AfterAll
    public static void tearDownClass() throws Exception {
    }

    @BeforeEach
    public void setUp() throws Exception {
        testDao = new VendingMachineDaoFileImpl();
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
        List<Item> testList = testDao.getAllItems();

        //implement
    }

    /**
     * Test of changeInventoryCount method, of class VendingMachineDaoImpl.
     */
    @Test
    public void testChangeItemQuantity() throws Exception {
        //implement
    }
}
