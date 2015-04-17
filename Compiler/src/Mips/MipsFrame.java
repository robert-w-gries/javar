package Mips;

import Assem.OPER;
import Frame.Frame;
import Frame.Access;
import Temp.Temp;
import Temp.Label;
import Tree.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jchitel
 * Date: 3/22/15
 * Time: 11:16 PM
 */
public class MipsFrame extends Frame {

    private Temp framePointer;
    private int argIndex = 0;
    private LinkedList<Assem.Instr> instructionList;

    public static final Label _BADPTR = new Label("_BADPTR");
    public Label badPtr() { return _BADPTR; }

    public static final Label _BADSUB = new Label("_BADSUB");
    public Label badSub() {
        return _BADSUB;
    }

    public MipsFrame() {
        framePointer = new Temp(30);
        formals = new LinkedList<>();
        actuals = new LinkedList<>();
        instructionList = new LinkedList<>();
    }

    public Temp FP() { return framePointer; }

    public int wordSize() { return 4; }

    int frameOffset = 0;

    public Access allocFormal() {
        // add a formal
        InReg formal = new InReg(new Temp()); // formals are always temp registers
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
            actuals.add(new InReg(new Temp(tempIndex)));
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
        return new InReg(new Temp());

        /* // Code to be inserted in the case a boolean arg is required or we need escapes.
           // (Should never happen)

    public Access allocLocal(boolean escape){
        if(escape){
            // This case will not happen in minijava, the passed arg to allocLocal here is a ..
            // .. formality.
            frameOffset += wordSize(); // Basically what happens in the else case in allocFormal
            return new InFrame(frameOffset);
        }else{
            return new InReg(new Temp());
        }

        */

    }

    @Override
    public void procEntryExit1(List<Stm> stms) {
        Temp ra = new Temp(), s0 = new Temp(), s1 = new Temp(), s2 = new Temp(),
                s3 = new Temp(), s4 = new Temp(), s5 = new Temp(), s6 = new Temp(),
                s7 = new Temp(), fp = new Temp();

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
        stms.add(0, new MOVE(new TEMP(fp), new TEMP(new Temp(30))));
        // s registers
        stms.add(0, new MOVE(new TEMP(s7), new TEMP(new Temp(23))));
        stms.add(0, new MOVE(new TEMP(s6), new TEMP(new Temp(22))));
        stms.add(0, new MOVE(new TEMP(s5), new TEMP(new Temp(21))));
        stms.add(0, new MOVE(new TEMP(s4), new TEMP(new Temp(20))));
        stms.add(0, new MOVE(new TEMP(s3), new TEMP(new Temp(19))));
        stms.add(0, new MOVE(new TEMP(s2), new TEMP(new Temp(18))));
        stms.add(0, new MOVE(new TEMP(s1), new TEMP(new Temp(17))));
        stms.add(0, new MOVE(new TEMP(s0), new TEMP(new Temp(16))));
        // return address
        stms.add(0, new MOVE(new TEMP(ra), new TEMP(new Temp(31))));

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
        stms.add(new MOVE(new TEMP(new Temp(30)), new TEMP(fp)));
        // s registers
        stms.add(new MOVE(new TEMP(new Temp(23)), new TEMP(s7)));
        stms.add(new MOVE(new TEMP(new Temp(22)), new TEMP(s6)));
        stms.add(new MOVE(new TEMP(new Temp(21)), new TEMP(s5)));
        stms.add(new MOVE(new TEMP(new Temp(20)), new TEMP(s4)));
        stms.add(new MOVE(new TEMP(new Temp(19)), new TEMP(s3)));
        stms.add(new MOVE(new TEMP(new Temp(18)), new TEMP(s2)));
        stms.add(new MOVE(new TEMP(new Temp(17)), new TEMP(s1)));
        stms.add(new MOVE(new TEMP(new Temp(16)), new TEMP(s0)));
        // return address
        stms.add(new MOVE(new TEMP(new Temp(31)), new TEMP(ra)));

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

        // TODO EXP_STM(*) -> just call munchExp
    }

    private Temp munchExp(Tree.Exp e) {

        if (e instanceof TEMP) {
            return ((TEMP)e).temp;
        } else if (e instanceof CONST) {
            CONST c = (CONST)e;
            // CONST(0) -> zero (aka $0)
            if (c.value == 0) return new Temp(0);
            else if (c.value < 65536) { // CONST(CONST_16)
                Temp t = new Temp();
                emit(OPER.addi_zero(t, c.value));
                return t;
            } else { // CONST(*)
                Temp t = new Temp();
                emit(OPER.li(t, c.value));
                return t;
            }
        } else if (e instanceof NAME) {
            Temp t = new Temp();
            emit(OPER.la(t, ((NAME)e).label.toString()));
            return t;
        } else if (e instanceof BINOP) {
            BINOP b = (BINOP)e;
            Temp t = new Temp();
            switch (b.binop) {
                case PLUS: {
                    if (b.left instanceof CONST && ((CONST)b.left).value < 65536) {
                        emit(OPER.addi(t, munchExp(b.right), ((CONST)b.left).value));
                    } else if (b.right instanceof CONST && ((CONST)b.right).value < 65536) {
                        emit(OPER.addi(t, munchExp(b.left), ((CONST)b.right).value));
                    } else {
                        emit(OPER.add(t, munchExp(b.left), munchExp(b.right)));
                    }
                }
                break;
                case MINUS: {
                    if (b.right instanceof CONST && ((CONST)b.right).value < 65536) {
                        emit(OPER.addi(t, munchExp(b.left), -((CONST)b.right).value));
                    } else {
                        emit(OPER.sub(t, munchExp(b.left), munchExp(b.right)));
                    }
                }
                break;
                case MUL: {
                    int log2 = -1;
                    if (b.left instanceof CONST) log2 = log2(((CONST)b.left).value);
                    else if (b.right instanceof CONST) log2 = log2(((CONST)b.right).value);
                    if (b.left instanceof CONST && log2 != -1) {
                        emit(OPER.sll(t, munchExp(b.right), log2));
                    } else if (b.right instanceof CONST && log2 != -1) {
                        emit(OPER.sll(t, munchExp(b.left), log2));
                    } else {
                        emit(OPER.mulo(t, munchExp(b.left), munchExp(b.right)));
                    }
                }
                break;
                case DIV: {
                    int log2 = -1;
                    if (b.right instanceof CONST) log2 = log2(((CONST)b.right).value);
                    if (b.right instanceof CONST && log2 != -1) {
                        emit(OPER.sra(t, munchExp(b.left), log2));
                    } else {
                        emit(OPER.div(t, munchExp(b.left), munchExp(b.right)));
                    }
                }
                break;
                case AND: case BITAND: { // not sure what Byrlow's looking for, but AND won't be used so just treat them both the same
                    if (b.left instanceof CONST && ((CONST)b.left).value < 65536) {
                        emit(OPER.andi(t, munchExp(b.right), ((CONST)b.left).value));
                    } else if (b.right instanceof CONST && ((CONST)b.right).value < 65536) {
                        emit(OPER.andi(t, munchExp(b.left), ((CONST)b.right).value));
                    } else {
                        emit(OPER.and(t, munchExp(b.left), munchExp(b.right)));
                    }
                }
                break;
                case OR: case BITOR: {// not sure what Byrlow's looking for, but OR won't be used so just treat them both the same
                    if (b.left instanceof CONST && ((CONST)b.left).value < 65536) {
                        emit(OPER.ori(t, munchExp(b.right), ((CONST)b.left).value));
                    } else if (b.right instanceof CONST && ((CONST)b.right).value < 65536) {
                        emit(OPER.ori(t, munchExp(b.left), ((CONST)b.right).value));
                    } else {
                        emit(OPER.or(t, munchExp(b.left), munchExp(b.right)));
                    }
                }
                break;
                case LSHIFT: {
                    if (b.right instanceof CONST && ((CONST)b.right).value < 65536) {
                        emit(OPER.sll(t, munchExp(b.left), ((CONST)b.right).value));
                    } else {
                        emit(OPER.sllv(t, munchExp(b.left), munchExp(b.right)));
                    }
                }
                break;
                case RSHIFT: {
                    if (b.right instanceof CONST && ((CONST)b.right).value < 65536) {
                        emit(OPER.srl(t, munchExp(b.left), ((CONST)b.right).value));
                    } else {
                        emit(OPER.srlv(t, munchExp(b.left), munchExp(b.right)));
                    }
                }
                break;
                case ARSHIFT: {
                    if (b.right instanceof CONST && ((CONST)b.right).value < 65536) {
                        emit(OPER.sra(t, munchExp(b.left), ((CONST)b.right).value));
                    } else {
                        emit(OPER.srav(t, munchExp(b.left), munchExp(b.right)));
                    }
                }
                break;
                case BITXOR: {
                    if (b.left instanceof CONST && ((CONST)b.left).value < 65536) {
                        emit(OPER.ori(t, munchExp(b.right), ((CONST)b.left).value));
                    } else if (b.right instanceof CONST && ((CONST)b.right).value < 65536) {
                        emit(OPER.ori(t, munchExp(b.left), ((CONST)b.right).value));
                    } else {
                        emit(OPER.or(t, munchExp(b.left), munchExp(b.right)));
                    }
                }
                break;
            }
            return t;
        } else if (e instanceof MEM) {
            MEM m = (MEM)e;
            Temp t = new Temp();
            if (m.exp instanceof BINOP && ((BINOP)m.exp).binop == BINOP.Operation.PLUS
                    && ((BINOP)m.exp).left instanceof CONST && ((CONST)((BINOP)m.exp).left).value < 65536) {
                emit(OPER.lw(t, munchExp(((BINOP)m.exp).right), ((CONST)((BINOP)m.exp).left).value));
            } else if (m.exp instanceof BINOP && ((BINOP)m.exp).binop == BINOP.Operation.PLUS
                    && ((BINOP)m.exp).right instanceof CONST && ((CONST)((BINOP)m.exp).right).value < 65536) {
                emit(OPER.lw(t, munchExp(((BINOP)m.exp).left), ((CONST)((BINOP)m.exp).right).value));
            } else {
                emit(OPER.lw(t, munchExp(m.exp), 0));
            }
            return t;
        } else if (e instanceof CALL) {
            CALL c = (CALL)e;
            LinkedList<Temp> uses = new LinkedList<>();

            // ARGS
            for (int i = 0; i < c.args.size(); i++) {
                if (i < 4) uses.add(new Temp(4+i)); // put argument registers into uses list
                munchStm(new MOVE(actuals.get(i).exp(new TEMP(framePointer)), c.args.get(i))); // move each arg into its access
            }

            // CALLER SAVE REGISTERS
            Temp[] defs = new Temp[] {
                    new Temp(31), new Temp(4), new Temp(5), new Temp(6),
                    new Temp(7), new Temp(8), new Temp(9), new Temp(10),
                    new Temp(11), new Temp(12), new Temp(13), new Temp(14),
                    new Temp(15), new Temp(24), new Temp(25), new Temp(2), new Temp(3)
            };

            // JUMP AND LINK
            if (c.func instanceof NAME) {
                emit(OPER.jal(defs, uses.toArray(new Temp[uses.size()]), ((NAME)c.func).label));
            } else {
                uses.add(0, munchExp(c.func));
                emit(OPER.jalr(defs, uses.toArray(new Temp[uses.size()])));
            }
            emit(OPER.call_sink(defs)); // call sink after jal/jalr
        }

        return null;
    }

    private void emit(Assem.Instr inst) {

        instructionList.add(inst);

    }

    private int log2(int n) {
        if ((n & (n - 1)) == 0 && n > 0) { // n == 1, 2, 4, 8, ...
            return 31 - Integer.numberOfLeadingZeros(n);
        } else {
            return -1;
        }
    }

    public void printFrame(java.io.PrintWriter printOut) {
        String tab = "\t\t"; // 8 spaces. Generally 2 tabs but I wont risk it. <- LIAR
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
