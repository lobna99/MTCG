package transactions;

import java.io.IOException;
import java.sql.SQLException;

public interface PurchaseHandler {
    public void removePack(int id) throws SQLException;

    public boolean purchasePack(String user) throws SQLException, IOException;

    public boolean checkifbroke(String user) throws SQLException;

    public void assignCards(int id, String user) throws SQLException;

    public boolean buyPack(String user) throws SQLException;
}
