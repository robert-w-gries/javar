package Types;

/**
 * Type for object with inheritance taken into account.
 */
public class OBJECT extends Type {
    public RECORD fields;

    public RECORD methods;

    public CLASS myClass;

    public boolean initialized;

    public OBJECT(CLASS myClass) {
        this.myClass = myClass;
        this.methods = new RECORD();
        this.fields = new RECORD();
        this.initialized = false;
    }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Visitor v) {
        v.visit(this);
    }

    public boolean coerceTo(Type t) {
        return t instanceof NIL || t instanceof OBJECT && myClass.coerceTo(((OBJECT)t).myClass);
    }

    public String toString() {
        return myClass.toString();
    }
}
