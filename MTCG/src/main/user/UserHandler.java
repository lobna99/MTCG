package user;

import db.DBconnectionImpl;
import db.getDBConnection;
import org.codehaus.jackson.JsonNode;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserHandler implements getDBConnection {


    public UserHandler() {
    }

    public boolean registration(JsonNode node) {
        String name = node.get("Username").getValueAsText();
        String password = node.get("Password").getValueAsText();
        User newUser = new User(name, password);
        try {
            insertUser(newUser);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public void insertUser(User newUser) throws SQLException {
        Connection con = DBconnectionImpl.getInstance().getConnection();
        PreparedStatement statement = con.prepareStatement("""
                    INSERT INTO users
                    (username,password,ELO,coins)
                    VALUES (?,?,?,?);   
                """);
        statement.setString(1, newUser.getUsername());
        statement.setString(2, newUser.getPassword());
        statement.setInt(3, newUser.getELO());
        statement.setInt(4, newUser.getCoins());
        statement.execute();
        statement.close();
        con.close();
    }

    public String getUserData(String user) throws SQLException, IOException {
        PreparedStatement statement = null;
        Connection con = DBconnectionImpl.getInstance().getConnection();
        statement = con.prepareStatement("""
                    SELECT *
                    from users
                    WHERE username=?
                """);
        statement.setString(1, user);
        ResultSet rs = statement.executeQuery();
        String response = "";
        while (rs.next()) {
            String username = rs.getString("Username");
            String name = rs.getString("name");
            String bio = rs.getString("bio");
            String image = rs.getString("image");
            int elo = rs.getInt("elo");
            int coins = rs.getInt("coins");
            response += "{\"Username\":\"" + username + "\",\"Name\":\"" + name + "\",\"ELO\":\"" + elo + "\",\"Coins\":\"" + coins + "\",\"Bio\":\"" + bio + "\",\"Image\":\"" + image + "\"}\n";
        }
        rs.close();
        statement.close();
        con.close();
        return response;
    }

    public boolean updateUser(String user, JsonNode node) throws SQLException {
        PreparedStatement statement = null;
        java.sql.Connection con = DBconnectionImpl.getInstance().getConnection();
        statement = con.prepareStatement("""
                   UPDATE users
                    SET bio=?,name=?,image=?
                    WHERE username=?;
                """);
        statement.setString(1, node.get("Bio").getValueAsText());
        statement.setString(2, node.get("Name").getValueAsText());
        statement.setString(3, node.get("Image").getValueAsText());
        statement.setString(4, user);
        int upd = statement.executeUpdate();
        statement.close();
        con.close();
        return upd > 0;

    }
}
