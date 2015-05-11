package frontend.parse.ast;

import frontend.translate.Exp;
import frontend.translate.Translate;
import frontend.typecheck.FUNCTION;

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
    public FUNCTION function;

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
    public frontend.typecheck.Type accept(TypeVisitor v) {
        return v.visit(this);
    }

    public Exp accept(Translate t) { return t.visit(this);}
}
