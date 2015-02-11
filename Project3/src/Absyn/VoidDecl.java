package Absyn;
/**
 * Run (Void) Method Declaration structure.
 */
public class VoidDecl extends Absyn.MethodDecl{

    public java.lang.String name;
    public java.util.LinkedList<Absyn.VarDecl> locals;
    public java.util.LinkedList<Absyn.Stmt> stmts;

    public VoidDecl(java.lang.String name, java.util.LinkedList<Absyn.VarDecl> locals, java.util.LinkedList<Absyn.Stmt> stmts)
    {   this.name = name;   this.locals = locals;   this.stmts = stmts;   }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Absyn.Visitor v){
        v.visit(this);
        return;
    }

}
