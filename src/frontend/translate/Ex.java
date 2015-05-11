package frontend.translate;

import arch.Label;
import frontend.translate.irtree.CJUMP;
import frontend.translate.irtree.CONST;
import frontend.translate.irtree.EXP_STM;
import frontend.translate.irtree.Stm;

/**
 * Created with IntelliJ IDEA.
 * User: jchitel
 * Date: 3/22/15
 * Time: 11:19 PM
 */
public class Ex extends Exp {

    frontend.translate.irtree.Exp exp;

    Ex (frontend.translate.irtree.Exp e) {
        exp = e;
    }

    frontend.translate.irtree.Exp unEx() {
        return exp;
    }

    Stm unNx() {
        return new EXP_STM(exp);
    }

    Stm unCx(Label t, Label f) {
            return new CJUMP(CJUMP.RelOperation.NE, exp, new CONST(0), t, f);
    }

}
