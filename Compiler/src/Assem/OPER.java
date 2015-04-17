/* Copyright (C) 1997-2003, Purdue Research Foundation of Purdue University.
 * All rights reserved.  */
package Assem;

import Temp.Temp;
import Temp.Label;

import java.util.LinkedList;
import java.util.List;

/**
 * Holds an assembly-instruction, a list of operand registers,
 * and a list of result registers.
 */

public class OPER extends Instr {
    public OPER(String a, Temp[] d, Temp[] s, List<Label> j) {
        assem = a;
        use = s;
        def = d;
        jumps = j;
    }

    public void output(java.io.PrintWriter writer) {
        writer.print("  OPER(\"" + assem + "\"");
        super.output(writer);
        writer.println(")");
    }

    // UTILITY FUNCTIONS FOR CREATING OPERS

    private static OPER nolabels(String a, Temp[] d, Temp[] s) {
        return new OPER(a, d, s, new LinkedList<Label>());
    }

    private static OPER nolab1dst1src(String a, Temp d, Temp s) {
        return nolabels(a, new Temp[] { d }, new Temp[] { s });
    }

    private static OPER nolab1dst2src(String a, Temp d, Temp s1, Temp s2) {
        return nolabels(a, new Temp[] { d }, new Temp[] { s1, s2 });
    }

    private static OPER nolab1dst0src(String a, Temp d) {
        return nolabels(a, new Temp[] { d }, new Temp[0]);
    }

    // END UTILITY FUNCTIONS

    // OPERATION IMPLEMENTATIONS

    public static OPER addi(Temp dst, Temp src, int value) {
        String assem = "addi\t`d0,\t`s0,\t" + value;
        return nolab1dst1src(assem, dst, src);
    }

    public static OPER sll(Temp dst, Temp src, int value) {
        String assem = "sll\t`d0,\t`s0,\t" + value;
        return nolab1dst1src(assem, dst, src);
    }

    public static OPER sra(Temp dst, Temp src, int value) {
        String assem = "sra\t`d0,\t`s0,\t" + value;
        return nolab1dst1src(assem, dst, src);
    }

    public static OPER srl(Temp dst, Temp src, int value) {
        String assem = "srl\t`d0,\t`s0,\t" + value;
        return nolab1dst1src(assem, dst, src);
    }

    public static OPER andi(Temp dst, Temp src, int value) {
        String assem = "andi\t`d0,\t`s0,\t" + value;
        return nolab1dst1src(assem, dst, src);
    }

    public static OPER ori(Temp dst, Temp src, int value) {
        String assem = "ori\t`d0,\t`s0,\t" + value;
        return nolab1dst1src(assem, dst, src);
    }

    public static OPER addi_zero(Temp dst, int value) {
        String assem = "addi\t`d0,\tzero, " + value;
        return nolab1dst0src(assem, dst);
    }

    public static OPER li(Temp dst, int value) {
        String assem = "li\t`d0,\t" + value;
        return nolab1dst0src(assem, dst);
    }

    public static OPER la(Temp dst, String value) {
        String assem = "la\t`d0,\t" + value;
        return nolab1dst0src(assem, dst);
    }

    public static OPER add(Temp dst, Temp src1, Temp src2) {
        String assem = "add\t`d0,\t`s0,\t`s1";
        return nolab1dst2src(assem, dst, src1, src2);
    }

    public static OPER sub(Temp dst, Temp src1, Temp src2) {
        String assem = "sub\t`d0,\t`s0,\t`s1";
        return nolab1dst2src(assem, dst, src1, src2);
    }

    public static OPER mulo(Temp dst, Temp src1, Temp src2) {
        String assem = "mulo\t`d0,\t`s0,\t`s1";
        return nolab1dst2src(assem, dst, src1, src2);
    }

    public static OPER div(Temp dst, Temp src1, Temp src2) {
        String assem = "div\t`d0,\t`s0,\t`s1";
        return nolab1dst2src(assem, dst, src1, src2);
    }

    public static OPER and(Temp dst, Temp src1, Temp src2) {
        String assem = "and\t`d0,\t`s0,\t`s1";
        return nolab1dst2src(assem, dst, src1, src2);
    }

    public static OPER or(Temp dst, Temp src1, Temp src2) {
        String assem = "or\t`d0,\t`s0,\t`s1";
        return nolab1dst2src(assem, dst, src1, src2);
    }

    public static OPER sllv(Temp dst, Temp src1, Temp src2) {
        String assem = "sllv\t`d0,\t`s0,\t`s1";
        return nolab1dst2src(assem, dst, src1, src2);
    }

    public static OPER srlv(Temp dst, Temp src1, Temp src2) {
        String assem = "srlv\t`d0,\t`s0,\t`s1";
        return nolab1dst2src(assem, dst, src1, src2);
    }

    public static OPER srav(Temp dst, Temp src1, Temp src2) {
        String assem = "srav\t`d0,\t`s0,\t`s1";
        return nolab1dst2src(assem, dst, src1, src2);
    }

    public static OPER lw(Temp dst, Temp src, int offset, String name) {
        String assem;
        if (src.regIndex == 29) {
            assem = "lw\t`d0,\t" + (offset+12) + "+" + name + "_framesize(`s0)";
        } else {
            assem = "lw\t`d0,\t" + offset + "\t(`s0)";
        }
        return nolab1dst1src(assem, dst, src);
    }

    public static OPER sw(Temp src, Temp dst, int offset) {
        String assem = "sw\t`s0,\t" + offset + "(`s1)";
        return new OPER(assem, new Temp[0], new Temp[] {src, dst}, new LinkedList<Label>());
    }

    public static OPER b(Label label) {

        String assem = "b\t`j0";

        LinkedList<Label> labels = new LinkedList<>();
        labels.add(label);

        return new OPER(assem, null, null, labels);

    }

    public static OPER jr(Temp jumpreg) {
        String assem = "jr\t`s0";
        return new OPER(assem, null, new Temp[] { jumpreg }, null);
    }

    public static OPER jal(Temp[] defs, Temp[] uses, Label label) {
        String assem = "jal\t" + label;
        return new OPER(assem, defs, uses, null);
    }

    public static OPER jalr(Temp[] defs, Temp[] uses) {
        String assem = "jalr\t`s0";
        return new OPER(assem, defs, uses, new LinkedList<Label>());
    }

    public static OPER call_sink(Temp[] uses) {
        String assem = "// Call sink";
        return new OPER(assem, null, uses, new LinkedList<Label>());

    }

    public static OPER beq(Temp src1, Temp src2, Label t, Label f) {
        String assem = "beq\t`s0,\t`s1,\t`j0";
        LinkedList<Label> jumps = new LinkedList<>();
        jumps.add(t);
        jumps.add(f);
        return new OPER(assem, new Temp[0], new Temp[] { src1, src2 }, jumps);
    }

    public static OPER bne(Temp src1, Temp src2, Label t, Label f) {
        String assem = "bne\t`s0,\t`s1,\t`j0";
        LinkedList<Label> jumps = new LinkedList<>();
        jumps.add(t);
        jumps.add(f);
        return new OPER(assem, new Temp[0], new Temp[] { src1, src2 }, jumps);
    }

    public static OPER blt(Temp src1, Temp src2, Label t, Label f) {
        String assem = "blt\t`s0,\t`s1,\t`j0";
        LinkedList<Label> jumps = new LinkedList<>();
        jumps.add(t);
        jumps.add(f);
        return new OPER(assem, new Temp[0], new Temp[] { src1, src2 }, jumps);
    }

    public static OPER bgt(Temp src1, Temp src2, Label t, Label f) {
        String assem = "bgt\t`s0,\t`s1,\t`j0";
        LinkedList<Label> jumps = new LinkedList<>();
        jumps.add(t);
        jumps.add(f);
        return new OPER(assem, new Temp[0], new Temp[] { src1, src2 }, jumps);
    }

    public static OPER ble(Temp src1, Temp src2, Label t, Label f) {
        String assem = "ble\t`s0,\t`s1,\t`j0";
        LinkedList<Label> jumps = new LinkedList<>();
        jumps.add(t);
        jumps.add(f);
        return new OPER(assem, new Temp[0], new Temp[] { src1, src2 }, jumps);
    }

    public static OPER bge(Temp src1, Temp src2, Label t, Label f) {
        String assem = "bge\t`s0,\t`s1,\t`j0";
        LinkedList<Label> jumps = new LinkedList<>();
        jumps.add(t);
        jumps.add(f);
        return new OPER(assem, new Temp[0], new Temp[] { src1, src2 }, jumps);
    }

    // END OPERATIONS
}
