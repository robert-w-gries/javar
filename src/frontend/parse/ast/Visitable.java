package frontend.parse.ast;

import frontend.typecheck.Type;

/**
 * Interface for nodes that permit Visitor Pattern traversals.
 */
public interface Visitable{
    /**
     * Visitor pattern dispatch.
     */
    abstract void accept(Visitor v);

    abstract Type accept(TypeVisitor v);
}
