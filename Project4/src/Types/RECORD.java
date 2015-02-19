package Types;

/**
 * Type for aggregate records.
 */
public class RECORD extends Type implements java.lang.Iterable<FIELD> {

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Visitor v) {
        return; //TODO codavaj!!
    }

    public boolean coerceTo(Type t) {
        return false; //TODO codavaj!!
    }

    public FIELD get(java.lang.String name) {
        return null; //TODO codavaj!!
    }

    public java.util.Iterator<FIELD> iterator() {
        return null; //TODO codavaj!!
    }

    public FIELD put(Type type, java.lang.String name) {
        return null; //TODO codavaj!!
    }

    public java.lang.String toString() {
        return null; //TODO codavaj!!
    }
}
