package Types;
/**
 * Interface for nodes that permit Visitor Pattern traversals.
 */
interface Visitable{
    /**
     * Visitor pattern dispatch.
     */
    abstract void accept(Visitor v);

}
