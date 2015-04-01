package Translate;

import Absyn.*;
import Tree.BINOP;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rgries on 3/30/15.
 */
public class Translate{

    private Mips.MipsFrame frame;
    private List<Frag> frags;

    public Translate(Mips.MipsFrame frame) {
        this.frame = frame;
        frags = new ArrayList<Frag>();
    }

    public List<Frag> results() {
        return frags;
    }

    public Exp visit(java.util.AbstractList<Visitable> list){
        return null;
    }

    public Exp visit(Absyn ast){
        return null;
    }

    public Exp visit(AddExpr ast){
        Tree.Exp l = ast.leftExpr.accept(this).unEx();
        Tree.Exp r = ast.rightExpr.accept(this).unEx();
        return new Ex(new Tree.BINOP(BINOP.Operation.PLUS, l, r));
    }

    public Exp visit(AndExpr ast){
        return null;
    }

    public Exp visit(ArrayExpr ast){
        return null;
    }

    public Exp visit(ArrayType ast){
        return null;
    }

    public Exp visit(AssignStmt ast){
        return null;
    }

    public Exp visit(BlockStmt ast){
        return null;
    }

    public Exp visit(BooleanType ast){
        return null;
    }

    public Exp visit(CallExpr ast){
        return null;
    }

    public Exp visit(ClassDecl ast){
        return null;
    }

    public Exp visit(DivExpr ast){
        Tree.Exp l = ast.leftExpr.accept(this).unEx();
        Tree.Exp r = ast.rightExpr.accept(this).unEx();
        return new Ex(new Tree.BINOP(BINOP.Operation.DIV, l, r));
    }

    public Exp visit(EqualExpr ast){
        return null;
    }

    public Exp visit(FalseExpr ast){
        return null;
    }

    public Exp visit(FieldExpr ast){
        return null;
    }

    public Exp visit(Formal ast){
        return null;
    }

    public Exp visit(GreaterExpr ast){
        return null;
    }

    public Exp visit(IdentifierExpr ast){
        return null;
    }

    public Exp visit(IdentifierType ast){
        return null;
    }

    public Exp visit(IfStmt ast){
        return null;
    }

    public Exp visit(IntegerLiteral ast){
        return null;
    }

    public Exp visit(IntegerType ast){
        return null;
    }

    public Exp visit(LesserExpr ast){
        return null;
    }

    public Exp visit(MethodDecl ast){
        return null;
    }

    public Exp visit(MulExpr ast){
        Tree.Exp l = ast.leftExpr.accept(this).unEx();
        Tree.Exp r = ast.rightExpr.accept(this).unEx();
        return new Ex(new Tree.BINOP(BINOP.Operation.MUL, l, r));
    }

    public Exp visit(NegExpr ast){
        return null;
    }

    public Exp visit(NewArrayExpr ast){
        return null;
    }

    public Exp visit(NewObjectExpr ast){
        return null;
    }

    public Exp visit(NotEqExpr ast){
        return null;
    }

    public Exp visit(NotExpr ast){
        return null;
    }

    public Exp visit(NullExpr ast){
        return null;
    }

    public Exp visit(OrExpr ast){
        return null;
    }

    public Exp visit(Program ast){
        return null;
    }

    public Exp visit(StringLiteral ast){
        return null;
    }

    public Exp visit(SubExpr ast){
        Tree.Exp l = ast.leftExpr.accept(this).unEx();
        Tree.Exp r = ast.rightExpr.accept(this).unEx();
        return new Ex(new Tree.BINOP(BINOP.Operation.MINUS, l, r));
    }

    public Exp visit(ThisExpr ast){
        return null;
    }

    public Exp visit(ThreadDecl ast){
        return null;
    }

    public Exp visit(TrueExpr ast){
        return null;
    }

    public Exp visit(VarDecl ast){
        return null;
    }

    public Exp visit(VoidDecl ast){
        return null;
    }

    public Exp visit(WhileStmt ast){
        return null;
    }

    public Exp visit(XinuCallExpr ast){
        return null;
    }

    public Exp visit(XinuCallStmt ast){
        return null;
    }

}
