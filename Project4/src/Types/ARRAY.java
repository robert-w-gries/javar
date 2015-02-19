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
        return; //TODO codavaj!!
    }

    public boolean coerceTo(Type t) {
        return false; //TODO codavaj!!
    }

    public String toString() {
        return null; //TODO codavaj!!
    }
}
