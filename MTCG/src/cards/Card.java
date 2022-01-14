package cards;

public abstract class Card {

    protected String id;
    protected String Name;
    protected Elements Element;
    protected double Dmg;


    public Card(String id,String name, Elements element, double dmg) {
        this.id=id;
        this.Name = name;
        this.Element = element;
        this.Dmg = dmg;
    }

    public void setDmg(double dmg){this.Dmg=dmg;}

    public String getName() {return Name;}

    public Elements getElement() {
        return Element;
    }

    public String getId() {
        return id;
    }

    public double getDmg() {
        return Dmg;
    }
}
