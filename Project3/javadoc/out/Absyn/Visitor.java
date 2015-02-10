package Absyn;
/**
 * Interface for Visitor Pattern traversals.
 */
public interface Visitor{
    /**
     * Visitor pattern dispatch.
     */
    abstract void visit(java.util.AbstractList<Absyn.Visitable> list);

    abstract void visit(Absyn.AddExpr ast);

    abstract void visit(Absyn.AndExpr ast);

    abstract void visit(Absyn.ArrayExpr ast);

    abstract void visit(Absyn.ArrayType ast);

    abstract void visit(Absyn.AssignStmt ast);

    abstract void visit(Absyn.BlockStmt ast);

    abstract void visit(Absyn.BooleanType ast);

    abstract void visit(Absyn.CallExpr ast);

    abstract void visit(Absyn.ClassDecl ast);

    abstract void visit(Absyn.DivExpr ast);

    abstract void visit(Absyn.EqualExpr ast);

    abstract void visit(Absyn.FalseExpr ast);

    abstract void visit(Absyn.FieldExpr ast);

    abstract void visit(Absyn.Formal ast);

    abstract void visit(Absyn.GreaterExpr ast);

    abstract void visit(Absyn.IdentifierExpr ast);

    abstract void visit(Absyn.IdentifierType ast);

    abstract void visit(Absyn.IfStmt ast);

    abstract void visit(Absyn.IntegerLiteral ast);

    abstract void visit(Absyn.IntegerType ast);

    abstract void visit(Absyn.LesserExpr ast);

    abstract void visit(Absyn.MethodDecl ast);

    abstract void visit(Absyn.MulExpr ast);

    abstract void visit(Absyn.NegExpr ast);

    abstract void visit(Absyn.NewArrayExpr ast);

    abstract void visit(Absyn.NewObjectExpr ast);

    abstract void visit(Absyn.NotEqExpr ast);

    abstract void visit(Absyn.NotExpr ast);

    abstract void visit(Absyn.NullExpr ast);

    abstract void visit(Absyn.OrExpr ast);

    abstract void visit(Absyn.Program ast);

    abstract void visit(Absyn.StringLiteral ast);

    abstract void visit(Absyn.SubExpr ast);

    abstract void visit(Absyn.ThisExpr ast);

    abstract void visit(Absyn.ThreadDecl ast);

    abstract void visit(Absyn.TrueExpr ast);

    abstract void visit(Absyn.VarDecl ast);

    abstract void visit(Absyn.VoidDecl ast);

    abstract void visit(Absyn.WhileStmt ast);

    abstract void visit(Absyn.XinuCallExpr ast);

    abstract void visit(Absyn.XinuCallStmt ast);

}
