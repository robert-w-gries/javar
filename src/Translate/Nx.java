package Translate;

/**
 * Created with IntelliJ IDEA.
 * User: jchitel
 * Date: 3/22/15
 * Time: 11:20 PM
 */
public class Nx extends Exp {

    Tree.Stm stm;

    Nx (Tree.Stm s) {
        stm = s;
    }

    Tree.Exp unEx() {
        System.err.println("Error: impossible to unEx() an Nx");
        return null;
    }

    Tree.Stm unNx() {
        return stm;
    }

    Tree.Stm unCx(Temp.Label t, Temp.Label f) {
        System.err.println("Error: impossible to unCx() an Nx");
        return null;
    }
}
