package Absyn;

/**
 * Type abstract class.
 */
public abstract class Type extends Absyn {

    public Type() {

    }

    /**
     * Visitor pattern dispatch.
     */
    public abstract void accept(Visitor v);

    public abstract Types.Type accept(TypeVisitor v);

}
