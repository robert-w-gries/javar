package Translate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rgries on 3/30/15.
 */
public class Translate {

    private Mips.MipsFrame frame;
    private List<Frag> frags;

    public Translate(Mips.MipsFrame frame) {
        this.frame = frame;
        frags = new ArrayList<Frag>();
    }

    public List<Frag> results() {
        return frags;
    }

}
