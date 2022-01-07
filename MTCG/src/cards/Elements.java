package cards;

public enum Elements {
    Fire(0),
    Water(1),
    Regular(2);

    public final int elem;

    Elements(int element){
        this.elem=element;
    }

    public int getElem(){
        return elem;
    }
}
