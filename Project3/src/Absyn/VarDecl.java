package Absyn;
/**
 * Declarations for variables and fields.
 */
public class VarDecl extends Absyn{
    public Type type;
    public String name;
    public Expr init;

    public VarDecl(Type type, java.lang.String name, Expr init){
        this.type = type;
        this.name = name;
        this.init = init;
    }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Visitor v){
        return; //TODO codavaj!!
    }

}
