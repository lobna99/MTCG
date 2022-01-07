package deck;

import cards.Card;
import db.DBconnection;
import server.HttpStatus;
import server.Responsebuilder;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeckHandler {
    private DBconnection Connection;

    public DeckHandler() {
        Connection=DBconnection.getInstance();
    }
    public void getDeck(String user)  {
        Responsebuilder respond = Responsebuilder.getInstance();
        PreparedStatement statement = null;
        try {
            statement = Connection.getConnection().prepareStatement("""
                             SELECT *
                             FROM cards
                             WHERE "user"=?
                             AND "inDeck"=true; 
                         """);
            statement.setString(1, user);
            ResultSet rs=statement.executeQuery();
            if(rs.next()) {
                while (rs.next()){
                    String name = rs.getString("name");
                    double dmg = rs.getDouble("damage");
                    int element = rs.getInt("element");
                    String response = " | " + name + " | " + dmg + " | " + element + " |";
                    try {
                        respond.writeHttpResponse(HttpStatus.OK, response);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }else{
            try {
                respond.writeHttpResponse(HttpStatus.OK,"No Deck is set for this user");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

