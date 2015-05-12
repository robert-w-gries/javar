package frontend.translate;

import arch.Access;
import arch.mips.InReg;
import arch.mips.MipsFrame;
import arch.Temp;
import util.SymbolTable;
import arch.Label;
import frontend.translate.irtree.*;
import frontend.parse.ast.*;
import frontend.typecheck.ARRAY;
import frontend.typecheck.FUNCTION;
import frontend.typecheck.OBJECT;
import frontend.typecheck.Type;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by rgries on 3/30/15.
 *
 */
public class Translate{

    private MipsFrame frame;
    private List<Frag> frags;
    private SymbolTable<Access> accesses;
    private SymbolTable<OBJECT> classes;
    private ClassDecl currentClass;

    public Translate() {
        frags = new LinkedList<>();
        accesses = new SymbolTable<>();
        classes = new SymbolTable<>();
        classes.put("**ARRAY**", new ARRAY(null));
    }

    public List<Frag> results() {
        return frags;
    }

    public Exp visit(AddExpr ast){
        frontend.translate.irtree.Exp l = ast.leftExpr.accept(this).unEx();
        frontend.translate.irtree.Exp r = ast.rightExpr.accept(this).unEx();
        return new Ex(new BINOP(BINOP.Operation.PLUS, l, r));
    }

    public Exp visit(AndExpr ast){

        return new IfThenElseExp(ast.leftExpr.accept(this), ast.rightExpr.accept(this), new Ex(new CONST(0)));

    }

    public Exp visit(ArrayExpr ast){
        // get array address
        TEMP array = new TEMP(new Temp());
        MOVE move_array = new MOVE(array, ast.targetExpr.accept(this).unEx());

        // get index value
        TEMP index = new TEMP(new Temp());
        MOVE move_index = new MOVE(index, ast.index.accept(this).unEx());

        // labels for null and OOB checks
        Label nullCheck = new Label(), lowOobCheck = new Label(), highOobCheck = new Label();
        Label nullLabel = new Label("_BADPTR"), oobLabel = new Label("_BADSUB");

        // jumps for null and OOB checks
        CJUMP nullJump = new CJUMP(CJUMP.RelOperation.EQ, array, new CONST(0), nullLabel, nullCheck);
        CJUMP lowOobJump = new CJUMP(CJUMP.RelOperation.LT, index, new CONST(0), oobLabel, lowOobCheck);
        MEM arrayLength = new MEM(new BINOP(BINOP.Operation.PLUS, array, new CONST(-1*frame.wordSize())));
        CJUMP highOobJump = new CJUMP(CJUMP.RelOperation.GE, index, arrayLength, oobLabel, highOobCheck);

        // assemble sequence that gets the array and index and performs checks
        SEQ getAndCheck =
                new SEQ(move_array,
                new SEQ(move_index,
                new SEQ(nullJump,
                new SEQ(new LABEL(nullCheck),
                new SEQ(lowOobJump,
                new SEQ(new LABEL(lowOobCheck),
                             highOobJump))))));

        // assemble result expression
        MEM arraySubscript = new MEM(new BINOP(BINOP.Operation.PLUS,
                array, new BINOP(BINOP.Operation.MUL, index, new CONST(frame.wordSize()))));

        return new Ex(new ESEQ(getAndCheck, new ESEQ(new LABEL(highOobCheck), arraySubscript)));
    }

    public Exp visit(AssignStmt ast){
        return new Nx(new MOVE(ast.leftExpr.accept(this).unEx(), ast.rightExpr.accept(this).unEx()));
    }

    public Exp visit(BlockStmt ast){
        Stm statement = null;
        for (int i = 0; i < ast.stmtList.size(); i++){
            Stm stm = ast.stmtList.get(i).accept(this).unNx();
            if (statement == null) statement = stm;
            else statement = new SEQ(statement, stm);
        }

        if (statement == null) statement = new LABEL(new Label());

        return new Nx(statement);
    }

    public Exp visit(CallExpr ast) {
        // register to store call target
        Temp target = new Temp();

        // assemble tree to get target and null check it
        frontend.translate.irtree.Exp targ = ast.targetExpr.accept(this).unEx();
        MOVE get_target = new MOVE(new TEMP(target), targ);
        Label nullCheckLabel = new Label();
        SEQ nullCheck = new SEQ(new CJUMP(CJUMP.RelOperation.EQ, new TEMP(target), new CONST(0), frame.badPtr(), nullCheckLabel), new LABEL(nullCheckLabel));
        SEQ get_and_check_target = new SEQ(get_target, nullCheck);

        // get memory location of vtable
        MEM vtable = new MEM(new BINOP(BINOP.Operation.PLUS, new TEMP(target), new CONST(-frame.wordSize())));

        // get memory location of method
        OBJECT inst = targ.type;
        int offset = frame.wordSize() * inst.methods.get(ast.methodString).index;
        MEM method = new MEM(new BINOP(BINOP.Operation.PLUS, vtable, new CONST(offset)));

        // assemble arguments list
        frontend.translate.irtree.Exp[] args = new frontend.translate.irtree.Exp[ast.argsList.size()+1];
        args[0] = new TEMP(target);
        for (int i = 0; i < ast.argsList.size(); i++) {
            args[i+1] = ast.argsList.get(i).accept(this).unEx();
        }

        // build result ESEQ
        ESEQ result = new ESEQ(get_and_check_target, new CALL(method, args));

        // set return type
        frontend.typecheck.Type returnType = ((FUNCTION)inst.methods.get(ast.methodString).type).result;
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
        frontend.translate.irtree.Exp l = ast.leftExpr.accept(this).unEx();
        frontend.translate.irtree.Exp r = ast.rightExpr.accept(this).unEx();
        return new Ex(new BINOP(BINOP.Operation.DIV, l, r));
    }

    public Exp visit(EqualExpr ast){
        return new RelCx(CJUMP.RelOperation.EQ, ast.leftExpr.accept(this).unEx(), ast.rightExpr.accept(this).unEx());
    }

    public Exp visitFalse(){
        return new Ex(new CONST(0));
    }

    public Exp visit(FieldExpr ast){
        // register to store call target
        Temp target = new Temp();

        // assemble tree to get target and null check it
        frontend.translate.irtree.Exp targ = ast.target.accept(this).unEx();
        MOVE get_target = new MOVE(new TEMP(target), targ);
        Label nullCheckLabel = new Label();
        CJUMP nullCheck = new CJUMP(CJUMP.RelOperation.EQ, new TEMP(target), new CONST(0), frame.badPtr(), nullCheckLabel);
        SEQ get_and_check_target = new SEQ(get_target, nullCheck);

        // get memory location of field
        OBJECT inst = targ.type;
        int offset = inst.fields.get(ast.field).index * frame.wordSize();
        MEM field = new MEM(new BINOP(BINOP.Operation.PLUS, new TEMP(target), new CONST(offset)));

        // build result ESEQ
        ESEQ result = new ESEQ(get_and_check_target, new ESEQ(new LABEL(nullCheckLabel), field));

        // set return type
        Type returnType = inst.fields.get(ast.field).type;
        if (returnType instanceof OBJECT) result.type = (OBJECT)returnType;

        // return result ESEQ
        return new Ex(result);
    }

    public Exp visit(GreaterExpr ast){
        return new RelCx(CJUMP.RelOperation.GT, ast.leftExpr.accept(this).unEx(), ast.rightExpr.accept(this).unEx());
    }

    public Exp visit(IdentifierExpr ast){
        frontend.translate.irtree.Exp exp;
        if (accesses.get(ast.id) != null) {

            exp = accesses.get(ast.id).exp(new TEMP(frame.FP()));
            if (classes.get(ast.id) != null) {
                exp.type = classes.get(ast.id);
            }

        } else {
            // if it's not a local variable then it's an instance variable
            exp = new FieldExpr(new ThisExpr(), ast.id).accept(this).unEx();
        }
        return new Ex(exp);
    }

    public Exp visit(IfStmt ast){
        Label true_label = new Label(), end_of_else = new Label(), false_label = null;
        SEQ if_block = new SEQ(new SEQ(new LABEL(true_label), ast.thenStm.accept(this).unNx()), new JUMP(end_of_else));

        SEQ if_else;
        if (ast.elseStm == null) {
            if_else = if_block;
        } else {
            false_label = new Label();
            SEQ else_block = new SEQ(new SEQ(new LABEL(false_label), ast.elseStm.accept(this).unNx()), new JUMP(end_of_else));
            if_else = new SEQ(if_block, else_block);
        }

        Stm cjump = ast.test.accept(this).unCx(true_label, false_label == null ? end_of_else : false_label);

        return new Nx(new SEQ(new SEQ(cjump, if_else), new LABEL(end_of_else)));
    }

    public Exp visit(IntegerLiteral ast){
        return new Ex(new CONST(ast.value));
    }

    public Exp visit(LesserExpr ast){
        return new RelCx(CJUMP.RelOperation.LT, ast.leftExpr.accept(this).unEx(), ast.rightExpr.accept(this).unEx());
    }

    public Exp visit(MethodDecl ast){
        frame = new MipsFrame();

        if (ast.name.equals("main") && ast.returnType == null) frame.setName(new Label("main"));
        else frame.setName(new Label(currentClass.name + "." + ast.name));

        accesses.beginScope();
        classes.beginScope();
        accesses.put("**THIS**", frame.allocFormal());
        for (Formal f : ast.params) {
            accesses.put(f.name, frame.allocFormal());
            if (f.checktype instanceof OBJECT) {
                classes.put(f.name, (OBJECT)f.checktype);
            }
        }

        Stm vars = null;
        for (VarDecl var : ast.locals) {
            if (vars == null) vars = var.accept(this).unNx();
            else vars = new SEQ(vars, var.accept(this).unNx());
        }

        Stm stmts = null;
        for (Stmt stmt : ast.stmts) {
            if (stmts == null) stmts = stmt.accept(this).unNx();
            else stmts = new SEQ(stmts, stmt.accept(this).unNx());
        }

        Stm body;
        if (vars == null && stmts == null) {
            body = null;
        } else if (vars == null) {
            body = stmts;
        } else if (stmts == null) {
            body = vars;
        } else {
            body = new SEQ(vars, stmts);
        }

        MOVE returnVal = new MOVE(new TEMP(new Temp(2)), ast.returnVal.accept(this).unEx());

        if (body == null) body = returnVal;
        else body = new SEQ(body, returnVal);

        accesses.endScope();
        classes.endScope();

        frags.add(new ProcFrag(body, frame));

        return new Nx(body);
    }

    public Exp visit(MulExpr ast){
        frontend.translate.irtree.Exp l = ast.leftExpr.accept(this).unEx();
        frontend.translate.irtree.Exp r = ast.rightExpr.accept(this).unEx();
        return new Ex(new BINOP(BINOP.Operation.MUL, l, r));
    }

    public Exp visit(NegExpr ast){
        frontend.translate.irtree.Exp r = ast.expr.accept(this).unEx();
        return new Ex(new BINOP(BINOP.Operation.MINUS, new CONST(0), r));
    }

    public Exp visit(NewArrayExpr ast){
        TEMP size = new TEMP(new Temp());
        // only need first dimension because you can only init one dimension at a time
        MOVE move_size = new MOVE(size, ast.dimensions.get(0).accept(this).unEx());
        CALL call_new = new CALL(new NAME(new Label("_new")), size, size);

        ESEQ result = new ESEQ(move_size, call_new);
        result.type = classes.get("**ARRAY**");

        return new Ex(result);
    }

    public Exp visit(NewObjectExpr ast){
        String class_name = ((IdentifierType)ast.type).id;
        OBJECT inst = classes.get(class_name);

        CONST num_fields = new CONST(inst.fields.count());
        NAME vtable = new NAME(new Label(class_name + "_vtable"));

        CALL result = new CALL(new NAME(new Label("_new")), num_fields, vtable);
        result.type = inst;

        return new Ex(result);
    }

    public Exp visit(NotEqExpr ast){
        return new RelCx(CJUMP.RelOperation.NE, ast.leftExpr.accept(this).unEx(), ast.rightExpr.accept(this).unEx());
    }

    public Exp visit(NotExpr ast){
        frontend.translate.irtree.Exp exp = ast.expr.accept(this).unEx();
        return new Ex(new BINOP(BINOP.Operation.BITXOR, exp, new CONST(1)));
    }

    public Exp visitNull(){
        return new Ex(new CONST(0));
    }

    public Exp visit(OrExpr ast) {

        return new IfThenElseExp(ast.leftExpr.accept(this), new Ex(new CONST(1)), ast.rightExpr.accept(this));

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
        return new Ex(new NAME(l));
    }

    public Exp visit(SubExpr ast){
        frontend.translate.irtree.Exp l = ast.leftExpr.accept(this).unEx();
        frontend.translate.irtree.Exp r = ast.rightExpr.accept(this).unEx();
        return new Ex(new BINOP(BINOP.Operation.MINUS, l, r));
    }

    public Exp visitThis(){
        frontend.translate.irtree.Exp exp = accesses.get("**THIS**").exp(new TEMP(frame.FP()));
        exp.type = currentClass.type.instance;
        return new Ex(exp);
    }

    public Exp visit(ThreadDecl ast){
        return visit((ClassDecl)ast);
    }

    public Exp visitTrue(){
        return new Ex(new CONST(1));
    }

    public Exp visit(VarDecl ast){
        Temp temp = new Temp();
        accesses.put(ast.name, new InReg(temp));

        // we have to keep track of types of variables with identifier types
        if (ast.type instanceof IdentifierType)
            classes.put(ast.name, classes.get(((IdentifierType)ast.type).id));
        else if (ast.type instanceof ArrayType)
            classes.put(ast.name, classes.get("**ARRAY**"));

        frontend.translate.irtree.Exp initial = ast.init == null ? new CONST(0)
                : ast.init.accept(this).unEx();

        return new Nx(new MOVE(new TEMP(temp), initial));
    }

    public Exp visit(VoidDecl ast){
        Stm body = ((Nx)visit((MethodDecl)ast)).stm;
        if (body instanceof SEQ) return new Nx(((SEQ)body).left);
        else {
            // empty body, create label and jump to it for a "no op"
            Label nop = new Label();
            return new Nx(new SEQ(new JUMP(nop), new LABEL(nop)));
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

        SEQ loop = new SEQ(new SEQ(new LABEL(loop_start), ast.body.accept(this).unNx()), new JUMP(jump_back));
        SEQ while_check = new SEQ(new LABEL(jump_back), ast.test.accept(this).unCx(loop_start, loop_done));

        return new Nx(new SEQ(new SEQ(while_check, loop), new LABEL(loop_done)));
    }

    public Exp visit(XinuCallExpr ast){
        return new Ex(new CALL(new NAME(new Label("_" + ast.method))));
    }

    public Exp visit(XinuCallStmt ast){
        frontend.translate.irtree.Exp[] args = new frontend.translate.irtree.Exp[ast.args.size()];
        for (int i = 0; i < ast.args.size(); i++) args[i] = ast.args.get(i).accept(this).unEx();
        return new Nx(new EXP_STM(new CALL(new NAME(new Label("_" + ast.method)), args)));
    }

}
