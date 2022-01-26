package cards;

import db.DBconnectionImpl;
import db.getDBConnection;
import org.codehaus.jackson.JsonNode;
import packages.Package;
import response.Responsebuilder;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CardHandlerImpl implements getDBConnection, CardHandler {


    public CardHandlerImpl() {
    }

    //extract all cards that are given to as json
    public boolean extractnewcards(JsonNode node, Package pack) {
        if (node.size() == 5) {//a pack consist of 5 cards
            for (int i = 0; i < 5; i++) {
                if (node.get(i).get("Name").getValueAsText().contains("Spell")) {//spell cards always have spell in name
                    String[] sp = node.get(i).get("Name").getValueAsText().split("Spell", 0);//element name is before Spell in string eg "Firespell"
                    Elements elem = null;
                    switch (sp[0]) {//set element for Card
                        case "Fire" -> elem = Elements.Fire;
                        case "Regular" -> elem = Elements.Regular;
                        case "Water" -> elem = Elements.Water;
                    }
                    //create the card and add it to DB
                    Spellcard newSpell = new Spellcard(node.get(i).get("Id").getValueAsText(), node.get(i).get("Name").getValueAsText(), elem, Double.parseDouble(node.get(i).get("Damage").getValueAsText()));
                    if (!addCard(newSpell, pack)) {
                        return false;
                    }
                } else {
                    //Do the same for monstercards
                    String sp = node.get(i).get("Name").getValueAsText();
                    Elements elem = null;
                    if (sp.contains("Fire")) {
                        elem = Elements.Fire;
                    } else if (sp.contains("Water")) {
                        elem = Elements.Water;
                    } else {
                        elem = Elements.Regular;
                    }
                    Monstercard newMonster = new Monstercard(node.get(i).get("Id").getValueAsText(), node.get(i).get("Name").getValueAsText(), elem, Double.parseDouble(node.get(i).get("Damage").getValueAsText()));
                    if (!addCard(newMonster, pack)) {
                        return false;
                    }
                }
            }
        } else {
            return false;
        }
        return true;
    }

    public boolean addCard(Card card, Package pack) {
        try {
            InsertCard(card, pack.getId());
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public void InsertCard(Card newCard, int id) throws SQLException {
        Connection con = DBconnectionImpl.getInstance().getConnection();
        PreparedStatement statement = con.prepareStatement("""
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

    public String getAllCards(String user) throws  SQLException {
        //Get all the cards from user
        if (user != null) {
            PreparedStatement statement = null;
            Connection con = DBconnectionImpl.getInstance().getConnection();
            statement = con.prepareStatement("""
                        SELECT *
                        from cards
                        WHERE "user"=?
                    """);
            statement.setString(1, user);
            ResultSet rs = statement.executeQuery();
            String response = "";
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                double dmg = rs.getDouble("damage");
                int element = rs.getInt("element");
                response += "{\"Id\":\"" + id + "\",\"Name\":\"" + name + "\",\"Damage\":\"" + dmg + "\",\"Element\":\"" + element + "\"}\n";

            }
            rs.close();
            statement.close();
            DBconnectionImpl.getInstance().getConnection().close();
            return response;
        } else {
            return "No token found";
        }

    }
}
