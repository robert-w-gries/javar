package Absyn;
/**
 * Statement abstract class.
 */
public abstract class Stmt extends Absyn.Absyn{

    public Stmt(){}

    /**
     * Visitor pattern dispatch.
     */
    abstract void accept(Absyn.Visitor v);

}
