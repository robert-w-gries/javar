package Absyn;
/**
 * Declarations for variables and fields.
 */
public class VarDecl extends Absyn.Absyn{

    public Absyn.Type type;
    public java.lang.String name;
    public Absyn.Expr init;

    public VarDecl(Absyn.Type type, java.lang.String name, Absyn.Expr init)
    {   this.type = type;   this.name = name;   this.init = init;   }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Absyn.Visitor v){
        v.visit(this);
        return;
    }

}
