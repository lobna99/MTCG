package deck;

import db.getDBConnection;
import org.codehaus.jackson.JsonNode;
import Http.HttpStatus;
import response.Response;
import response.Responsebuilder;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeckHandler implements getDBConnection, Response,DeckHandlerInterface {


    public DeckHandler() {
    }

    //Get deck of user
    public void getDeck(String user, String getParam) {
        PreparedStatement statement = null;
        try {
            statement = Connection.getConnection().prepareStatement("""
                        SELECT *
                        FROM cards
                        WHERE "user"=?
                        AND "inDeck"=true;
                    """);
            statement.setString(1, user);
            ResultSet rs = statement.executeQuery();
            int rows = 0;
            String response;
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                double dmg = rs.getDouble("damage");
                int element = rs.getInt("element");
                if (getParam.contains("plain")) {
                    response = id + "," + name + "," + dmg + "," + element;
                } else {
                    response = "{\"Id\":\"" + id + "\",\"Name\":\"" + name + "\",\"Damage\":\"" + dmg + "\",\"Element\":\"" + element + "\"}";
                }
                try {
                    respond.writeHttpResponse(HttpStatus.OK, response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                rows++;
            }
            rs.close();
            statement.close();
            if (rows == 0) {
                respond.writeHttpResponse(HttpStatus.OK, "No Deck is set for this user");
            }
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();

        }
    }

    //call all relevant functions tp configureDeck
    public void configureDeck(JsonNode node, String user) {
        Responsebuilder respond = Responsebuilder.getInstance();
        if (node.size() > 3) {
            for (int i = 0; i < 4; i++) {
                try {
                    addCardtoDeck(node.get(i).getValueAsText(), user);
                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                throw new Exception("not enough Cards");
            } catch (Exception e) {
                try {
                    respond.writeHttpResponse(HttpStatus.BAD_REQUEST, "not enough cards");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                e.printStackTrace();
            }
        }
    }
    //Update deck of user
    public void addCardtoDeck(String id, String user) throws SQLException, IOException {
        PreparedStatement statement = Connection.getConnection().prepareStatement("""
                   UPDATE cards
                    SET "inDeck"=true
                    WHERE id=?
                    AND  "user"=?;
                """);
        statement.setString(1, id);
        statement.setString(2, user);
        int updatedRows = statement.executeUpdate();
        if (updatedRows > 0) {
                respond.writeHttpResponse(HttpStatus.OK, "Deck configurated");
        } else {
                respond.writeHttpResponse(HttpStatus.BAD_REQUEST, "ERR");

        }
        statement.close();
    }
}

