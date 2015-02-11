package Absyn;
/**
 * Statement abstract class.
 */
public abstract class Stmt extends Absyn.Absyn{

    public Stmt(){}

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Absyn.Visitor v){
        v.visit(this);
        return;
    }

}
