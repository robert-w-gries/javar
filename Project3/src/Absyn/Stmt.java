package Absyn;
/**
 * Statement abstract class.
 */
public abstract class Stmt extends Absyn{
    public Stmt(){
         //TODO codavaj!!
    }

    /**
     * Visitor pattern dispatch.
     */
    public abstract void accept(Visitor v);

}
