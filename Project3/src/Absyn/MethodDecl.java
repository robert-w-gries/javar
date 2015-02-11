package Absyn;
/**
 * Method Declaration structure.
 */
public class MethodDecl extends Absyn.Absyn{

    public Absyn.Type returnType;
    public boolean synced;
    public java.lang.String name;
    public java.util.LinkedList<Absyn.Formal> params;
    public java.util.LinkedList<Absyn.VarDecl> locals;
    public java.util.LinkedList<Absyn.Stmt> stmts;
    public Absyn.Expr returnVal;

    public MethodDecl(Absyn.Type returnType, boolean synced, java.lang.String name, java.util.LinkedList<Absyn.Formal> params, java.util.LinkedList<Absyn.VarDecl> locals, java.util.LinkedList<Absyn.Stmt> stmts, Absyn.Expr returnVal)
    {   this.returnType = returnType;   this.synced = synced;   this.name = name;   for (int i = 0; i < params.size(); i++) this.params.set(i, params.get(i));
        for (int i = 0; i < locals.size(); i++) this.locals.set(i, locals.get(i));  for (int i = 0; i < stmts.size(); i++) this.stmts.set(i, stmts.get(i));
        this.returnVal = returnVal;   }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Absyn.Visitor v){
        v.visit(this);
        return;
    }

}
