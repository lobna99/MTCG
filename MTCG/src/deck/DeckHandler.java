package deck;

import cards.Card;
import db.DBconnection;
import org.codehaus.jackson.JsonNode;
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
            int rows=0;
                while (rs.next()) {
                    String id = rs.getString("id");
                    String name = rs.getString("name");
                    double dmg = rs.getDouble("damage");
                    int element = rs.getInt("element");
                    String response = "{\"Id\":\""+id+"\",\"Name\":\""+name+"\",\"Damage\":\""+dmg+"\",\"Element\":\""+element+"\"}";
                    try {
                        respond.writeHttpResponse(HttpStatus.OK, response);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    rows++;
                }
                if(rows==0){
                    respond.writeHttpResponse(HttpStatus.OK, "No Deck is set for this user");
                }
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();

        }
    }
     public void configureDeck(JsonNode node, String user){
         Responsebuilder respond = Responsebuilder.getInstance();
         if(node.size()>3) {
         for(int i = 0; i < 4; i++) {
             try {
                     addCardtoDeck(node.get(i).getValueAsText(), user);
                     respond.writeHttpResponse(HttpStatus.OK, "Deck configurated");
             } catch (SQLException | IOException e) {
                 e.printStackTrace();
                 try {
                     respond.writeHttpResponse(HttpStatus.BAD_REQUEST, "ERR");
                 } catch (IOException ex) {
                     ex.printStackTrace();
                 }
             }
         }
         }else{
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
     public void addCardtoDeck(String id,String user) throws SQLException {
         PreparedStatement statement = null;
             statement = Connection.getConnection().prepareStatement("""
                            UPDATE cards
                             SET "inDeck"=true
                             WHERE id=?
                             AND  "user"=?;
                         """);
             statement.setString(1,id);
             statement.setString(2,user);
             statement.execute();
             statement.close();
     }
}

