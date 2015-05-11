package frontend.parse.ast;

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

    public abstract frontend.typecheck.Type accept(TypeVisitor v);

}
