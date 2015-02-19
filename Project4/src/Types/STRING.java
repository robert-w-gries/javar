package Types;

/**
 * Basetype "String".
 */
public class STRING extends Type {

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Visitor v) {
        return; //TODO codavaj!!
    }

    public boolean coerceTo(Type t) {
        return false; //TODO codavaj!!
    }

    public String toString() {
        return null; //TODO codavaj!!
    }
}
