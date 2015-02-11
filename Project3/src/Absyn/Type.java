package Absyn;
/**
 * Type abstract class.
 */
public abstract class Type extends Absyn.Absyn{

    public Type(){}

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Absyn.Visitor v){
        v.visit(this);
        return;
    }

}
