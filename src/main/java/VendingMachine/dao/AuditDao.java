package VendingMachine.dao;

public interface AuditDao {

    public void writeAuditEntry(String entry) throws
            PersistenceException;
}
