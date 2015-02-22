package Types;

/**
 * Basetype "boolean".
 */
public class BOOLEAN extends Type {

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Visitor v) {
        return; //TODO codavaj!!
    }

    public boolean coerceTo(Type t) {
        return false; //TODO codavaj!!
    }

    public java.lang.String toString() {
        return null; //TODO codavaj!!
    }
}
