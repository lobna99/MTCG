package battle;

import cards.Card;

public interface BattleInterface {

    final BattleLogger  battleLogger=BattleLogger.getInstance();
    public String calculateDMG(Card first, Card second);
    public void battle(String user, String opponent);
    public void checkSpeciality(Card first, Card second);
}
