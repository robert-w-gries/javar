package frontend.typecheck;

/**
 * Type for arrays.
 */
public class ARRAY extends OBJECT {
    public Type element;

    public ARRAY(Type element) {
        super(new CLASS("**ARRAY**"));
        this.element = element;
        this.myClass.instance = this;
        this.myClass.parent = null;
        this.initialized = true;
        this.fields.put(new INT(), "length");
        this.fields.get("length").index = -1;
    }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Visitor v) {
        v.visit(this);
    }

    public boolean coerceTo(Type t) {
        return t instanceof ARRAY && element.coerceTo(((ARRAY)t).element);
    }

    public String toString() {
        return element.toString() + "[]";
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ARRAY)) return false;
        ARRAY arr = (ARRAY) obj;
        return element.equals(arr.element);
    }
}
