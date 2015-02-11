package Absyn;
/**
 * Expression abstract class.
 */
public abstract class Expr extends Absyn.Absyn{
    public Expr(){}

    /**
     * Visitor pattern dispatch.
     */
    abstract void accept(Absyn.Visitor v);

}
