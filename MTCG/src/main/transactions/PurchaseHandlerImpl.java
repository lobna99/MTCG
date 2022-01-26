package transactions;


import Http.HttpStatus;
import db.DBconnectionImpl;
import db.getDBConnection;
import response.Response;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PurchaseHandlerImpl implements getDBConnection, PurchaseHandler {

    public PurchaseHandlerImpl() {
    }

    public boolean buyPack(String user) throws SQLException {//purchase Package
        Connection con = DBconnectionImpl.getInstance().getConnection();
        //-------- SELECT PACKAGE AND GET ID
        PreparedStatement statement = con.prepareStatement("""
                    SELECT id
                    from package
                    ORDER BY
                     	id
                     LIMIT 1;
                """);
        ResultSet result = statement.executeQuery();
        int packageid;
        if (result.next()) {
            packageid = result.getInt(1);
            result.close();
        } else {
            result.close();
            return false;
        }
        assignCards(packageid, user);
        pay(user, packageid);
        removePack(packageid);
        statement.close();
        con.close();
        return true;
    }

    public void assignCards(int id, String user) throws SQLException {
        //---------------ASSIGN CARDS TO A PACKAGE
        Connection con = DBconnectionImpl.getInstance().getConnection();
        PreparedStatement statement = con.prepareStatement("""
                   UPDATE card
                    SET "user"=?
                    WHERE packageid=?
                """);
        statement.setString(1, user);
        statement.setInt(2, id);
        statement.execute();
        statement.close();
        con.close();
    }

    public boolean checkifbroke(String user) throws SQLException {
        //-----------CHECK IF USER GOT ENOUGH MONEY
        Connection con = DBconnectionImpl.getInstance().getConnection();
        PreparedStatement statement = con.prepareStatement("""
                    SELECT coins
                    from "user"
                    WHERE username=?;
                """);
        statement.setString(1, user);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            if (rs.getInt(1) < 5) {
                statement.close();
                con.close();
                return true;
            } else {
                statement.close();
                con.close();
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean purchasePack(String user) throws SQLException, IOException {
        if (!checkifbroke(user)) {
            return buyPack(user);
        } else {
            return false;
        }
    }

    public void removePack(int id) throws SQLException {
        //Delete pack after its bought
        PreparedStatement statement = null;
        Connection con = DBconnectionImpl.getInstance().getConnection();
        statement = con.prepareStatement("""
                DELETE
                FROM package
                WHERE id=?;
                """);
        statement.setInt(1, id);
        statement.execute();
        statement.close();
        con.close();
    }

    public boolean pay(String user, int packageid) throws SQLException {
        //---------- USER PAYED 5 COINS FOR PACKAGE
        Connection con = DBconnectionImpl.getInstance().getConnection();
        PreparedStatement pay = con.prepareStatement("""
                    UPDATE "user"
                    SET   coins=coins-(Select cost from package where id=?)
                    WHERE username=?
                """);
        pay.setInt(1, packageid);
        pay.setString(2, user);
        int update = pay.executeUpdate();
        pay.close();
        con.close();
        return update > 0;
    }
}
