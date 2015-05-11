/* Copyright (C) 1997-2003, Purdue Research Foundation of Purdue University.
 * All rights reserved.  */
package backend.assem;

import arch.Temp;

import java.util.ArrayList;

/**
 * Holds an assembly-language instruction that performs only data transfer
 */

public class MOVE extends Instr {
    public MOVE(String a, Temp d, Temp s) {
        assem = a;
        use = new ArrayList<>();
        use.add(s);
        def = new ArrayList<>();
        def.add(d);
        jumps = null;
    }

    public void output(java.io.PrintWriter writer) {
        writer.print("  MOVE(\"" + assem + "\"");
        super.output(writer);
        writer.println(")");
    }

    /**
     * Returns a list dst.
     */
    public Temp dst() {
        return def.get(0);
    }

    /**
     * Returns a list src.
     */
    public Temp src() {
        return use.get(0);
    }
}
