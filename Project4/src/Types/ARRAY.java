package Types;

/**
 * Type for arrays.
 */
public class ARRAY extends Type {
    public Type element;

    public ARRAY(Type element) {
        this.element = element;
    }

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
        if (!(obj instanceof ARRAY)) return false;
        ARRAY arr = (ARRAY) obj;
        return element.equals(arr.element);
    }
}
