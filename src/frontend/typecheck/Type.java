package frontend.typecheck;
/**
 * Parent class of all type descriptors.
 */
public abstract class Type implements Visitable{

    public abstract void accept(Visitor v);

    public abstract boolean coerceTo(Type t);

    public abstract java.lang.String toString();

}
