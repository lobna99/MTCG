package battle;

import cards.Card;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PrepareCards {
    public ArrayList<Card> chooseCard(String user) throws SQLException;
}
