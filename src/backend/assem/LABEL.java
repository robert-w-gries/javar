/* Copyright (C) 1997-2003, Purdue Research Foundation of Purdue University.
 * All rights reserved.  */
package backend.assem;

import arch.Label;

/**
 * A point within the program to which a jump may go.
 */

public class LABEL extends Instr {
    public Label label;

    public LABEL(String assem, Label label) {
        this.assem = assem;
        use = null;
        def = null;
        jumps = null;
        this.label = label;
    }

    public void output(java.io.PrintWriter writer) {
        writer.print("  LABEL(\"" + label + "\"");
        super.output(writer);
        writer.println(")");
    }

    public boolean equals(Object o) {
        if (o == null || !(o instanceof LABEL)) return false;

        LABEL l = (LABEL)o;

        return label.equals(l.label);
    }

    public int hashCode() {
        return label.hashCode();
    }
}
