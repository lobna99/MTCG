package packages;

import cards.Card;

import java.util.ArrayList;

public class Package {
    private final ArrayList<Card> cards=new ArrayList<Card>();
    private int id;
    private final int cost;

    public Package(){
        this.cost=5;
    }
    public int getCost() {
        return cost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void pushCard(Card card) {
        this.cards.add(card);
    }
}
