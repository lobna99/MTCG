package deck;

import org.codehaus.jackson.JsonNode;

import java.io.IOException;
import java.sql.SQLException;

public interface DeckHandler {
    public String getDeck(String user, String getParam) throws SQLException, IOException;

    public boolean configureDeck(JsonNode node, String user) throws IOException, SQLException;

    public boolean addCardtoDeck(String id, String user) throws SQLException, IOException;
}
