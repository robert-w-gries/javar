package Absyn;
/**
 * While Statements.
 */
public class WhileStmt extends Stmt{
    public Expr test;
    public Stmt body;

    public WhileStmt(Expr test, Stmt body){
        this.test = test;
        this.body = body;
    }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Visitor v){
        return; //TODO codavaj!!
    }

}
