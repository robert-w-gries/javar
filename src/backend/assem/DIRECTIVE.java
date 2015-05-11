package backend.assem;

/**
 * Created by rgries on 5/8/15.
 */
public class DIRECTIVE extends Instr {

    public DIRECTIVE(String a) {
        assem = a;
        this.use = null;
        this.def = null;
        this.jumps = null;
    }

}
