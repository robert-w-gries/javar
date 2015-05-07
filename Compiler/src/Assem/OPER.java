/* Copyright (C) 1997-2003, Purdue Research Foundation of Purdue University.
 * All rights reserved.  */
package Assem;

import Temp.Temp;
import Temp.Label;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Holds an assembly-instruction, a list of operand registers,
 * and a list of result registers.
 */

public class OPER extends Instr {
    public OPER(String a, List<Temp> d, List<Temp> s, List<Label> j) {
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

    private static OPER nolabels(String a, List<Temp> d, List<Temp> s) {
        return new OPER(a, d, s, new LinkedList<Label>());
    }

    private static OPER nolab1dst1src(String a, Temp d, Temp s) {
        List<Temp> ds = new ArrayList<>();
        ds.add(d);
        List<Temp> ss = new ArrayList<>();
        ss.add(s);
        return nolabels(a, ds, ss);
    }

    private static OPER nolab1dst2src(String a, Temp d, Temp s1, Temp s2) {
        List<Temp> ds = new ArrayList<>();
        ds.add(d);
        List<Temp> ss = new ArrayList<>();
        ss.add(s1);
        ss.add(s2);
        return nolabels(a, ds, ss);
    }

    private static OPER nolab1dst0src(String a, Temp d) {
        List<Temp> ds = new ArrayList<>();
        ds.add(d);
        return nolabels(a, ds, new ArrayList<Temp>());
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

    public static OPER xori(Temp dst, Temp src, int value) {
        String assem = "xori `d0, `s0, " + value;
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

    public static OPER lw(Temp dst, Temp src, int offset, String name) {
        String assem;
        if (src.regIndex == 29) {
            assem = "lw `d0, " + (offset+12) + "+" + name + "_framesize(`s0)";
        } else {
            assem = "lw `d0, " + offset + " (`s0)";
        }
        return nolab1dst1src(assem, dst, src);
    }

    public static OPER sw(Temp src, Temp dst, int offset) {
        String assem = "sw `s0, " + offset + "(`s1)";
        List<Temp> ss = new ArrayList<>();
        ss.add(src);
        ss.add(dst);
        return new OPER(assem, new ArrayList<Temp>(), ss, new LinkedList<Label>());
    }

    public static OPER b(Label label) {

        String assem = "b `j0";

        LinkedList<Label> labels = new LinkedList<>();
        labels.add(label);

        return new OPER(assem, null, null, labels);

    }

    public static OPER jr(Temp jumpreg) {
        String assem = "jr `s0";
        List<Temp> ss = new ArrayList<>();
        ss.add(jumpreg);
        return new OPER(assem, null, ss, null);
    }

    public static OPER jal(List<Temp> defs, List<Temp> uses, Label label) {
        String assem = "jal " + label;
        return new OPER(assem, defs, uses, null);
    }

    public static OPER jalr(List<Temp> defs, List<Temp> uses) {
        String assem = "jalr `s0";
        return new OPER(assem, defs, uses, new LinkedList<Label>());
    }

    public static OPER call_sink(List<Temp> uses) {
        String assem = "// Call sink";
        return new OPER(assem, null, uses, new LinkedList<Label>());

    }

    public static OPER beq(Temp src1, Temp src2, Label t, Label f) {
        String assem = "beq `s0, `s1, `j0";
        LinkedList<Label> jumps = new LinkedList<>();
        jumps.add(t);
        jumps.add(f);
        List<Temp> ss = new ArrayList<>();
        ss.add(src1);
        ss.add(src2);
        return new OPER(assem, null, ss, jumps);
    }

    public static OPER bne(Temp src1, Temp src2, Label t, Label f) {
        String assem = "bne `s0, `s1, `j0";
        LinkedList<Label> jumps = new LinkedList<>();
        jumps.add(t);
        jumps.add(f);
        List<Temp> ss = new ArrayList<>();
        ss.add(src1);
        ss.add(src2);
        return new OPER(assem, null, ss, jumps);
    }

    public static OPER blt(Temp src1, Temp src2, Label t, Label f) {
        String assem = "blt `s0, `s1, `j0";
        LinkedList<Label> jumps = new LinkedList<>();
        jumps.add(t);
        jumps.add(f);
        List<Temp> ss = new ArrayList<>();
        ss.add(src1);
        ss.add(src2);
        return new OPER(assem, null, ss, jumps);
    }

    public static OPER bgt(Temp src1, Temp src2, Label t, Label f) {
        String assem = "bgt `s0, `s1, `j0";
        LinkedList<Label> jumps = new LinkedList<>();
        jumps.add(t);
        jumps.add(f);
        List<Temp> ss = new ArrayList<>();
        ss.add(src1);
        ss.add(src2);
        return new OPER(assem, null, ss, jumps);
    }

    public static OPER ble(Temp src1, Temp src2, Label t, Label f) {
        String assem = "ble `s0, `s1, `j0";
        LinkedList<Label> jumps = new LinkedList<>();
        jumps.add(t);
        jumps.add(f);
        List<Temp> ss = new ArrayList<>();
        ss.add(src1);
        ss.add(src2);
        return new OPER(assem, null, ss, jumps);
    }

    public static OPER bge(Temp src1, Temp src2, Label t, Label f) {
        String assem = "bge `s0, `s1, `j0";
        LinkedList<Label> jumps = new LinkedList<>();
        jumps.add(t);
        jumps.add(f);
        List<Temp> ss = new ArrayList<>();
        ss.add(src1);
        ss.add(src2);
        return new OPER(assem, null, ss, jumps);
    }

    // END OPERATIONS
}
