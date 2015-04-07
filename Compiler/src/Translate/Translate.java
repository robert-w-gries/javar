package Translate;

import Absyn.*;
import Frame.Access;
import Mips.MipsFrame;
import Symbol.Symbol;
import Symbol.SymbolTable;
import Temp.Label;
import Tree.BINOP;
import Tree.CJUMP;

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
    private ClassDecl currentClass;

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

    // TODO AndExpr jake
    public Exp visit(AndExpr ast){
        return null;
    }

    public Exp visit(ArrayExpr ast){
        // get array address
        Tree.TEMP array = new Tree.TEMP(new Temp.Temp());
        Tree.MOVE move_array = new Tree.MOVE(array, ast.targetExpr.accept(this).unEx());

        // get index value
        Tree.TEMP index = new Tree.TEMP(new Temp.Temp());
        Tree.MOVE move_index = new Tree.MOVE(index, ast.index.accept(this).unEx());

        // labels for null and OOB checks
        Label nullCheck = new Label(), lowOobCheck = new Label(), highOobCheck = new Label();
        Label nullLabel = new Label("_BADPTR"), oobLabel = new Label("_BADSUB");

        // jumps for null and OOB checks
        Tree.CJUMP nullJump = new CJUMP(CJUMP.RelOperation.EQ, array, new Tree.CONST(0), nullLabel, nullCheck);
        Tree.CJUMP lowOobJump = new CJUMP(CJUMP.RelOperation.LT, index, new Tree.CONST(0), oobLabel, lowOobCheck);
        Tree.MEM arrayLength = new Tree.MEM(new Tree.BINOP(Tree.BINOP.Operation.PLUS, array, new Tree.CONST(-4)));
        Tree.CJUMP highOobJump = new CJUMP(CJUMP.RelOperation.GE, index, arrayLength, oobLabel, highOobCheck);

        // assemble sequence that gets the array and index and performs checks
        Tree.SEQ getAndCheck =
                new Tree.SEQ(move_array,
                new Tree.SEQ(move_index,
                new Tree.SEQ(nullJump,
                new Tree.SEQ(new Tree.LABEL(nullCheck),
                new Tree.SEQ(lowOobJump,
                new Tree.SEQ(new Tree.LABEL(lowOobCheck),
                             highOobJump))))));

        // assemble result expression
        Tree.MEM arraySubscript = new Tree.MEM(new Tree.BINOP(BINOP.Operation.PLUS,
                array, new Tree.BINOP(BINOP.Operation.MUL, index, new Tree.CONST(frame.wordSize()))));
        return new Ex(new Tree.ESEQ(new Tree.LABEL(highOobCheck), arraySubscript));
    }

    // TODO AssignStmt Luke
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

    // TODO CallExpr
    public Exp visit(CallExpr ast){
        return null;
    }

    public Exp visit(ClassDecl ast){
        currentClass = ast;
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
        return new RelCx(CJUMP.RelOperation.EQ, ast.leftExpr.accept(this).unEx(), ast.rightExpr.accept(this).unEx());
    }

    public Exp visit(FalseExpr ast){
        return new Ex(new Tree.CONST(0));
    }

    // TODO FieldExpr
    public Exp visit(FieldExpr ast){
        return null;
    }

    public Exp visit(GreaterExpr ast){
        return new RelCx(CJUMP.RelOperation.GT, ast.leftExpr.accept(this).unEx(), ast.rightExpr.accept(this).unEx());
    }

    // TODO IdentifierExpr jake
    public Exp visit(IdentifierExpr ast){
        return null;
    }

    public Exp visit(IfStmt ast){
        Label true_label = new Label(), end_of_else = new Label(), false_label = null;
        Tree.SEQ if_block = new Tree.SEQ(new Tree.SEQ(new Tree.LABEL(true_label), ast.thenStm.accept(this).unNx()), new Tree.JUMP(end_of_else));

        Tree.SEQ if_else;
        if (ast.elseStm == null) {
            if_else = if_block;
        } else {
            false_label = new Label();
            Tree.SEQ else_block = new Tree.SEQ(new Tree.SEQ(new Tree.LABEL(false_label), ast.elseStm.accept(this).unNx()), new Tree.JUMP(end_of_else));
            if_else = new Tree.SEQ(if_block, else_block);
        }

        Tree.Stm cjump = ast.test.accept(this).unCx(true_label, false_label == null ? end_of_else : false_label);

        return new Nx(new Tree.SEQ(new Tree.SEQ(cjump, if_else), new Tree.LABEL(end_of_else)));
    }

    public Exp visit(IntegerLiteral ast){
        return new Ex(new Tree.CONST(ast.value));
    }

    public Exp visit(LesserExpr ast){
        return new RelCx(CJUMP.RelOperation.LT, ast.leftExpr.accept(this).unEx(), ast.rightExpr.accept(this).unEx());
    }

    // TODO MethodDecl jake
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
        Tree.Exp r = ast.expr.accept(this).unEx();
        return new Ex(new Tree.BINOP(BINOP.Operation.MINUS, new Tree.CONST(0), r));
    }

    public Exp visit(NewArrayExpr ast){
        Tree.TEMP size = new Tree.TEMP(new Temp.Temp());
        // only need first dimension because you can only init one dimension at a time
        Tree.MOVE move_size = new Tree.MOVE(size, ast.dimensions.get(0).accept(this).unEx());
        Tree.CALL call_new = new Tree.CALL(new Tree.NAME(new Label("_new")), size, size);
        return new Ex(new Tree.ESEQ(move_size, call_new));
    }

    // TODO NewObjectExpr jake
    public Exp visit(NewObjectExpr ast){
        return null;
    }

    public Exp visit(NotEqExpr ast){
        return new RelCx(CJUMP.RelOperation.NE, ast.leftExpr.accept(this).unEx(), ast.rightExpr.accept(this).unEx());
    }

    // TODO jake
    public Exp visit(NotExpr ast){
        return null;
    }

    public Exp visit(NullExpr ast){
        return new Ex(new Tree.CONST(0));
    }

    // TODO jake
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

    // TODO VarDecl jake
    public Exp visit(VarDecl ast){
        return null;
    }

    public Exp visit(VoidDecl ast){
        // TODO call MethodDecl, strip off return value
        return null;
    }

    public Exp visit(WhileStmt ast){
        // SEQ(
        //  SEQ(
        //   SEQ(
        //    jump_back
        //    test.unCx(loop_start, loop_done))
        //   SEQ(
        //    SEQ(
        //     loop_start
        //     while_stmt
        //    JUMP(jump_back)))
        //  LABEL(loop done)))

        Label loop_start = new Label(), jump_back = new Label(), loop_done = new Label();

        Tree.SEQ loop = new Tree.SEQ(new Tree.SEQ(new Tree.LABEL(loop_start), ast.body.accept(this).unNx()), new Tree.LABEL(jump_back));
        Tree.SEQ while_check = new Tree.SEQ(new Tree.LABEL(jump_back), ast.test.accept(this).unCx(loop_start, loop_done));

        return new Nx(new Tree.SEQ(new Tree.SEQ(while_check, loop), new Tree.LABEL(loop_done)));
    }

    // TODO XinuCallExpr jake
    public Exp visit(XinuCallExpr ast){
        return null;
    }

    // TODO XinuCallStmt jake
    public Exp visit(XinuCallStmt ast){
        return null;
    }

}
