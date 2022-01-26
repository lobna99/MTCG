package packages;

import cards.Card;

import java.util.ArrayList;

public class Package {
    private int id;
    private final int cost;

    public Package() {
        this.cost = 5;
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
}
