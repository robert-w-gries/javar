package Absyn;
/**
 * Method Declaration structure.
 */
public class MethodDecl extends Absyn.Absyn{
    public MethodDecl(Absyn.Type returnType, boolean synced, java.lang.String name, java.util.LinkedList<Absyn.Formal> params, java.util.LinkedList<Absyn.VarDecl> locals, java.util.LinkedList<Absyn.Stmt> stmts, Absyn.Expr returnVal){
         //TODO codavaj!!
    }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Absyn.Visitor v){
        return; //TODO codavaj!!
    }

}
