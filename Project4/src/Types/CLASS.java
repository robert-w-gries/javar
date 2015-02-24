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
        this.fields = new RECORD();
        this.methods = new RECORD();
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

    public boolean equals(Object obj) {
        if (!(obj instanceof CLASS)) return false;
        CLASS cls = (CLASS)obj;
        return name.equals(cls.name);
    }
}
