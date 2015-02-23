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
        return;
    }

    public boolean coerceTo(Type t) {
        return t.getClass().equals(this);
    }

    public String toString() {
        return "NIL";
    }
}
