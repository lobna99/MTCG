package user;

import cards.Card;

import java.util.ArrayList;

public class User {

    private final String Username;
    private final String Password;
    private int ELO;
    private final int Coins;
    private ArrayList<Card> Cards;
    private int won;
    private int lost;
    private float ratio;
    private int streak;

    public User(String Username, String Password) {
        this.Username = Username;
        this.Password = Password;
        this.ELO = 100;
        this.Coins = 20;
        this.won = 0;
        this.lost = 0;
        this.ratio = 0;
        this.streak = 0;
    }

    public int getCoins() {
        return Coins;
    }

    public void setWon(int won) {
        this.won = won;
    }

    public int getELO() {
        return ELO;
    }

    public void setELO(int Elo) {
        this.ELO = Elo;
    }

    public String getPassword() {
        return Password;
    }

    public String getUsername() {
        return Username;
    }

    public void setLost(int lost) {
        this.lost = lost;
    }

    public ArrayList<Card> getCards() {
        return Cards;
    }

    public void setCards(ArrayList<Card> cards) {
        Cards = cards;
    }

    public int getWon() {
        return won;
    }

    public int getLost() {
        return lost;
    }

    public int getStreak() {
        return streak;
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }

    public float getRatio() {
        return ratio;
    }

    public void setRatio(float ratio) {
        this.ratio = ratio;
    }
}
