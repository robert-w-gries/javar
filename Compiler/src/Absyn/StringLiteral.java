package Absyn;
/**
 * String Literals.
 */
public class StringLiteral extends Expr{
    public String value;

    public StringLiteral(java.lang.String value){
        this.value = value;
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
