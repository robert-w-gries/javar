package frontend.typecheck;

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
    }

    public boolean coerceTo(Type t) {
        return t instanceof FIELD && type.coerceTo(((FIELD)t).type);
    }

    public String toString() {
        return type.toString() + " " + name;
    }
}
