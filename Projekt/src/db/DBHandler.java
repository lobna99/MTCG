package main.db;

import cards.Card;
import user.User;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBHandler {
    private DBconnection Connection;

    public DBHandler() {
        Connection=DBconnection.getInstance();
    }
    public void InsertCard(Card newCard) throws SQLException {

        PreparedStatement statement = Connection.getConnection().prepareStatement("""
                         INSERT INTO cards
                         (monster,element)
                         VALUES (?,?);   
                     """);
            statement.setString(1, newCard.getName());
            statement.setString(2, String.valueOf(newCard.getElement()));
            statement.execute();
    }
    public void InsertUser(User newUser)throws SQLException {

        PreparedStatement statement = Connection.getConnection().prepareStatement("""
                         INSERT INTO user
                         (username,pass,coins,ELO)
                         VALUES (?,?,?);   
                     """);
        statement.setString(1, newUser.getUsername());
        statement.setString(2, newUser.getPassword());
        statement.setString(2, String.valueOf(newUser.getCoins()));
        statement.setString(2, String.valueOf(newUser.getELO()));
        statement.execute();
    }

}
