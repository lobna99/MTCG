package cards;

import lombok.Getter;

public abstract class Card {
    @Getter
    protected String Name;
    @Getter
    protected char Element;
    @Getter
    protected int Dmg;

    public Card() {
        this("noname", 'e', 0);
    }
    public Card(String name, char element, int dmg) {
        this.Name = name;
        this.Element = element;
        this.Dmg = dmg;
    }



}
