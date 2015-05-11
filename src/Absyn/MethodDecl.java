package Absyn;

import java.util.LinkedList;

/**
 * Method Declaration structure.
 */
public class MethodDecl extends Absyn {
    public Type returnType;
    public boolean synced;
    public String name;
    public LinkedList<Formal> params;
    public LinkedList<VarDecl> locals;
    public LinkedList<Stmt> stmts;
    public Expr returnVal;
    public Types.FUNCTION function;

    public MethodDecl(Type returnType, boolean synced, String name, LinkedList<Formal> params,
                      LinkedList<VarDecl> locals, LinkedList<Stmt> stmts, Expr returnVal) {
        this.returnType = returnType;
        this.synced = synced;
        this.name = name;
        this.params = params;
        this.locals = locals;
        this.stmts = stmts;
        this.returnVal = returnVal;
    }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Visitor v){
        v.visit(this);
    }

    @Override
    public Types.Type accept(TypeVisitor v) {
        return v.visit(this);
    }

    public Translate.Exp accept(Translate.Translate t) { return t.visit(this);}
}
