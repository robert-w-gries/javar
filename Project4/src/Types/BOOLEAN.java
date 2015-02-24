package Types;

/**
 * Basetype "boolean".
 */
public class BOOLEAN extends Type {

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
        return "BOOLEAN";
    }

    public boolean equals(Object obj) {
        return obj instanceof BOOLEAN;
    }
}
