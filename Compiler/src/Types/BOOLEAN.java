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
    }

    public boolean coerceTo(Type t) {
        return t instanceof BOOLEAN;
    }

    public String toString() {
        return "boolean";
    }

    public boolean equals(Object obj) {
        return obj instanceof BOOLEAN;
    }
}
