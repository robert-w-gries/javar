package Types;

/**
 * Base type "int".
 */
public class INT extends Type {

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Visitor v) {
        v.visit(this);
    }

    public boolean coerceTo(Type t) {
        return t instanceof INT;
    }

    public String toString() {
        return "INT";
    }

    public boolean equals(Object obj) {
        return obj instanceof INT;
    }
}
