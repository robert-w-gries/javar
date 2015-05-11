package frontend.translate;

import arch.Label;
import frontend.translate.irtree.*;
import frontend.translate.irtree.Exp;

/**
 * Created with IntelliJ IDEA.
 * User: jchitel
 * Date: 3/22/15
 * Time: 11:21 PM
 */
public class RelCx extends Cx {

    CJUMP.RelOperation op;
    frontend.translate.irtree.Exp left, right;

    public RelCx(CJUMP.RelOperation op,  Exp left, Exp right) {

        this.op = op;
        this.left = left;
        this.right = right;

    }

    public Stm unCx(Label tt, Label ff) {
        return new CJUMP(op, left, right, tt, ff);
    }

}
