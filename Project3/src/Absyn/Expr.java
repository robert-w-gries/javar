package Absyn;
/**
 * Expression abstract class.
 */
public abstract class Expr extends Absyn.Absyn{
    public Expr(){}

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Absyn.Visitor v){
        v.visit(this);
        return;
    }

}
