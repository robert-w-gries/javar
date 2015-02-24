package Absyn;

import Types.Type;
/**
 * Curly-brace delimited block of statements.
 */
public class BlockStmt extends Stmt{

    public java.util.LinkedList<Stmt> stmtList;

    public BlockStmt(java.util.LinkedList<Stmt> stmts){
        stmtList = stmts; 
    }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Visitor v){
        v.visit(this);
    }

    @Override
    public Types.Type accept(TypeVisitor v) {
        v.visit(this);
        return null;
    }
}
