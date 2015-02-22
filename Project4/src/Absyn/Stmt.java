package Absyn;
/**
 * Statement abstract class.
 */
public abstract class Stmt extends Absyn{

    public Stmt(){

    }

    /**
     * Visitor pattern dispatch.
     */
    public abstract void accept(Visitor v);

}
