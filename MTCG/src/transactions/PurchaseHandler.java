package transactions;


import Http.HttpStatus;
import db.DBconnectionImpl;
import db.getDBConnection;
import response.Response;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PurchaseHandler implements getDBConnection,PurchaseHandlerInterface, Response {

    public PurchaseHandler() {
    }
    public boolean buyPack(String user) throws SQLException {//purchase Package
        //-------- SELECT RANDOM PACKAGE AND GET ID
        PreparedStatement statement = DBconnectionImpl.getInstance().getConnection().prepareStatement("""
                         SELECT id
                         from packages
                         ORDER BY
                          	id
                          LIMIT 1;
                     """);
        ResultSet result=statement.executeQuery();
        int packageid;
        if(result.next()) {
             packageid= result.getInt(1);
             result.close();
        }else{
            result.close();
            return false;
        }
        assignCards(packageid,user);
        //---------- USER PAYED 5 COINS FOR PACKAGE
        PreparedStatement pay = DBconnectionImpl.getInstance().getConnection().prepareStatement("""
                         UPDATE users
                         SET   coins=coins-5
                         WHERE username=?
                     """);
        pay.setString(1,user);
        pay.execute();
        removePack(packageid);
        pay.close();
        statement.close();
        DBconnectionImpl.getInstance().getConnection().close();
        return true;
    }
    public void assignCards(int id,String user) throws SQLException {
        //---------------ASSIGN CARDS TO A PACKAGE
        PreparedStatement statement = DBconnectionImpl.getInstance().getConnection().prepareStatement("""
                        UPDATE cards
                         SET "user"=?
                         WHERE packageid=?
                     """);
        statement.setString(1,user);
        statement.setInt(2,id);
        statement.execute();
        statement.close();
        DBconnectionImpl.getInstance().getConnection().close();
    }
    public boolean checkifbroke(String user) throws SQLException {
        //-----------CHECK IF USER GOT ENOUGH MONEY
        PreparedStatement statement = DBconnectionImpl.getInstance().getConnection().prepareStatement("""
                         SELECT coins
                         from users
                         WHERE username=?;
                     """);
        statement.setString(1,user);
        ResultSet rs=statement.executeQuery();
        rs.next();
        if(rs.getInt(1) < 5){
            statement.close();
            DBconnectionImpl.getInstance().getConnection().close();
            return true;
        }else {
            statement.close();
            DBconnectionImpl.getInstance().getConnection().close();
            return false;
        }
    }
    public void purchasePack(String user){
        try {
            if (!checkifbroke(user)) {
                if(buyPack(user)) {
                    respond.writeHttpResponse(HttpStatus.OK, "Purchase was succesful");
                }else{
                    respond.writeHttpResponse(HttpStatus.BAD_REQUEST,"No Package available");
                }
            }else{
                respond.writeHttpResponse(HttpStatus.BAD_REQUEST,"User doesnt have enough coins");
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
    public void removePack(int id){
        PreparedStatement statement = null;
        try {
            statement = DBconnectionImpl.getInstance().getConnection().prepareStatement("""
                         DELETE
                         FROM packages
                         WHERE id=?;
                         """);
            statement.setInt(1,id);
            statement.execute();
            statement.close();
            DBconnectionImpl.getInstance().getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
