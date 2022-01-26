package trading;

import Http.HttpStatus;
import db.DBconnectionImpl;
import org.codehaus.jackson.JsonNode;
import response.Response;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TradingHandlerImpl implements TradingHandler,Response {

    public TradingHandlerImpl() {
    }

    @Override
    public String getTrades() throws SQLException {
        //Get all current Trades
        PreparedStatement statement = null;
        statement = DBconnectionImpl.getInstance().getConnection().prepareStatement("""
                    SELECT *
                    from trade
                """);
        ResultSet rs = statement.executeQuery();
        int cnt = 0;
        String response = "";
        while (rs.next()) {
            String id = rs.getString("id");
            String Card = rs.getString("Card");
            String type = rs.getString("typeOfCard");
            int minDmg = rs.getInt("minDamage");
            response += "{\"ID\":\"" + id + "\",\"Card\":\"" + Card + "\",\"Type\":\"" + type + "\",\"MinDamage\":\"" + minDmg + "\"}\n";
            ++cnt;
        }
        if (cnt == 0) {
            return "no trading deals";
        }
        rs.close();
        statement.close();
        DBconnectionImpl.getInstance().getConnection().close();
        return response;
    }

    @Override
    public boolean postTrade(JsonNode node, String user) throws SQLException{
        //post Trade of user
        if (!checkifInDeck(node, user)) {
            Connection con = DBconnectionImpl.getInstance().getConnection();
            PreparedStatement statement = con.prepareStatement("""
                        INSERT INTO trade
                        (id,"Card","typeOfCard","minDamage",owner)
                        VALUES (?,?,?,?,?);   
                    """);
            statement.setString(1, node.get("Id").getValueAsText());
            statement.setString(2, node.get("CardToTrade").getValueAsText());
            statement.setString(3, node.get("Type").getValueAsText());
            statement.setInt(4, Integer.parseInt(node.get("MinimumDamage").getValueAsText()));
            statement.setString(5, user);
            statement.execute();
            statement.close();
            con.close();
            return true;
        } else {
            return false;
        }
    }


    @Override
    public boolean tradeCard(String Card, String user) throws SQLException {
        //get the new card traded
        Connection con = DBconnectionImpl.getInstance().getConnection();
        PreparedStatement trade = con.prepareStatement("""
                    UPDATE card
                    SET "user"=?
                    WHERE id=?
                """);
        trade.setString(1, user);
        trade.setString(2, Card);
        int update = trade.executeUpdate();
        trade.close();
        con.close();
        return update > 0;
    }

    @Override
    public void deleteTrade(String user, String card) throws SQLException{
        //delete Trade of user
        Connection con = DBconnectionImpl.getInstance().getConnection();
        PreparedStatement trade = con.prepareStatement("""
                    DELETE
                    from trade
                    WHERE id=?
                    and owner=?
                """);
        trade.setString(1, card);
        trade.setString(2, user);
        trade.execute();
        trade.close();
        con.close();
    }

    @Override
    public String getOwner(String card, String user) throws SQLException {
        //Get the current owner of card thats traded
        PreparedStatement statement = null;
        String owner;
        Connection con = DBconnectionImpl.getInstance().getConnection();
        statement = con.prepareStatement("""
                    SELECT owner
                    from trade
                    WHERE id=?
                    and owner!=?;
                """);
        statement.setString(1, card);
        statement.setString(2, user);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            owner = rs.getString(1);
            rs.close();
            statement.close();
            con.close();
            return owner;
        } else {
            rs.close();
            statement.close();
            con.close();
            return null;
        }
    }

    @Override
    public boolean trade(String card, JsonNode node, String user) throws SQLException, IOException {
        //do the trading
        String owner = getOwner(card, user);
        if (owner != null) {
            tradeCard(node.getValueAsText(), owner);
            tradeCard(card, user);
            respond.writeHttpResponse(HttpStatus.OK, user + " traded " + node.getValueAsText() + " for " + card + " from " + owner);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean checkifInDeck(JsonNode node, String user) throws SQLException {
        //check if the card is already in deck of user
        PreparedStatement statement = null;
        Connection con = DBconnectionImpl.getInstance().getConnection();
        statement = con.prepareStatement("""
                    SELECT "inDeck"
                    from card
                    WHERE "user"=?
                    and id=?;
                """);
        statement.setString(1, user);
        statement.setString(2, node.get("Id").getValueAsText());
        ResultSet rs = statement.executeQuery();
        boolean check = false;
        if (rs.next()) {
            check = rs.getBoolean(1);
        }
        rs.close();
        statement.close();
        con.close();
        return check;
    }
}
