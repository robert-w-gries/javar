package Absyn;
/**
 * Interface for nodes that permit Visitor Pattern traversals.
 */
public interface Visitable{
    /**
     * Visitor pattern dispatch.
     */
    abstract void accept(Absyn.Visitor v);

}