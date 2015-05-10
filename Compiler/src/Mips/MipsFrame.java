package Mips;

import Assem.*;
import Frame.Frame;
import Frame.Access;
import Temp.Temp;
import Temp.Label;
import Tree.*;
import Tree.LABEL;
import Tree.MOVE;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: jchitel
 * Date: 3/22/15
 * Time: 11:16 PM
 */
public class MipsFrame extends Frame {

    private Temp framePointer;
    private int argIndex = 0;
    public int maxArgOffset = 0;
    private int frameOffset = 0;
    private LinkedList<Assem.Instr> instructionList;

    private static final int NUM_REGS = 32;
    private static Set<Temp> availableRegs;
    private static Set<Temp> reservedRegs;
    private static Map<Temp, String> tempMap;

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

        tempMap = new HashMap<>();
        initTempMap();

        reservedRegs = new HashSet<>();
        initReservedRegs();

        availableRegs = new HashSet<>();
        initAvaialableRegs();

    }

    private void initReservedRegs() {
        reservedRegs.add(new Temp(0)); //zero
        reservedRegs.add(new Temp(1)); //MOVE related meta-reg
        reservedRegs.add(new Temp(26)); //kernel reg
        reservedRegs.add(new Temp(27)); //kernel reg
        reservedRegs.add(new Temp(28)); //gp
        reservedRegs.add(new Temp(29)); //sp
    }

    private void initAvaialableRegs() {

        //grab all regs from tempMap
        availableRegs = new HashSet<>(tempMap.keySet());
        Iterator<Temp> it = availableRegs.iterator();
        while (it.hasNext()) {
            Temp t = it.next();
            // if reg is reserved, remove from set
            if (reservedRegs.contains(t)) it.remove();
        }

    }

    private void initTempMap() {
        tempMap.put(new Temp(0), "zero");
        tempMap.put(new Temp(1), "at");
        tempMap.put(new Temp(2), "v0");
        tempMap.put(new Temp(3), "v1");
        tempMap.put(new Temp(4), "a0");
        tempMap.put(new Temp(5), "a1");
        tempMap.put(new Temp(6), "a2");
        tempMap.put(new Temp(7), "a3");
        tempMap.put(new Temp(8), "t0");
        tempMap.put(new Temp(9), "t1");
        tempMap.put(new Temp(10), "t2");
        tempMap.put(new Temp(11), "t3");
        tempMap.put(new Temp(12), "t4");
        tempMap.put(new Temp(13), "t5");
        tempMap.put(new Temp(14), "t6");
        tempMap.put(new Temp(15), "t7");
        tempMap.put(new Temp(16), "s0");
        tempMap.put(new Temp(17), "s1");
        tempMap.put(new Temp(18), "s2");
        tempMap.put(new Temp(19), "s3");
        tempMap.put(new Temp(20), "s4");
        tempMap.put(new Temp(21), "s5");
        tempMap.put(new Temp(22), "s6");
        tempMap.put(new Temp(23), "s7");
        tempMap.put(new Temp(24), "t8");
        tempMap.put(new Temp(25), "t9");
        tempMap.put(new Temp(26), "k0");
        tempMap.put(new Temp(27), "k1");
        tempMap.put(new Temp(28), "gp");
        tempMap.put(new Temp(29), "sp");
        tempMap.put(new Temp(30), "fp");
        tempMap.put(new Temp(31), "ra");
    }

    public static String getTempName(Temp t) {
        return tempMap.get(t);
    }

    public Temp FP() { return framePointer; }

    public int wordSize() { return 4; }

    public int getFrameSize() {
        return frameOffset;
    }

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
        frameOffset += wordSize();
        return new InFrame(frameOffset);

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

        TEMP _sp = new TEMP(new Temp(29));

        //////////////////////////////////////////////////////////////////////////////////////////////////////
        // PROLOGUE: needs to happen in reverse order because we are inserting at the beginning of the list //
        //////////////////////////////////////////////////////////////////////////////////////////////////////

        // MOVE(ACTUAL, FORMAL)

        for (int i = formals.size()-1; i >= 0; i--) {
            stms.add(0, new MOVE(formals.get(i).exp(_sp), actuals.get(i).exp(_sp)));
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
    public void procEntryExit2(List<Instr> instrs) {
        List<Temp> returnUses = new ArrayList<>();
        returnUses.add(new Temp(0));
        returnUses.add(new Temp(31));
        returnUses.add(new Temp(29));
        returnUses.add(new Temp(30));
        returnUses.add(new Temp(16));
        returnUses.add(new Temp(17));
        returnUses.add(new Temp(18));
        returnUses.add(new Temp(19));
        returnUses.add(new Temp(20));
        returnUses.add(new Temp(21));
        returnUses.add(new Temp(22));
        returnUses.add(new Temp(23));
        returnUses.add(new Temp(2));
        returnUses.add(new Temp(3));
        instrs.add(new Assem.OPER("// Return from " + name.toString(), null, returnUses, null));
        List<Temp> ra = new LinkedList<>();
        ra.add(new Temp(31));
        instrs.add(new Assem.OPER("jr `s0", null, ra, null));
    }

    @Override
    public void procEntryExit3(List<Instr> instrs) {

        int frameSize = frameOffset+maxArgOffset;
        String frameSizeString = this.name.toString() + "_framesize";
        List<Temp> sp = new ArrayList<>();
        sp.add(new Temp(29));

        List<Instr> entryInstrs = new LinkedList<>();
        entryInstrs.add(new Assem.OPER(".text"));
        entryInstrs.add(new Assem.OPER(".align\t4"));
        entryInstrs.add(new Assem.OPER(".globl\t" + this.name.toString()));
        entryInstrs.add(new Assem.LABEL(this.name.toString(), this.name));
        entryInstrs.add(new Assem.DIRECTIVE(this.name.toString() + "_framesize=" + frameSize));
        if (frameSize > 0) {
            entryInstrs.add(new Assem.OPER("addiu `d0, `s0, -" + frameSizeString, sp, sp, null));
            instrs.add(instrs.size()-1, new Assem.OPER("addiu `d0, `s0, " + frameSizeString, sp, sp, null));
        }

        instrs.addAll(0, entryInstrs);

    }

    @Override
    public List<Assem.Instr> codeGen(List<Tree.Stm> stms) {

        for (Tree.Stm stm : stms) {
            munchStm(stm);
        }

        return instructionList;
    }

    private void munchStm(Tree.Stm s) {

        if (s instanceof JUMP) {

            JUMP jumpS = (JUMP)s;
            if (jumpS.exp instanceof NAME) {
                emit(OPER.b(jumpS.target));
            } else {
                emit(OPER.jr(munchExp(jumpS.exp)));
            }


        }

        else if (s instanceof LABEL) {
            emit(new Assem.LABEL(((LABEL)s).label.toString(), ((LABEL)s).label));
        } else if (s instanceof MOVE) {
            MOVE m = (MOVE)s;

            if (m.dst instanceof MEM) {
                MEM dst = (MEM)m.dst;
                if (dst.exp instanceof BINOP && ((BINOP)dst.exp).binop == BINOP.Operation.PLUS
                        && ((BINOP)dst.exp).left instanceof CONST && ((CONST)((BINOP)dst.exp).left).value < 65536) {
                    emit(OPER.sw(munchExp(m.src), munchExp(((BINOP)dst.exp).right), ((CONST)((BINOP)dst.exp).left).value, name.toString()));
                } else if (dst.exp instanceof BINOP && ((BINOP)dst.exp).binop == BINOP.Operation.PLUS
                        && ((BINOP)dst.exp).right instanceof CONST && ((CONST)((BINOP)dst.exp).right).value < 65536) {
                    emit(OPER.sw(munchExp(m.src), munchExp(((BINOP)dst.exp).left), ((CONST)((BINOP)dst.exp).right).value, name.toString()));
                } else {
                    emit(OPER.sw(munchExp(m.src), munchExp(dst.exp), 0, name.toString()));
                }
            } else {
                emit(new Assem.MOVE("move `d0, `s0", munchExp(m.dst), munchExp(m.src)));
            }
        } else if (s instanceof CJUMP) {
            CJUMP c = (CJUMP)s;
            switch (c.relop) {
                case EQ: {
                    emit(OPER.beq(munchExp(c.left), munchExp(c.right), c.iftrue, c.iffalse));
                }
                break;
                case NE: {
                    emit(OPER.bne(munchExp(c.left), munchExp(c.right), c.iftrue, c.iffalse));
                }
                break;
                case LT: {
                    emit(OPER.blt(munchExp(c.left), munchExp(c.right), c.iftrue, c.iffalse));
                }
                break;
                case GT: {
                    emit(OPER.bgt(munchExp(c.left), munchExp(c.right), c.iftrue, c.iffalse));
                }
                break;
                case LE: {
                    emit(OPER.ble(munchExp(c.left), munchExp(c.right), c.iftrue, c.iffalse));
                }
                break;
                case GE: {
                    emit(OPER.bge(munchExp(c.left), munchExp(c.right), c.iftrue, c.iffalse));
                }
                break;
            }
        } else if (s instanceof EXP_STM) {
            munchExp(((EXP_STM)s).exp);
        }
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
                        emit(OPER.xori(t, munchExp(b.right), ((CONST)b.left).value));
                    } else if (b.right instanceof CONST && ((CONST)b.right).value < 65536) {
                        emit(OPER.xori(t, munchExp(b.left), ((CONST)b.right).value));
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
                emit(OPER.lw(t, munchExp(((BINOP)m.exp).right), ((CONST)((BINOP)m.exp).left).value, name.toString()));
            } else if (m.exp instanceof BINOP && ((BINOP)m.exp).binop == BINOP.Operation.PLUS
                    && ((BINOP)m.exp).right instanceof CONST && ((CONST)((BINOP)m.exp).right).value < 65536) {
                emit(OPER.lw(t, munchExp(((BINOP)m.exp).left), ((CONST)((BINOP)m.exp).right).value, name.toString()));
            } else {
                emit(OPER.lw(t, munchExp(m.exp), 0, name.toString()));
            }
            return t;
        } else if (e instanceof CALL) {
            CALL c = (CALL)e;
            LinkedList<Temp> uses = new LinkedList<>();

            if (c.args.size()*wordSize() > maxArgOffset) {
                maxArgOffset = c.args.size()*wordSize();
            }

            // ARGS
            for (int i = 0; i < c.args.size(); i++) {
                if (i < 4) {
                    uses.add(new Temp(4+i)); // put argument registers into uses list
                    munchStm(new MOVE(new TEMP(new Temp(4+i)), c.args.get(i)));
                } else {
                    munchStm(new MOVE(new MEM(new BINOP(BINOP.Operation.PLUS, new CONST(i*wordSize()), new TEMP(framePointer))), c.args.get(i)));
                }
                //munchStm(new MOVE(actuals.get(i).exp(new TEMP(framePointer)), c.args.get(i))); // move each arg into its access
            }

            // CALLER SAVE REGISTERS
            List<Temp> defs = Arrays.asList(new Temp(31), new Temp(4), new Temp(5),
                    new Temp(6), new Temp(7), new Temp(8), new Temp(9), new Temp(10),
                    new Temp(11), new Temp(12), new Temp(13), new Temp(14), new Temp(15),
                    new Temp(24), new Temp(25), new Temp(2), new Temp(3));

            // JUMP AND LINK
            if (c.func instanceof NAME) {
                emit(OPER.jal(defs, uses, ((NAME)c.func).label));
            } else {
                uses.add(0, munchExp(c.func));
                emit(OPER.jalr(defs, uses));
            }
            emit(OPER.call_sink(defs)); // call sink after jal/jalr

            return new Temp(2);
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
        String tab = "  "; // 8 spaces. Generally 2 tabs but I wont risk it. <- LIAR
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
        printOut.print(     "maxArgOffset(" + maxArgOffset + ")" + "\n");
        printOut.println(")"); // Close frame
    }

    public boolean isRealRegister(Temp temp) {
         return temp.regIndex < numRegs();
    }

    public int numRegs() {
        return NUM_REGS;
    }

    public int numAvailableRegs() {
        return availableRegs.size();
    }

    public static Set<Temp> getReservedRegs() {
        return reservedRegs;
    }

    public static Set<Temp> getAvailableRegs() {
        return availableRegs;
    }

}
