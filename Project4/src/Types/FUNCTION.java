package Types;

import java.util.Iterator;

/**
 * Type for methods.
 */
public class FUNCTION extends Type {
    public RECORD formals;

    private String name;

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
        v.visit(this);
    }

    public FIELD addFormal(Type type, String name) {
        return formals.put(type, name);
    }

    public boolean coerceTo(Type t) {
        return t instanceof FUNCTION && formals.coerceTo(((FUNCTION)t).formals);
    }

    public String toString() {
        String str = result.toString() + " " + name + "(";
        for (FIELD f : formals) {
            str += f.toString();
        }
        return str + ")";
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof FUNCTION)) return false;
        FUNCTION func = (FUNCTION)obj;
        if (!result.equals(func.result)) return false; // return type mismatch
        Iterator<FIELD> thisParams = formals.iterator();
        Iterator<FIELD> superParams = func.formals.iterator();
        while (thisParams.hasNext()) {
            if (!superParams.hasNext()) return false; // override has more parameters than superclass
            if (!thisParams.next().type.equals(superParams.next().type))
                return false; // mismatched type at this parameter
        }
        return !superParams.hasNext(); // if false, override has less params than superclass
    }
}
