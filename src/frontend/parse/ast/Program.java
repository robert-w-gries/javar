package frontend.parse.ast;

import frontend.translate.Exp;
import frontend.translate.Translate;

/**
 * Goal ::= MainClass ( ClassDeclaration )*
 */
public class Program extends Absyn{
    public java.util.AbstractList<ClassDecl> classes;

    public Program(java.util.AbstractList<ClassDecl> classes){
         this.classes = classes;
    }

    /**
     * Visitor pattern dispatch.
     */
    public void accept(Visitor v){
        v.visit(this);
    }

    @Override
    public frontend.typecheck.Type accept(TypeVisitor v) {
        v.visit(this);
        return null;
    }

    public Exp accept(Translate t) { return t.visit(this);}
}
