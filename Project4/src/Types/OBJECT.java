package Types;

/**
 * Type for object with inheritance taken into account.
 */
public class OBJECT extends Type {
    public RECORD fields;

    public RECORD methods;

    public CLASS myClass;

    public OBJECT(CLASS myClass) {
        this.myClass = myClass;
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
        return;
    }

    public boolean coerceTo(Type t) {
        return false; //TODO codavaj!!
    }

    public String toString() {
        return null; //TODO codavaj!!
    }
}
