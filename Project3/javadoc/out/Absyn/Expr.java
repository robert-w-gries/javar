package Absyn;
/**
 * Expression abstract class.
 */
public abstract class Expr extends Absyn.Absyn{
    public Expr(){
         //TODO codavaj!!
    }

    /**
     * Visitor pattern dispatch.
     */
    public abstract void accept(Absyn.Visitor v);

}