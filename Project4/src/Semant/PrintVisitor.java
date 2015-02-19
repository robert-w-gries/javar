package Semant;

import java.util.AbstractList;

/**
 * Created with IntelliJ IDEA.
 * User: jchitel
 * Date: 2/18/15
 * Time: 10:42 PM
 */
public class PrintVisitor implements Types.Visitor, Absyn.Visitor {

    /* Absyn.Visitor implementations */

    @Override
    public void visit(AbstractList<Absyn.Visitable> list) {
    }

    @Override
    public void visit(Absyn.AddExpr ast) {
    }

    @Override
    public void visit(Absyn.AndExpr ast) {
    }

    @Override
    public void visit(Absyn.ArrayExpr ast) {
    }

    @Override
    public void visit(Absyn.ArrayType ast) {
    }

    @Override
    public void visit(Absyn.XinuCallStmt ast) {
    }

    @Override
    public void visit(Absyn.XinuCallExpr ast) {
    }

    @Override
    public void visit(Absyn.WhileStmt ast) {
    }

    @Override
    public void visit(Absyn.VoidDecl ast) {
    }

    @Override
    public void visit(Absyn.VarDecl ast) {
    }

    @Override
    public void visit(Absyn.TrueExpr ast) {
    }

    @Override
    public void visit(Absyn.ThreadDecl ast) {
    }

    @Override
    public void visit(Absyn.ThisExpr ast) {
    }

    @Override
    public void visit(Absyn.SubExpr ast) {
    }

    @Override
    public void visit(Absyn.StringLiteral ast) {
    }

    @Override
    public void visit(Absyn.Program ast) {
    }

    @Override
    public void visit(Absyn.NullExpr ast) {
    }

    @Override
    public void visit(Absyn.OrExpr ast) {
    }

    @Override
    public void visit(Absyn.NotExpr ast) {
    }

    @Override
    public void visit(Absyn.NotEqExpr ast) {
    }

    @Override
    public void visit(Absyn.NewObjectExpr ast) {
    }

    @Override
    public void visit(Absyn.NewArrayExpr ast) {
    }

    @Override
    public void visit(Absyn.NegExpr ast) {
    }

    @Override
    public void visit(Absyn.MulExpr ast) {
    }

    @Override
    public void visit(Absyn.MethodDecl ast) {
    }

    @Override
    public void visit(Absyn.LesserExpr ast) {
    }

    @Override
    public void visit(Absyn.IntegerType ast) {
    }

    @Override
    public void visit(Absyn.IntegerLiteral ast) {
    }

    @Override
    public void visit(Absyn.IfStmt ast) {
    }

    @Override
    public void visit(Absyn.IdentifierType ast) {
    }

    @Override
    public void visit(Absyn.IdentifierExpr ast) {
    }

    @Override
    public void visit(Absyn.GreaterExpr ast) {
    }

    @Override
    public void visit(Absyn.Formal ast) {
    }

    @Override
    public void visit(Absyn.FieldExpr ast) {
    }

    @Override
    public void visit(Absyn.EqualExpr ast) {
    }

    @Override
    public void visit(Absyn.FalseExpr ast) {
    }

    @Override
    public void visit(Absyn.DivExpr ast) {
    }

    @Override
    public void visit(Absyn.ClassDecl ast) {
    }

    @Override
    public void visit(Absyn.CallExpr ast) {
    }

    @Override
    public void visit(Absyn.BooleanType ast) {
    }

    @Override
    public void visit(Absyn.BlockStmt ast) {
    }

    @Override
    public void visit(Absyn.AssignStmt ast) {
    }

    /* Types.Visitor implementations */

    @Override
    public void visit(Types.ARRAY a) {
    }

    @Override
    public void visit(Types.BOOLEAN b) {
    }

    @Override
    public void visit(Types.CLASS c) {
    }

    @Override
    public void visit(Types.FIELD f) {
    }

    @Override
    public void visit(Types.FUNCTION f) {
    }

    @Override
    public void visit(Types.INT i) {
    }

    @Override
    public void visit(Types.NIL n) {
    }

    @Override
    public void visit(Types.OBJECT o) {
    }

    @Override
    public void visit(Types.RECORD r) {
    }

    @Override
    public void visit(Types.STRING s) {
    }

    @Override
    public void visit(Types.VOID v) {
    }
}
