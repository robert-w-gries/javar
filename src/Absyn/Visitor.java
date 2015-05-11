package Absyn;
/**
 * Interface for Visitor Pattern traversals.
 */
public interface Visitor{
    /**
     * Visitor pattern dispatch.
     */
    abstract void visit(java.util.AbstractList<Visitable> list);

    abstract void visit(AddExpr ast);

    abstract void visit(AndExpr ast);

    abstract void visit(ArrayExpr ast);

    abstract void visit(ArrayType ast);

    abstract void visit(AssignStmt ast);

    abstract void visit(BlockStmt ast);

    abstract void visit(BooleanType ast);

    abstract void visit(CallExpr ast);

    abstract void visit(ClassDecl ast);

    abstract void visit(DivExpr ast);

    abstract void visit(EqualExpr ast);

    abstract void visit(FalseExpr ast);

    abstract void visit(FieldExpr ast);

    abstract void visit(Formal ast);

    abstract void visit(GreaterExpr ast);

    abstract void visit(IdentifierExpr ast);

    abstract void visit(IdentifierType ast);

    abstract void visit(IfStmt ast);

    abstract void visit(IntegerLiteral ast);

    abstract void visit(IntegerType ast);

    abstract void visit(LesserExpr ast);

    abstract void visit(MethodDecl ast);

    abstract void visit(MulExpr ast);

    abstract void visit(NegExpr ast);

    abstract void visit(NewArrayExpr ast);

    abstract void visit(NewObjectExpr ast);

    abstract void visit(NotEqExpr ast);

    abstract void visit(NotExpr ast);

    abstract void visit(NullExpr ast);

    abstract void visit(OrExpr ast);

    abstract void visit(Program ast);

    abstract void visit(StringLiteral ast);

    abstract void visit(SubExpr ast);

    abstract void visit(ThisExpr ast);

    abstract void visit(ThreadDecl ast);

    abstract void visit(TrueExpr ast);

    abstract void visit(VarDecl ast);

    abstract void visit(VoidDecl ast);

    abstract void visit(WhileStmt ast);

    abstract void visit(XinuCallExpr ast);

    abstract void visit(XinuCallStmt ast);

}
