package cards;

import org.codehaus.jackson.JsonNode;
import packages.Package;

import java.io.IOException;
import java.sql.SQLException;

public interface CardHandler {
    public boolean extractnewcards(JsonNode node, Package pack) throws IOException;

    public boolean addCard(Card card, Package pack) throws IOException;

    public void InsertCard(Card newCard, int id) throws SQLException;

    public String getAllCards(String user) throws IOException, SQLException;
}
