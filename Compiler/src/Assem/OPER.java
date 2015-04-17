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
        String assem = "addi `d0, `s0, " + value;
        return nolab1dst1src(assem, dst, src);
    }

    public static OPER sll(Temp dst, Temp src, int value) {
        String assem = "sll `d0, `s0, " + value;
        return nolab1dst1src(assem, dst, src);
    }

    public static OPER sra(Temp dst, Temp src, int value) {
        String assem = "sra `d0, `s0, " + value;
        return nolab1dst1src(assem, dst, src);
    }

    public static OPER srl(Temp dst, Temp src, int value) {
        String assem = "srl `d0, `s0, " + value;
        return nolab1dst1src(assem, dst, src);
    }

    public static OPER andi(Temp dst, Temp src, int value) {
        String assem = "andi `d0, `s0, " + value;
        return nolab1dst1src(assem, dst, src);
    }

    public static OPER ori(Temp dst, Temp src, int value) {
        String assem = "ori `d0, `s0, " + value;
        return nolab1dst1src(assem, dst, src);
    }

    public static OPER addi_zero(Temp dst, int value) {
        String assem = "addi `d0, zero, " + value;
        return nolab1dst0src(assem, dst);
    }

    public static OPER li(Temp dst, int value) {
        String assem = "li `d0, " + value;
        return nolab1dst0src(assem, dst);
    }

    public static OPER la(Temp dst, String value) {
        String assem = "la `d0, " + value;
        return nolab1dst0src(assem, dst);
    }

    public static OPER add(Temp dst, Temp src1, Temp src2) {
        String assem = "add `d0, `s0, `s1";
        return nolab1dst2src(assem, dst, src1, src2);
    }

    public static OPER sub(Temp dst, Temp src1, Temp src2) {
        String assem = "sub `d0, `s0, `s1";
        return nolab1dst2src(assem, dst, src1, src2);
    }

    public static OPER mulo(Temp dst, Temp src1, Temp src2) {
        String assem = "mulo `d0, `s0, `s1";
        return nolab1dst2src(assem, dst, src1, src2);
    }

    public static OPER div(Temp dst, Temp src1, Temp src2) {
        String assem = "div `d0, `s0, `s1";
        return nolab1dst2src(assem, dst, src1, src2);
    }

    public static OPER and(Temp dst, Temp src1, Temp src2) {
        String assem = "and `d0, `s0, `s1";
        return nolab1dst2src(assem, dst, src1, src2);
    }

    public static OPER or(Temp dst, Temp src1, Temp src2) {
        String assem = "or `d0, `s0, `s1";
        return nolab1dst2src(assem, dst, src1, src2);
    }

    public static OPER sllv(Temp dst, Temp src1, Temp src2) {
        String assem = "sllv `d0, `s0, `s1";
        return nolab1dst2src(assem, dst, src1, src2);
    }

    public static OPER srlv(Temp dst, Temp src1, Temp src2) {
        String assem = "srlv `d0, `s0, `s1";
        return nolab1dst2src(assem, dst, src1, src2);
    }

    public static OPER srav(Temp dst, Temp src1, Temp src2) {
        String assem = "srav `d0, `s0, `s1";
        return nolab1dst2src(assem, dst, src1, src2);
    }

    public static OPER lw(Temp dst, Temp src, int offset) {
        String assem = "lw `d0, " + offset + "(`s0)";
        return nolab1dst1src(assem, dst, src);
    }

    // END OPERATIONS
}
