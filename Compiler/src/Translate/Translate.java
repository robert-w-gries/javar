package Translate;

import Absyn.*;
import Frame.Access;
import Mips.MipsFrame;
import Symbol.Symbol;
import Symbol.SymbolTable;
import Temp.Label;
import Tree.BINOP;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rgries on 3/30/15.
 *
 */
public class Translate{

    private Mips.MipsFrame frame;
    private List<Frag> frags;
    private SymbolTable<Access> accesses;

    public Translate(Mips.MipsFrame frame) {
        this.frame = frame;
        frags = new ArrayList<Frag>();
        accesses = new SymbolTable<Access>();
    }

    public List<Frag> results() {
        return frags;
    }

    public Exp visit(java.util.AbstractList<Visitable> list){
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

    public Exp visit(AssignStmt ast){
        //return new Nx(new Tree.MOVE(accesses.get(ast.leftExpr.toString()).exp().unEx(), ast.accept(this).unEx());
        return null;
    }

    public Exp visit(BlockStmt ast){
        // convert the statment list into a SEQ tree
        if(ast.stmtList.peekFirst() != null) {
            int curr = 0;
            int next = curr + 1;
            int tail = ast.stmtList.size();
            // statement to start off the tree
            Tree.Stm statement = ast.stmtList.get(curr).accept(this).unNx();
            curr = next;
            while(next++ != tail){
                statement = new Tree.SEQ(statement, ast.stmtList.get(curr).accept(this).unNx());
                curr = next;
            }
        }
        return null;
    }

    public Exp visit(CallExpr ast){
        return null;
    }

    public Exp visit(ClassDecl ast){
        // visit each method, creating a ProcFrag and any needed DataFrags for each
        for (MethodDecl meth : ast.methods) {
            meth.accept(this);
        }
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
        return new Ex(new Tree.CONST(ast.value));
    }

    public Exp visit(LesserExpr ast){
        return null;
    }

    public Exp visit(MethodDecl ast){
        MipsFrame frame = new MipsFrame();
        frame.name = new Temp.Label(ast.name); // TODO classname.methodname, except for main
        accesses.beginScope();
        accesses.put("**THIS**", frame.allocFormal());
        for (Formal f : ast.params) {
            accesses.put(f.name, frame.allocFormal());
        }

        // TODO loop through VarDecls, create a move for each one

        // TODO loop through Stmts, arrange into SEQ tree

        // TODO add a last MOVE(TEMP(t2) returnExp) for the return expression

        accesses.endScope();
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
        return new Ex(new Tree.CONST(0));
    }

    public Exp visit(OrExpr ast){
        return null;
    }

    public Exp visit(Program ast){
        // create data fragments for class vtables
        for (ClassDecl cls : ast.classes) {
            String text = "  .data\n";
            text += cls.name + "_vtable:\n";
            for (MethodDecl meth : cls.methods) {
                text += "  .word " + cls.name + "." + meth.name + "\n";
            }
            frags.add(new DataFrag(text));
        }

        // create proc fragments for the methods in each class
        for (ClassDecl cls : ast.classes) {
            cls.accept(this);
        }
        return null;
    }

    public Exp visit(StringLiteral ast){
        String text = "  .data\n";
        Label l = new Label();
        text += l.toString() + " .asciiz \"" + ast.value + "\"";
        frags.add(new DataFrag(text));
        return new Ex(new Tree.NAME(l));
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
        return visit((ClassDecl)ast);
    }

    public Exp visit(TrueExpr ast){
        return new Ex(new Tree.CONST(1));
    }

    public Exp visit(VarDecl ast){
        return null;
    }

    public Exp visit(VoidDecl ast){
        // TODO call MethodDecl, strip off return value
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
