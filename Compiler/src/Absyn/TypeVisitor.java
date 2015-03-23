package Absyn;

import Types.Type;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jchitel
 * Date: 2/23/15
 * Time: 3:41 PM
 */
public interface TypeVisitor {
    abstract List<Type> visit(java.util.AbstractList<Visitable> list);

    abstract Type visit(AddExpr ast);

    abstract Type visit(AndExpr ast);

    abstract Type visit(ArrayExpr ast);

    abstract Type visit(ArrayType ast);

    abstract void visit(AssignStmt ast);

    abstract void visit(BlockStmt ast);

    abstract Type visit(BooleanType ast);

    abstract Type visit(CallExpr ast);

    abstract Type visit(ClassDecl ast);

    abstract Type visit(DivExpr ast);

    abstract Type visit(EqualExpr ast);

    abstract Type visit(FalseExpr ast);

    abstract Type visit(FieldExpr ast);

    abstract Type visit(Formal ast);

    abstract Type visit(GreaterExpr ast);

    abstract Type visit(IdentifierExpr ast);

    abstract Type visit(IdentifierType ast);

    abstract void visit(IfStmt ast);

    abstract Type visit(IntegerLiteral ast);

    abstract Type visit(IntegerType ast);

    abstract Type visit(LesserExpr ast);

    abstract Type visit(MethodDecl ast);

    abstract Type visit(MulExpr ast);

    abstract Type visit(NegExpr ast);

    abstract Type visit(NewArrayExpr ast);

    abstract Type visit(NewObjectExpr ast);

    abstract Type visit(NotEqExpr ast);

    abstract Type visit(NotExpr ast);

    abstract Type visit(NullExpr ast);

    abstract Type visit(OrExpr ast);

    abstract void visit(Program ast);

    abstract Type visit(StringLiteral ast);

    abstract Type visit(SubExpr ast);

    abstract Type visit(ThisExpr ast);

    abstract Type visit(ThreadDecl ast);

    abstract Type visit(TrueExpr ast);

    abstract Type visit(VarDecl ast);

    abstract Type visit(VoidDecl ast);

    abstract void visit(WhileStmt ast);

    abstract Type visit(XinuCallExpr ast);

    abstract void visit(XinuCallStmt ast);
}
