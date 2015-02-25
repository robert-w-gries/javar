package Types;

/**
 * Type for object with inheritance taken into account.
 */
public class OBJECT extends Type {
    public RECORD fields;

    public RECORD methods;

    private CLASS myClass;

    public OBJECT(CLASS myClass) {
        this.myClass = myClass;
        this.methods = new RECORD();
        this.fields = new RECORD();
    }

    public OBJECT(CLASS myClass, RECORD methods, RECORD fields) {
        this.myClass = myClass;
        this.methods = methods;
        this.fields = fields;
    }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Visitor v) {
        v.visit(this);
    }

    public boolean coerceTo(Type t) {
        return false;
    }

    public String toString() {
        return myClass.toString();
    }
}
