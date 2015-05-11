package Types;
/**
 * Base type void".
 */
public class VOID extends Type{
    
    /**
     * Visitor pattern dispatch.
     */
    public void accept(Visitor v){
        v.visit(this);
    }

    public boolean coerceTo(Type t){
        return false;
    }

    public java.lang.String toString(){
        return "void";
    }

    public boolean equals(Object obj) {
        return obj instanceof VOID;
    }

}
