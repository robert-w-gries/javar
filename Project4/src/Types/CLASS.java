package Types;

/**
 * Class descriptor.
 */
public class CLASS extends Type {
    public RECORD fields;

    public OBJECT instance;

    public RECORD methods;

    public String name;

    public CLASS parent;

    public CLASS(String name) {
        this.name = name;
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
