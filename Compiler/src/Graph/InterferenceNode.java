package Graph;

import Temp.Temp;

public class InterferenceNode extends Node<Temp> {

    private Temp color;

    public InterferenceNode(Temp t) {
        super(t);
    }

    public Temp getColor() {
        return color;
    }

    public void setColor(Temp color) {
        this.color = color;
    }

}
