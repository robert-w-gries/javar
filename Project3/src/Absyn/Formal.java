package Absyn;
/**
 * Declarations for formal method parameters.
 */
public class Formal extends Absyn{
    public Type type;
    public String name;

    public Formal(Type type, java.lang.String name){
        this.type = type;
        this.name = name;
    }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Visitor v){
        v.visit(this);
        return;
    }

}
