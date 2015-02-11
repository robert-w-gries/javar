package Absyn;
/**
 * Type abstract class.
 */
public abstract class Type extends Absyn.Absyn{

    public Type(){}

    /**
     * Visitor pattern dispatch.
     */
    abstract void accept(Absyn.Visitor v);

}
