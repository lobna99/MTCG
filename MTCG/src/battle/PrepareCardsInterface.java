package battle;

import cards.Card;

public interface PrepareCardsInterface {
    public Card chooseCard(String user);
    public void removeCard(Card card,String opponent);
}
