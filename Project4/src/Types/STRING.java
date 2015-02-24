package Types;


/**
 * Basetype "String".
 */
public class STRING extends Type {

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
        return "STRING";
    }

    public boolean equals(Object obj) {
        return obj instanceof STRING;
    }
}
