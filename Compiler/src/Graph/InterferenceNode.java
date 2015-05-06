package Graph;

import Temp.Temp;

import java.util.HashSet;
import java.util.Set;

public class InterferenceNode extends Node<Temp> {

    private Temp color;
    private Set<Temp> coalescedTemps;

    public InterferenceNode(Temp t) {
        super(t);
        coalescedTemps = new HashSet<>();
    }

    public Temp getColor() {
        return color;
    }

    public void setColor(Temp color) {
        this.color = color;
    }


    public Set<Temp> getCoalescedTemps() {
        return coalescedTemps;
    }

    public void addCoalescedTemp(Temp t) {
        coalescedTemps.add(t);
    }
}
