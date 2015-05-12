/* Copyright (C) 1997-2003, Purdue Research Foundation of Purdue University.
 * All rights reserved.  */
package backend.assem;

import arch.mips.MipsFrame;
import arch.Temp;
import arch.Label;

import java.io.PrintWriter;
import java.util.List;

/**
 * A data type used for assembly language without assigned registers.
 */
public abstract class Instr {
    protected String assem;
    protected List<Temp> use;
    protected List<Temp> def;
    protected List<Label> jumps;

    public List<Temp> getUse() {
        return use;
    }

    public List<Temp> getDef() {
        return def;
    }

    public List<Label> getJumps() {
        return jumps;
    }

    void output(PrintWriter writer) {
        if ((def != null) && (def.size() > 0)) {
            writer.print(" defs(");
            for (Temp aDef : def) {
                writer.print(aDef + " ");
            }
            writer.print(")");
        }
        if ((use != null) && (use.size() > 0)) {
            writer.print(" uses(");
            for (Temp anUse : use) {
                writer.print(anUse + " ");
            }
            writer.print(")");
        }
        if ((jumps != null) && (jumps.size() > 0)) {
            writer.print(" jumps(");
            for (Label label : jumps) {
                writer.print(label + " ");
            }
            writer.print(")");
        }
    }

    /**
     * Replaces the src list.
     */
    public void replaceUse(Temp olduse, Temp newuse) {
        if (use != null) {
            for (int i = 0; i < use.size(); i++) {
                if (use.get(i) == olduse) use.set(i, newuse);
            }
        }
    }

    /**
     * Replaces the dst list.
     */
    public void replaceDef(Temp olddef, Temp newdef) {
        if (def != null) {
            for (int i = 0; i < def.size(); i++) {
                if (def.get(i) == olddef) def.set(i, newdef);
            }
        }
    }

    /**
     * Formats an assembly instruction as a string.
     */
    public void formatInstruction() {
        StringBuilder s = new StringBuilder();

        if (this instanceof OPER || this instanceof MOVE) {
            s.append("\t");
        }

        int len = assem.length();
        for (int i = 0; i < len; i++) {
            if (assem.charAt(i) == '`') {
                switch (assem.charAt(++i)) {
                    case 's': {
                        int n = Character.digit(assem.charAt(++i), 10);
                        s.append(MipsFrame.getTempName(use.get(n)));
                        break;
                    }
                    case 'd': {
                        int n = Character.digit(assem.charAt(++i), 10);
                        s.append(MipsFrame.getTempName(def.get(n)));
                        break;
                    }
                    case 'j': {
                        int n = Character.digit(assem.charAt(++i), 10);
                        s.append(jumps.get(n).toString());
                        break;
                    }
                    case '`':
                        s.append('`');
                        break;
                    default:
                        throw new Error("bad Assem format:" + assem);
                }
            } else {
                s.append(assem.charAt(i));
            }
        }

        if (this instanceof LABEL) {
            s.append(":");
        }

        this.assem = s.toString();
    }

    public String toString() {
        return assem;
    }

}
