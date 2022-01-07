package main.cards;

public abstract class Card {

    protected String Name;
    protected char Element;
    protected int Dmg;

    public Card() {
        this("noname", 'e', 0);
    }
    public Card(String name, char element, int dmg) {
        this.Name = name;
        this.Element = element;
        this.Dmg = dmg;
    }

    public String getName() {
        return Name;
    }

    public char getElement() {
        return Element;
    }

    public int getDmg() {
        return Dmg;
    }
}
