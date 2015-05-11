package frontend.typecheck;

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
        this.instance = new OBJECT(this);
    }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Visitor v) {
        v.visit(this);
    }

    public boolean coerceTo(Type t) {
        return t instanceof CLASS && (name.equals(((CLASS)t).name) || parent.coerceTo(t));
    }

    public String toString() {
        return name;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof CLASS)) return false;
        CLASS cls = (CLASS)obj;
        return name.equals(cls.name);
    }
}
