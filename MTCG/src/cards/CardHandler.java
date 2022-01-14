package cards;
import db.getDBConnection;
import org.codehaus.jackson.JsonNode;
import packages.Package;
import Http.HttpStatus;
import response.Responsebuilder;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CardHandler implements getDBConnection,CardHandlerInterface {


    public CardHandler() {
    }
    public void extractnewcards(JsonNode node,Package pack){
        for (int i = 0; i < 5; i++) {
            if (node.get(i).get("Name").getValueAsText().contains("Spell")) {
                String[] sp = node.get(i).get("Name").getValueAsText().split("Spell", 0);
                Elements elem = null;
                switch (sp[0]) {
                    case "Fire" -> elem = Elements.Fire;
                    case "Regular" -> elem = Elements.Regular;
                    case "Water" -> elem = Elements.Water;
                }
                Spellcard newSpell = new Spellcard(node.get(i).get("Id").getValueAsText(), node.get(i).get("Name").getValueAsText(), elem, Double.parseDouble(node.get(i).get("Damage").getValueAsText()));
                addCard(newSpell,pack);
            } else {
                    String sp = node.get(i).get("Name").getValueAsText();
                    Elements elem = null;
                    if(sp.contains("Fire")){
                        elem=Elements.Fire;
                    }else if(sp.contains("Water")){
                        elem=Elements.Water;
                    }else{
                        elem=Elements.Regular;
                    }
                Monstercard newMonster = new Monstercard(node.get(i).get("Id").getValueAsText(), node.get(i).get("Name").getValueAsText(),elem, Double.parseDouble(node.get(i).get("Damage").getValueAsText()));
                addCard(newMonster,pack);
            }

        }
    }
    public void addCard(Card card,Package pack){
        Responsebuilder response=Responsebuilder.getInstance();
        try {
            pack.pushCard(card);
            InsertCard(card,pack.getId());
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                response.writeHttpResponse(HttpStatus.BAD_REQUEST,"Card already exists");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    public void InsertCard(Card newCard,int id) throws SQLException {
        PreparedStatement statement = Connection.getConnection().prepareStatement("""
                         INSERT INTO cards
                         (id,name,damage,element,packageid)
                         VALUES (?,?,?,?,?);   
                     """);
        statement.setString(1, newCard.getId());
        statement.setString(2, newCard.getName());
        statement.setDouble(3, newCard.getDmg());
        statement.setInt(4, newCard.getElement().getElem());
        statement.setInt(5, id);
        statement.execute();
        statement.close();
    }

    public void getAllCards(String user)  {
        Responsebuilder respond = Responsebuilder.getInstance();
        if(user!=null) {
            PreparedStatement statement = null;
            try {
                statement = Connection.getConnection().prepareStatement("""
                             SELECT *
                             from cards
                             WHERE "user"=?
                         """);
                statement.setString(1, user);
                ResultSet rs=statement.executeQuery();
                while(rs.next()){
                    String id=rs.getString("id");
                    String name=rs.getString("name");
                    double dmg=rs.getDouble("damage");
                    int element=rs.getInt("element");
                    String response="{\"Id\":\""+id+"\",\"Name\":\""+name+"\",\"Damage\":\""+dmg+"\",\"Element\":\""+element+"\"}";
                    try {
                        respond.writeHttpResponse(HttpStatus.OK,response);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                rs.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
            try {
                respond.writeHttpResponse(HttpStatus.BAD_REQUEST,"No token found");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
