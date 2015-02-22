package Types;
/**
 * Interface for Visitor Pattern traversals.
 */
public interface Visitor{
    /**
     * Visitor pattern dispatch.
     */
    abstract void visit(ARRAY a);

    abstract void visit(BOOLEAN b);

    abstract void visit(CLASS c);

    abstract void visit(FIELD f);

    abstract void visit(FUNCTION f);

    abstract void visit(INT i);

    abstract void visit(NIL n);

    abstract void visit(OBJECT o);

    abstract void visit(RECORD r);

    abstract void visit(STRING s);

    abstract void visit(VOID v);

}
