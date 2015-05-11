package frontend.parse.ast;

import frontend.translate.Exp;
import frontend.translate.Translate;

/**
 * Declarations for variables and fields.
 */
public class VarDecl extends Absyn{
    public Type type;
    public String name;
    public Expr init;
    public frontend.typecheck.Type semantType;

    public VarDecl(Type type, java.lang.String name, Expr init){
        this.type = type;
        this.name = name;
        this.init = init;
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
