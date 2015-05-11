package frontend.translate;

import arch.Label;
import frontend.translate.irtree.Stm;

/**
 * Created with IntelliJ IDEA.
 * User: jchitel
 * Date: 3/22/15
 * Time: 11:20 PM
 */
public class Nx extends Exp {

    Stm stm;

    Nx (Stm s) {
        stm = s;
    }

    frontend.translate.irtree.Exp unEx() {
        System.err.println("Error: impossible to unEx() an Nx");
        return null;
    }

    Stm unNx() {
        return stm;
    }

    Stm unCx(Label t, Label f) {
        System.err.println("Error: impossible to unCx() an Nx");
        return null;
    }
}
