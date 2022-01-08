package battle;

import cards.Card;
import cards.Elements;
import cards.Monstercard;
import cards.Spellcard;
import db.DBconnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PrepareCards {
    private final DBconnection Connection;

    public PrepareCards() {
        Connection=DBconnection.getInstance();
    }

    public Card chooseCard(String user){
        //--------------CHOOSE A RANDOM CARD FOR FIGHT FROM USERR--------------
        PreparedStatement statement = null;
        try {
            statement = Connection.getConnection().prepareStatement("""
                        SELECT *
                        from cards
                        WHERE "user"=?
                        AND "inDeck"=true
                        ORDER BY
                             random()
                         LIMIT 1;
                    """);
            statement.setString(1,user);
            ResultSet rs=statement.executeQuery();
            while(rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                double dmg = rs.getDouble("damage");
                int element = rs.getInt("element");
                if (name.contains("Spell")){
                    return new Spellcard(id,name, Elements.values()[element],dmg);
                }else{
                    return new Monstercard(id,name,Elements.values()[element],dmg);
                }
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void removeCard(Card card,String opponent){
        try {
            PreparedStatement update=Connection.getConnection().prepareStatement("""
                        UPDATE cards
                        SET "user"=?
                        WHERE id=?
                        ;
                    """);
           update.setString(1,opponent);
           update.setString(2,card.getId());
           update.execute();
           update.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
