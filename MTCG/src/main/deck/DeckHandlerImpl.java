package deck;

import db.DBconnectionImpl;
import db.getDBConnection;
import org.codehaus.jackson.JsonNode;
import response.Response;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeckHandlerImpl implements getDBConnection, Response, DeckHandler {


    public DeckHandlerImpl() {
    }

    //Get deck of user
    public String getDeck(String user, String getParam) throws SQLException, IOException {
        PreparedStatement statement = null;
        Connection con = DBconnectionImpl.getInstance().getConnection();
        statement = con.prepareStatement("""
                    SELECT *
                    FROM card
                    WHERE "user"=?
                    AND "inDeck"=true;
                """);
        statement.setString(1, user);
        ResultSet rs = statement.executeQuery();
        int rows = 0;
        String response = "";
        while (rs.next()) {
            String id = rs.getString("id");
            String name = rs.getString("name");
            double dmg = rs.getDouble("damage");
            int element = rs.getInt("element");
            if (getParam.contains("plain")) {//plain returns a plain text else JSON
                response += id + "," + name + "," + dmg + "," + element + "\n";
            } else {
                response += "{\"Id\":\"" + id + "\",\"Name\":\"" + name + "\",\"Damage\":\"" + dmg + "\",\"Element\":\"" + element + "\"}\n";
            }
            rows++;
        }
        rs.close();
        statement.close();
        con.close();
        if (rows == 0) {//if theres no resultSet theres no deck for user
            return "No Deck is set for this user";
        }
        return response;
    }

    //call all relevant functions to configureDeck
    public boolean configureDeck(JsonNode node, String user) throws IOException, SQLException {
        if (node.size() == 4) {//deck can only consist of 4 cards
            for (int i = 0; i < 4; i++) {
                if (!addCardtoDeck(node.get(i).getValueAsText(), user)) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    //Update deck of user
    public boolean addCardtoDeck(String id, String user) throws SQLException, IOException {
        Connection con = DBconnectionImpl.getInstance().getConnection();
        PreparedStatement statement = con.prepareStatement("""
                   UPDATE card
                    SET "inDeck"=true
                    WHERE id=?
                    AND  "user"=?;
                """);
        statement.setString(1, id);
        statement.setString(2, user);
        int updatedRows = statement.executeUpdate();
        statement.close();
        con.close();
        return updatedRows > 0;
    }
}

