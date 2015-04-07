package Absyn;
/**
 * Expression abstract class.
 */
public abstract class Expr extends Absyn {
    public Expr(){}

    /**
     * Visitor pattern dispatch.
     */
    public abstract void accept(Visitor v);

}
