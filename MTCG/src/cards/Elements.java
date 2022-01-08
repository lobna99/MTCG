package cards;

public enum Elements {
    Fire(0),
    Water(2),
    Regular(1);

    //water-fire=2 regular-water=-1 fire-regular=-1
    public final int elem;

    Elements(int element){
        this.elem=element;
    }

    public int getElem(){
        return elem;
    }
}
