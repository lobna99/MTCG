package battle;

import cards.Card;
import stats.StatsHandlerImpl;
import user.User;

import java.io.IOException;
import java.sql.SQLException;

public interface Battle {

    PrepareCardsImpl handleCard = new PrepareCardsImpl();
    BattleHandlerImpl battleHandler = new BattleHandlerImpl();
    StatsHandlerImpl statsHandler = new StatsHandlerImpl();
    final BattleLogger battleLogger = BattleLogger.getInstance();

    public String calculateDMG(Card first, Card second, User Player1, User Player2);

    public String battle(String user, String opponent) throws SQLException, IOException;

    public void checkSpeciality(Card first, Card second);
}
