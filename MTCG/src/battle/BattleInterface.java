package battle;

import cards.Card;
import stats.StatsHandler;

public interface BattleInterface {

    PrepareCards handleCard = new PrepareCards();
    BattleHandler battleHandler = new BattleHandler();
    StatsHandler statsHandler = new StatsHandler();
    final BattleLogger  battleLogger=BattleLogger.getInstance();
    public String calculateDMG(Card first, Card second);
    public void battle(String user, String opponent);
    public void checkSpeciality(Card first, Card second);
}
