package Translate;

/**
 * Created with IntelliJ IDEA.
 * User: jchitel
 * Date: 3/22/15
 * Time: 11:18 PM
 */
public class ProcFrag extends Frag {

    Tree.Stm body;
    Frame.Frame frame;

    public ProcFrag(Tree.Stm body, Frame.Frame frame) {
        this.body = body;
        this.frame = frame;
    }

}
