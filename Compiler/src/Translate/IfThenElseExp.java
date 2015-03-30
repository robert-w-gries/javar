package Translate;

/**
 * Created with IntelliJ IDEA.
 * User: jchitel
 * Date: 3/22/15
 * Time: 11:21 PM
 */
public class IfThenElseExp extends Exp {

    Exp cond, a, b;
    Temp.Label t = new Temp.Label();
    Temp.Label f = new Temp.Label();
    Temp.Label join = new Temp.Label();

    IfThenElseExp(Exp cond, Exp a, Exp b) {
        this.cond = cond;
        this.a = a;
        this.b = b;
    }

    //TODO
    Tree.Stm unCx(Temp.Label tt, Temp.Label ff) {
        return null;
    }

    //TODO
    @Override
    Tree.Exp unEx() {
        return null;
    }

    //TODO
    @Override
    Tree.Stm unNx() {
        return null;
    }

}
