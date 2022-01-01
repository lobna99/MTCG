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


}
