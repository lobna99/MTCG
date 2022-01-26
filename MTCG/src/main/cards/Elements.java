package cards;

public enum Elements {
    Fire(0),
    Regular(1),
    Water(2);



    public int elem;

    Elements(int element) {
        this.elem = element;
    }

    public int getElem() {
        return elem;
    }
}
