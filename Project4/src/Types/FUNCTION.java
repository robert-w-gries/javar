package Types;

/**
 * Type for methods.
 */
public class FUNCTION extends Type {
    public RECORD formals;

    public String name;

    public Type result;

    public Type self;

    public FUNCTION(String name, Type self, RECORD formals, Type result) {
        this.name = name;
        this.self = self;
        this.formals = formals;
        this.result = result;
    }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Visitor v) {
        return; //TODO codavaj!!
    }

    public FIELD addFormal(Type type, String name) {
        return formals.put(type, name);
    }

    public boolean coerceTo(Type t) {
        return false; //TODO codavaj!!
    }

    public String toString() {
        return null; //TODO codavaj!!
    }
}
