package trading;

import org.codehaus.jackson.JsonNode;

import java.io.IOException;
import java.sql.SQLException;

public interface TradingHandler {
    public String getTrades() throws SQLException, IOException;

    public boolean postTrade(JsonNode node, String user) throws SQLException, IOException;

    public boolean tradeCard(String card, String user) throws SQLException;

    public void deleteTrade(String user, String card) throws SQLException, IOException;

    public String getOwner(String card, String user) throws SQLException, IOException;

    public boolean trade(String card, JsonNode node, String user) throws SQLException, IOException;

    public boolean checkifInDeck(JsonNode node, String user) throws SQLException;

}
