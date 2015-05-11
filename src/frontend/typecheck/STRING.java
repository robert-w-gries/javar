package frontend.typecheck;


/**
 * Basetype "String".
 */
public class STRING extends Type {

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Visitor v) {
        v.visit(this);
    }

    public boolean coerceTo(Type t) {
        return t instanceof STRING;
    }

    public String toString() {
        return "String";
    }

    public boolean equals(Object obj) {
        return obj instanceof STRING;
    }
}
