package Translate;

import Absyn.*;
import Frame.Access;
import Mips.InReg;
import Mips.MipsFrame;
import Symbol.SymbolTable;
import Temp.Label;
import Tree.BINOP;
import Tree.CJUMP;
import Tree.SEQ;
import Types.FUNCTION;
import Types.OBJECT;

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
    private SymbolTable<OBJECT> classes;
    private ClassDecl currentClass;

    public Translate() {
        frags = new ArrayList<Frag>();
        accesses = new SymbolTable<Access>();
        classes = new SymbolTable<OBJECT>();
    }

    public List<Frag> results() {
        return frags;
    }

    public Exp visit(AddExpr ast){
        Tree.Exp l = ast.leftExpr.accept(this).unEx();
        Tree.Exp r = ast.rightExpr.accept(this).unEx();
        return new Ex(new Tree.BINOP(BINOP.Operation.PLUS, l, r));
    }

    public Exp visit(AndExpr ast){

        return new IfThenElseExp(ast.leftExpr.accept(this), ast.rightExpr.accept(this), new Ex(new Tree.CONST(0)));

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

        return new Ex(new Tree.ESEQ(getAndCheck, new Tree.ESEQ(new Tree.LABEL(highOobCheck), arraySubscript)));
    }

    public Exp visit(AssignStmt ast){
        return new Nx(new Tree.MOVE(ast.leftExpr.accept(this).unEx(), ast.rightExpr.accept(this).unEx()));
    }

    public Exp visit(BlockStmt ast){
        Tree.Stm statement = null;
        for (int i = 0; i < ast.stmtList.size(); i++){
            Tree.Stm stm = ast.stmtList.get(i).accept(this).unNx();
            if (statement == null) statement = stm;
            else statement = new Tree.SEQ(statement, stm);
        }

        if (statement == null) statement = new Tree.LABEL(new Label());

        return new Nx(statement);
    }

    public Exp visit(CallExpr ast) {
        // register to store call target
        Temp.Temp target = new Temp.Temp();

        // assemble tree to get target and null check it
        Tree.Exp targ = ast.targetExpr.accept(this).unEx();
        Tree.MOVE get_target = new Tree.MOVE(new Tree.TEMP(target), targ);
        Label nullCheckLabel = new Label();
        Tree.SEQ nullCheck = new Tree.SEQ(new Tree.CJUMP(Tree.CJUMP.RelOperation.EQ, new Tree.TEMP(target), new Tree.CONST(0), frame.badPtr(), nullCheckLabel), new Tree.LABEL(nullCheckLabel));
        Tree.SEQ get_and_check_target = new Tree.SEQ(get_target, nullCheck);

        // get memory location of vtable
        Tree.MEM vtable = new Tree.MEM(new Tree.BINOP(Tree.BINOP.Operation.PLUS, new Tree.TEMP(target), new Tree.CONST(-frame.wordSize())));

        // get memory location of method
        OBJECT inst = targ.type;
        int offset = frame.wordSize() * inst.methods.get(ast.methodString).index;
        Tree.MEM method = new Tree.MEM(new Tree.BINOP(BINOP.Operation.PLUS, vtable, new Tree.CONST(offset)));

        // assemble arguments list
        Tree.Exp[] args = new Tree.Exp[ast.argsList.size()+1];
        args[0] = new Tree.TEMP(target);
        for (int i = 0; i < ast.argsList.size(); i++) {
            args[i+1] = ast.argsList.get(i).accept(this).unEx();
        }

        // build result ESEQ
        Tree.ESEQ result = new Tree.ESEQ(get_and_check_target, new Tree.CALL(method, args));

        // set return type
        Types.Type returnType = ((FUNCTION)inst.methods.get(ast.methodString).type).result;
        if (returnType instanceof OBJECT) result.type = (OBJECT)returnType;

        return new Ex(result);
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

    public Exp visitFalse(){
        return new Ex(new Tree.CONST(0));
    }

    public Exp visit(FieldExpr ast){
        // register to store call target
        Temp.Temp target = new Temp.Temp();

        // assemble tree to get target and null check it
        Tree.Exp targ = ast.target.accept(this).unEx();
        Tree.MOVE get_target = new Tree.MOVE(new Tree.TEMP(target), targ);
        Label nullCheckLabel = new Label();
        Tree.CJUMP nullCheck = new Tree.CJUMP(Tree.CJUMP.RelOperation.EQ, new Tree.TEMP(target), new Tree.CONST(0), frame.badPtr(), nullCheckLabel);
        Tree.SEQ get_and_check_target = new Tree.SEQ(get_target, nullCheck);

        // get memory location of field
        OBJECT inst = targ.type;
        int offset = inst.fields.get(ast.field).index * frame.wordSize();
        Tree.MEM field = new Tree.MEM(new Tree.BINOP(BINOP.Operation.PLUS, new Tree.TEMP(target), new Tree.CONST(offset)));

        // build result ESEQ
        Tree.ESEQ result = new Tree.ESEQ(get_and_check_target, new Tree.ESEQ(new Tree.LABEL(nullCheckLabel), field));

        // set return type
        Types.Type returnType = inst.fields.get(ast.field).type;
        if (returnType instanceof OBJECT) result.type = (OBJECT)returnType;

        // return result ESEQ
        return new Ex(result);
    }

    public Exp visit(GreaterExpr ast){
        return new RelCx(CJUMP.RelOperation.GT, ast.leftExpr.accept(this).unEx(), ast.rightExpr.accept(this).unEx());
    }

    public Exp visit(IdentifierExpr ast){
        Tree.Exp exp;
        if (accesses.get(ast.id) != null) {
            exp = accesses.get(ast.id).exp(new Tree.TEMP(frame.FP()));
            if (classes.get(ast.id) != null) exp.type = classes.get(ast.id);
        } else {
            // if it's not a local variable then it's an instance variable
            exp = new FieldExpr(new ThisExpr(), ast.id).accept(this).unEx();
        }
        return new Ex(exp);
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

    public Exp visit(MethodDecl ast){
        frame = new MipsFrame();

        if (ast.name.equals("main") && ast.returnType == null) frame.name = new Temp.Label("main");
        else frame.name = new Temp.Label(currentClass.name + "." + ast.name);

        accesses.beginScope();
        classes.beginScope();
        accesses.put("**THIS**", frame.allocFormal());
        for (Formal f : ast.params) {
            accesses.put(f.name, frame.allocFormal());
        }

        Tree.Stm vars = null;
        for (VarDecl var : ast.locals) {
            if (vars == null) vars = var.accept(this).unNx();
            else vars = new Tree.SEQ(vars, var.accept(this).unNx());
        }

        Tree.Stm stmts = null;
        for (Stmt stmt : ast.stmts) {
            if (stmts == null) stmts = stmt.accept(this).unNx();
            else stmts = new Tree.SEQ(stmts, stmt.accept(this).unNx());
        }

        Tree.Stm body;
        if (vars == null && stmts == null) {
            body = null;
        } else if (vars == null) {
            body = stmts;
        } else if (stmts == null) {
            body = vars;
        } else {
            body = new Tree.SEQ(vars, stmts);
        }

        Tree.MOVE returnVal = new Tree.MOVE(new Tree.TEMP(new Temp.Temp(2)), ast.returnVal.accept(this).unEx());

        if (body == null) body = returnVal;
        else body = new Tree.SEQ(body, returnVal);

        accesses.endScope();
        classes.endScope();

        frags.add(new ProcFrag(body, frame));

        return new Nx(body);
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

    public Exp visit(NewObjectExpr ast){
        String class_name = ((IdentifierType)ast.type).id;
        OBJECT inst = classes.get(class_name);

        Tree.CONST num_fields = new Tree.CONST(inst.fields.count());
        Tree.NAME vtable = new Tree.NAME(new Label(class_name + "_vtable"));

        Tree.CALL result = new Tree.CALL(new Tree.NAME(new Label("_new")), num_fields, vtable);
        result.type = inst;

        return new Ex(result);
    }

    public Exp visit(NotEqExpr ast){
        return new RelCx(CJUMP.RelOperation.NE, ast.leftExpr.accept(this).unEx(), ast.rightExpr.accept(this).unEx());
    }

    public Exp visit(NotExpr ast){
        Tree.Exp exp = ast.expr.accept(this).unEx();
        return new Ex(new Tree.BINOP(BINOP.Operation.BITXOR, exp, new Tree.CONST(1)));
    }

    public Exp visitNull(){
        return new Ex(new Tree.CONST(0));
    }

    public Exp visit(OrExpr ast) {

        return new IfThenElseExp(ast.leftExpr.accept(this), new Ex(new Tree.CONST(1)), ast.rightExpr.accept(this));

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
            classes.put(cls.name, cls.type.instance);
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
        text += l.toString() + ": .asciiz " + ast.value;
        frags.add(new DataFrag(text));
        return new Ex(new Tree.NAME(l));
    }

    public Exp visit(SubExpr ast){
        Tree.Exp l = ast.leftExpr.accept(this).unEx();
        Tree.Exp r = ast.rightExpr.accept(this).unEx();
        return new Ex(new Tree.BINOP(BINOP.Operation.MINUS, l, r));
    }

    public Exp visitThis(){
        Tree.Exp exp = accesses.get("**THIS**").exp(new Tree.TEMP(frame.FP()));
        exp.type = currentClass.type.instance;
        return new Ex(exp);
    }

    public Exp visit(ThreadDecl ast){
        return visit((ClassDecl)ast);
    }

    public Exp visitTrue(){
        return new Ex(new Tree.CONST(1));
    }

    public Exp visit(VarDecl ast){
        Temp.Temp temp = new Temp.Temp();
        accesses.put(ast.name, new InReg(temp));

        // we have to keep track of types of variables with identifier types
        if (ast.type instanceof IdentifierType)
            classes.put(ast.name, classes.get(((IdentifierType)ast.type).id));

        Tree.Exp initial = ast.init == null ? new Tree.CONST(0)
                : ast.init.accept(this).unEx();

        return new Nx(new Tree.MOVE(new Tree.TEMP(temp), initial));
    }

    public Exp visit(VoidDecl ast){
        Tree.Stm body = ((Nx)visit((MethodDecl)ast)).stm;
        if (body instanceof Tree.SEQ) return new Nx(((Tree.SEQ)body).left);
        else {
            // empty body, create label and jump to it for a "no op"
            Label nop = new Label();
            return new Nx(new SEQ(new Tree.JUMP(nop), new Tree.LABEL(nop)));
        }
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

    public Exp visit(XinuCallExpr ast){
        return new Ex(new Tree.CALL(new Tree.NAME(new Label("_" + ast.method))));
    }

    public Exp visit(XinuCallStmt ast){
        Tree.Exp[] args = new Tree.Exp[ast.args.size()];
        for (int i = 0; i < ast.args.size(); i++) args[i] = ast.args.get(i).accept(this).unEx();
        return new Nx(new Tree.EXP_STM(new Tree.CALL(new Tree.NAME(new Label("_" + ast.method)), args)));
    }

}
