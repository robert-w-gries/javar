package Translate;

/**
 * Created with IntelliJ IDEA.
 * User: jchitel
 * Date: 3/22/15
 * Time: 11:21 PM
 */
public class RelCx extends Cx {

    Tree.CJUMP.RelOperation op;
    Tree.Exp left, right;

    public RelCx(Tree.CJUMP.RelOperation op,  Tree.Exp left, Tree.Exp right) {

        this.op = op;
        this.left = left;
        this.right = right;

    }

    public Tree.Stm unCx(Temp.Label tt, Temp.Label ff) {
        return new Tree.CJUMP(op, left, right, tt, ff);
    }

}
