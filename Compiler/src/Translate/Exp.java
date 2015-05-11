package Translate;

/**
 * Created with IntelliJ IDEA.
 * User: jchitel
 * Date: 3/22/15
 * Time: 11:19 PM
 */
public abstract class Exp {

    abstract Tree.Exp unEx();
    abstract Tree.Stm unNx();
    abstract Tree.Stm unCx(Temp.Label t, Temp.Label f);

}
