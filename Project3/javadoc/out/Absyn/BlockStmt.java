package Absyn;
/**
 * Curly-brace delimited block of statements.
 */
public class BlockStmt extends Absyn.Stmt{
    public BlockStmt(java.util.LinkedList<Absyn.Stmt> stmts){
         //TODO codavaj!!
    }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Absyn.Visitor v){
        return; //TODO codavaj!!
    }

}
