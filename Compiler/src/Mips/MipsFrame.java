package Mips;

import Assem.Instr;
import Frame.Frame;
import Frame.Access;
import Tree.MOVE;
import Tree.Stm;
import Tree.TEMP;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jchitel
 * Date: 3/22/15
 * Time: 11:16 PM
 */
public class MipsFrame extends Frame {

    private Temp.Temp framePointer;
    private int argIndex = 0;
    private LinkedList<Assem.Instr> instructionList;

    public static final Temp.Label _BADPTR = new Temp.Label("_BADPTR");
    public Temp.Label badPtr() { return _BADPTR; }

    public static final Temp.Label _BADSUB = new Temp.Label("_BADSUB");
    public Temp.Label badSub() {
        return _BADSUB;
    }

    public MipsFrame() {
        framePointer = new Temp.Temp(30);
        formals = new LinkedList<Access>();
        actuals = new LinkedList<Access>();
        instructionList = new LinkedList<Instr>();
    }

    public Temp.Temp FP() { return framePointer; }

    public int wordSize() { return 4; }

    int frameOffset = 0;

    public Access allocFormal() {
        // add a formal
        InReg formal = new InReg(new Temp.Temp()); // formals are always temp registers
        formals.add(formal);

        /*
         * frameOffset starts at 0 and increments by word size for every extra
         * arg past the first 4 stored in the a registers.
         */


        // add an actual
        // there are only 4 "a" registers
        if (argIndex < 4) {
            // "a" registers start at $4
            int tempIndex = (argIndex++) + 4;
            actuals.add(new InReg(new Temp.Temp(tempIndex)));
        } else {
            // otherwise we need to allocate space on the stack for remaining args
            frameOffset += wordSize(); // Amount of bytes past the stack
            actuals.add(new InFrame(frameOffset));
            argIndex++;
        }

        // translation only cares about the formal, not the actual
        return formal;
    }

    public Access allocLocal() {
        // Since we have no escaping variables this is all that happens here
        return new InReg(new Temp.Temp());

        /* // Code to be inserted in the case a boolean arg is required or we need escapes.
           // (Should never happen)

    public Access allocLocal(boolean escape){
        if(escape){
            // This case will not happen in minijava, the passed arg to allocLocal here is a ..
            // .. formality.
            frameOffset += wordSize(); // Basically what happens in the else case in allocFormal
            return new InFrame(frameOffset);
        }else{
            return new InReg(new Temp.Temp());
        }

        */

    }

    @Override
    public void procEntryExit1(List<Stm> stms) {
        Temp.Temp ra = new Temp.Temp(), s0 = new Temp.Temp(), s1 = new Temp.Temp(), s2 = new Temp.Temp(),
                s3 = new Temp.Temp(), s4 = new Temp.Temp(), s5 = new Temp.Temp(), s6 = new Temp.Temp(),
                s7 = new Temp.Temp(), fp = new Temp.Temp();

        TEMP _fp = new TEMP(framePointer);

        //////////////////////////////////////////////////////////////////////////////////////////////////////
        // PROLOGUE: needs to happen in reverse order because we are inserting at the beginning of the list //
        //////////////////////////////////////////////////////////////////////////////////////////////////////

        // MOVE(ACTUAL, FORMAL)

        for (int i = formals.size()-1; i >= 0; i--) {
            stms.add(0, new MOVE(actuals.get(i).exp(_fp), formals.get(i).exp(_fp)));
        }

        // CALLEE SAVES

        // frame pointer
        stms.add(0, new MOVE(new TEMP(fp), new TEMP(new Temp.Temp(30))));
        // s registers
        stms.add(0, new MOVE(new TEMP(s7), new TEMP(new Temp.Temp(23))));
        stms.add(0, new MOVE(new TEMP(s6), new TEMP(new Temp.Temp(22))));
        stms.add(0, new MOVE(new TEMP(s5), new TEMP(new Temp.Temp(21))));
        stms.add(0, new MOVE(new TEMP(s4), new TEMP(new Temp.Temp(20))));
        stms.add(0, new MOVE(new TEMP(s3), new TEMP(new Temp.Temp(19))));
        stms.add(0, new MOVE(new TEMP(s2), new TEMP(new Temp.Temp(18))));
        stms.add(0, new MOVE(new TEMP(s1), new TEMP(new Temp.Temp(17))));
        stms.add(0, new MOVE(new TEMP(s0), new TEMP(new Temp.Temp(16))));
        // return address
        stms.add(0, new MOVE(new TEMP(ra), new TEMP(new Temp.Temp(31))));

        // at this point, the return address is saved first, followed by the s registers, then the frame pointer,
        // then by the arguments

        //////////////////
        // END PROLOGUE //
        //////////////////

        ////////////////////////////////////////////////////////////////////////////
        // EPILOGUE: normal order because we are inserting at the end of the list //
        ////////////////////////////////////////////////////////////////////////////

        // CALLEE RESTORES

        // frame pointer
        stms.add(new MOVE(new TEMP(new Temp.Temp(30)), new TEMP(fp)));
        // s registers
        stms.add(new MOVE(new TEMP(new Temp.Temp(23)), new TEMP(s7)));
        stms.add(new MOVE(new TEMP(new Temp.Temp(22)), new TEMP(s6)));
        stms.add(new MOVE(new TEMP(new Temp.Temp(21)), new TEMP(s5)));
        stms.add(new MOVE(new TEMP(new Temp.Temp(20)), new TEMP(s4)));
        stms.add(new MOVE(new TEMP(new Temp.Temp(19)), new TEMP(s3)));
        stms.add(new MOVE(new TEMP(new Temp.Temp(18)), new TEMP(s2)));
        stms.add(new MOVE(new TEMP(new Temp.Temp(17)), new TEMP(s1)));
        stms.add(new MOVE(new TEMP(new Temp.Temp(16)), new TEMP(s0)));
        // return address
        stms.add(new MOVE(new TEMP(new Temp.Temp(31)), new TEMP(ra)));

        // at this point, we save the callee save registers at the top, followed by the arguments
        // at the end of the method, we restore the callee save registers into their original registers

        //////////////////
        // END EPILOGUE //
        //////////////////
    }

    @Override
    public List<Assem.Instr> codeGen(List<Tree.Stm> stms) {

        for (Tree.Stm stm : stms) {
            munchStm(stm);
        }

        return instructionList;
    }

    private void munchStm(Tree.Stm s) {
        // TODO statements that we will see here: CALL, CJUMP, EXP_STM, JUMP, LABEL, MOVE

        // TODO JUMP(NAME) -> b label
        // TODO JUMP(*) -> jr Rs

        // TODO LABEL -> label:

        // TODO MOVE(MEM(+(*,CONST_16)),*) -> sw Rs, I_16(Rd)
        // TODO MOVE(MEM(+(CONST_16,*)),*) -> sw Rs, I_16(Rd)
        // TODO MOVE(MEM(*),*) -> sw Rs, 0(Rd)
        // TODO MOVE(*,*) -> move Rd, Rs

        // TODO CJUMP(op,*,*,label,*) -> beq/bne/blt/bgt/ble/bge  Rs1, Rs2, label

        // TODO CALL(NAME,*) -> (move/sw for params) jal label
        // TODO CALL(*,*) -> (move/sw for params) jalr Rs

        // TODO EXP_STM(CALL(*)) -> just do the call
    }

    private Temp.Temp munchExp(Tree.Exp e) {
        // TODO expressions that we will see here: BINOP, CONST, MEM, NAME, TEMP

        // TODO TEMP -> tn

        // TODO CONST(0) -> zero (t0)
        // TODO CONST(CONST_16) -> addi Rd, zero, I_16
        // TODO CONST(*) -> li Rd, I

        // TODO NAME -> la Rd, label

        // TODO BINOP(PLUS,*,CONST_16) -> addi Rd, Rs, I_16
        // TODO BINOP(PLUS,CONST_16,*) -> addi Rd, Rs, I_16
        // TODO BINOP(PLUS,*,*) -> add Rd, Rs1, Rs2

        // TODO BINOP(MINUS,*,CONST_16) -> addi Rd, Rs, -I_16
        // TODO BINOP(MINUS,*,*) -> sub Rd, Rs1, Rs2

        // TODO BINOP(MUL,*,CONST_2^k) -> sll Rd, Rs, I_k
        // TODO BINOP(MUL,CONST_2^k,*) -> sll Rd, Rs, I_k
        // TODO BINOP(MUL,*,*) -> mulo Rd, Rs1, Rs2

        // TODO BINOP(DIV,*,CONST_2^k) -> sra Rd, Rs, I_k
        // TODO BINOP(DIV,*,*) -> div Rd, Rs1, Rs2

        // TODO BINOP(AND,*,CONST_16) -> andi Rd, Rs, I_16
        // TODO BINOP(AND,CONST_16,*) -> andi Rd, Rs, I_16
        // TODO BINOP(AND,*,*) -> and Rd, Rs1, Rs2

        // TODO BINOP(OR,*,CONST_16) -> ori Rd, Rs, I_16
        // TODO BINOP(OR,CONST_16,*) -> ori Rd, Rs, I_16
        // TODO BINOP(OR,*,*) -> or Rd, Rs1, Rs2

        // TODO BINOP(LSHIFT,*,CONST_16) -> sllv Rd, Rs, I_16
        // TODO BINOP(LSHIFT,*,*) -> sll Rd, Rs1, Rs2

        // TODO BINOP(RSHIFT,*,CONST_16) -> srlv Rd, Rs, I_16
        // TODO BINOP(RSHIFT,*,*) -> srl Rd, Rs1, Rs2

        // TODO BINOP(ARSHIFT,*,CONST_16) -> srav Rd, Rs, I_16
        // TODO BINOP(ARSHIFT,*,*) -> sra Rd, Rs1, Rs2

        // TODO BINOP(BITXOR,*,CONST_16) -> xori Rd, Rs, I_16
        // TODO BINOP(BITXOR,CONST_16,*) -> xori Rd, Rs, I_16
        // TODO BINOP(BITXOR,*,*) -> xor Rd, Rs1, Rs2

        // TODO MEM(+(CONST_16,*)) -> lw Rd, I_16(Rs)
        // TODO MEM(+(*,CONST_16)) -> lw Rd, I_16(Rs)
        // TODO MEM(*) -> lw Rd, 0(Rs)

        return null;

    }

    private void emit(Assem.Instr inst) {

        //TODO: find out if emit only adds inst to list
        //TODO: if emit only adds inst to instructionList, remove the emit function
        instructionList.add(inst);

    }

    public void printFrame(java.io.PrintWriter printOut) {
        String tab = "\t\t"; // 8 spaces. Generally 2 tabs but I wont risk it.
        printOut.print(     "MipsFrame(" + "\n" + // Open frame
                            name + ":"   + "\n");
        printOut.print(     "Formals(");

        for (Access curFormal : formals) {
            printOut.println(curFormal);
            printOut.print(tab);
        }

        printOut.print(     ")"    + "\n");
        printOut.print(     "Actuals(");
        
        for (Access curActual : actuals) {
            printOut.println(curActual);
            printOut.print(tab);
        }

        printOut.print(     ")"    + "\n");
        printOut.print(     "BadPtr(" + badPtr() + ")" + "\n");
        printOut.print(     "BadSub(" + badSub() + ")" + "\n");
        printOut.print(     "maxArgOffset(0)" + "\n"); //TODO: figure out correct way to print maxArgOffset
        printOut.println(")"); // Close frame
    }

}
