package transactions;

import db.DBconnection;
import server.HttpStatus;
import server.Responsebuilder;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PurchaseHandler {
    private DBconnection Connection;

    public PurchaseHandler() {
        Connection=DBconnection.getInstance();
    }
    public boolean buyPack(String user) throws SQLException {//purchase Package
        //-------- SELECT RANDOM PACKAGE AND GET ID
        PreparedStatement statement = Connection.getConnection().prepareStatement("""
                         SELECT id
                         from packages 
                         WHERE purchased=false
                         ORDER BY
                          	RANDOM()
                          LIMIT 1;
                     """);
        ResultSet result=statement.executeQuery();
        int packageid;
        if(result.next()) {
             packageid= result.getInt(1);
        }else{
            return false;
        }
        //-------------- SET PACKAGE AS PURCHASED
        PreparedStatement bought = Connection.getConnection().prepareStatement("""
                         UPDATE packages
                         SET   purchased=?
                         WHERE id=?
                     """);
        bought.setBoolean(1,true);
        bought.setInt(2,packageid);
        bought.execute();
        assignCards(packageid,user);
        //---------- USER PAYED 5 COINS FOR PACKAGE
        PreparedStatement pay = Connection.getConnection().prepareStatement("""
                         UPDATE users
                         SET   coins=coins-5
                         WHERE username=?
                     """);
        pay.setString(1,user);
        pay.execute();
        return true;
    }
    public void assignCards(int id,String user) throws SQLException {
        //---------------ASSIGN CARDS TO A PACKAGE
        PreparedStatement statement = Connection.getConnection().prepareStatement("""
                        UPDATE cards
                         SET "user"=?
                         WHERE packageid=?
                     """);
        statement.setString(1,user);
        statement.setInt(2,id);
        statement.execute();
        statement.close();
    }
    public boolean checkifbroke(String user) throws SQLException {
        //-----------CHECK IF USER GOT ENOUGH MONEY
        PreparedStatement statement = Connection.getConnection().prepareStatement("""
                         SELECT coins
                         from users 
                         WHERE username=?;
                     """);
        statement.setString(1,user);
        ResultSet rs=statement.executeQuery();
        rs.next();
        return rs.getInt(1) < 5;
    }
    public void purchasePack(String user){
        Responsebuilder respond=Responsebuilder.getInstance();
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
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
