package cards;

import org.codehaus.jackson.JsonNode;
import packages.Package;

import java.sql.SQLException;

public interface CardHandlerInterface {
    public void extractnewcards(JsonNode node, Package pack);
    public void addCard(Card card,Package pack);
    public void InsertCard(Card newCard,int id) throws SQLException;
    public void getAllCards(String user);
}
