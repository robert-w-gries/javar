package frontend.translate;

import arch.Label;
import frontend.translate.irtree.Stm;

/**
 * Created with IntelliJ IDEA.
 * User: jchitel
 * Date: 3/22/15
 * Time: 11:19 PM
 */
public abstract class Exp {

    abstract frontend.translate.irtree.Exp unEx();
    abstract Stm unNx();
    abstract Stm unCx(Label t, Label f);

}
