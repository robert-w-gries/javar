package frontend.parse.ast;

import frontend.translate.Exp;
import frontend.translate.Translate;

import java.util.LinkedList;

/**
 * Run (Void) Method Declaration structure.
 */
public class VoidDecl extends MethodDecl{
    public VoidDecl(java.lang.String name, java.util.LinkedList<VarDecl> locals, java.util.LinkedList<Stmt> stmts){
        super(null, false, name, new LinkedList<Formal>(), locals, stmts, null);
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
