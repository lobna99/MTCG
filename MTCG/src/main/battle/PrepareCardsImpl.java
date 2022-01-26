package battle;

import cards.Card;
import cards.Elements;
import cards.Monstercard;
import cards.Spellcard;
import db.DBconnectionImpl;
import db.getDBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PrepareCardsImpl implements getDBConnection, PrepareCards {


    public PrepareCardsImpl() {
    }

    public ArrayList<Card> chooseCard(String user) throws SQLException {
        //--------------CHOOSE DECK FOR FIGHT FROM USERR AND RETURN AS ARRAY-------------
        ArrayList<Card> CardsofUser = new ArrayList<>();
        PreparedStatement statement = null;
        statement = DBconnectionImpl.getInstance().getConnection().prepareStatement("""
                    SELECT *
                    from card
                    WHERE "user"=?
                    and "inDeck"=true;
                """);
        statement.setString(1, user);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            String id = rs.getString("id");
            String name = rs.getString("name");
            double dmg = rs.getDouble("damage");
            int element = rs.getInt("element");
            if (name.contains("Spell")) {
                CardsofUser.add(new Spellcard(id, name, Elements.values()[element], dmg));
            } else {
                CardsofUser.add(new Monstercard(id, name, Elements.values()[element], dmg));
            }
        }
        rs.close();
        statement.close();
        DBconnectionImpl.getInstance().getConnection().close();
        return CardsofUser;
    }
}
