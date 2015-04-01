package Translate;

import Tree.CJUMP;
import Tree.CONST;

/**
 * Created with IntelliJ IDEA.
 * User: jchitel
 * Date: 3/22/15
 * Time: 11:19 PM
 */
public class Ex extends Exp {

    Tree.Exp exp;

    Ex (Tree.Exp e) {
        exp = e;
    }

    Tree.Exp unEx() {
        return exp;
    }

    Tree.Stm unNx() {
        return new Tree.EXP_STM(exp);
    }

    Tree.Stm unCx(Temp.Label t, Temp.Label f) {
            return new CJUMP(Tree.CJUMP.RelOperation.NE, exp, new CONST(0), t, f);
    }

}
