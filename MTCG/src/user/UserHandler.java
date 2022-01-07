package user;

import db.DBconnection;
import org.codehaus.jackson.JsonNode;
import server.HttpStatus;
import server.Responsebuilder;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserHandler {

    private Responsebuilder respond=Responsebuilder.getInstance();
    private DBconnection Connection;

    public UserHandler() {
        Connection=DBconnection.getInstance();
    }
    public void registration(JsonNode node) throws IOException {
        String name= node.get("Username").getValueAsText();
        String password= node.get("Password").getValueAsText();
        User newUser=new User(name,password);
        try {
            insertUser(newUser);
            respond.writeHttpResponse(HttpStatus.CREATED,"User created");
        } catch (SQLException e) {
            respond.writeHttpResponse(HttpStatus.BAD_REQUEST,"User already exists");
        }

    }
    public void insertUser(User newUser) throws SQLException{
        PreparedStatement statement = Connection.getConnection().prepareStatement("""
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
    }
}
