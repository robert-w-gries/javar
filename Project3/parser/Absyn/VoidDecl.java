package Absyn;
/**
 * Run (Void) Method Declaration structure.
 */
public class VoidDecl extends Absyn.MethodDecl{
    public VoidDecl(java.lang.String name, java.util.LinkedList<Absyn.VarDecl> locals, java.util.LinkedList<Absyn.Stmt> stmts){
         //TODO codavaj!!
    }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Absyn.Visitor v){
        return; //TODO codavaj!!
    }

}
