package deck;

import org.codehaus.jackson.JsonNode;

import java.io.IOException;
import java.sql.SQLException;

public interface DeckHandlerInterface {
    public void getDeck(String user, String getParam);
    public void configureDeck(JsonNode node, String user);
    public void addCardtoDeck(String id, String user) throws SQLException, IOException;
}
