package Absyn;

import Types.CLASS;

/**
 * Class Declaration Blocks
 */
public class ClassDecl extends Absyn{

    public java.util.LinkedList<VarDecl> fields;
    public java.util.LinkedList<MethodDecl> methods;
    public java.lang.String name;
    public java.lang.String parent;
    public CLASS type;

    public ClassDecl(java.lang.String name, java.lang.String parent, java.util.LinkedList<VarDecl> fields, java.util.LinkedList<MethodDecl> methods){
        this.name = name;
        this.parent = parent;
        this.fields = fields;
        this.methods = methods;
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
}
