package transactions;

import java.sql.SQLException;

public interface PurchaseHandlerInterface {
    public void removePack(int id);
    public void purchasePack(String user);
    public boolean checkifbroke(String user) throws SQLException;
    public void assignCards(int id,String user) throws SQLException;
    public boolean buyPack(String user) throws SQLException;
}
