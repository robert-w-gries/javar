package Types;

/**
 * Type for fields.
 */
public class FIELD extends Type {
    public int index;

    public String name;

    public Type type;

    public FIELD(Type type, String name, int index) {
        this.type = type;
        this.name = name;
        this.index = index;
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
