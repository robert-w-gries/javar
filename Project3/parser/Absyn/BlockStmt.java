package Absyn;

/**
 * Curly-brace delimited block of statements.
 */
public class BlockStmt extends Absyn.Stmt{

    public java.util.LinkedList<Absyn.Stmt> stmtList;

    public BlockStmt(java.util.LinkedList<Absyn.Stmt> stmts){
        stmtList = stmts; 
    }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Absyn.Visitor v){
        v.visit(this);
        return; 
    }

}
