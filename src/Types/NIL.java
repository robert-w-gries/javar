package Types;

/**
 * Base type for "null".
 */
public class NIL extends Type {

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Visitor v) {
        v.visit(this);
    }

    public boolean coerceTo(Type t) {
        return t instanceof NIL || t instanceof OBJECT;
    }

    public String toString() {
        return "NIL";
    }
}
