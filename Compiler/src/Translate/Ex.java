package Translate;

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

    //TODO
    Tree.Stm unCx(Temp.Label t, Temp.Label f) {
        return null;
    }

}
