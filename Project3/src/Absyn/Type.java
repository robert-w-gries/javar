package Absyn;
/**
 * Type abstract class.
 */
public abstract class Type extends Absyn.Absyn{
    public Type(){
         //TODO codavaj!!
    }

    /**
     * Visitor pattern dispatch.
     */
    public abstract void accept(Absyn.Visitor v);

}
