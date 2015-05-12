/* Copyright (C) 1997-2003, Purdue Research Foundation of Purdue University.
 * All rights reserved.  */
package backend.canon;

import arch.Label;
import frontend.translate.irtree.CJUMP;
import frontend.translate.irtree.JUMP;
import frontend.translate.irtree.LABEL;
import frontend.translate.irtree.Stm;

import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;
import java.util.HashMap;

/**
 * Arranges the blocks so that CJUMP is followed by its false label.
 */

public class TraceSchedule {
    protected Label done;
    protected Iterator<LinkedList<Stm>> theBlocks;
    protected List<Stm> stms;
    protected HashMap<Label, LinkedList<Stm>> map = new HashMap<>();

    Stm getLast(LinkedList<Stm> block) {
        Stm last = block.removeLast();
        stms.addAll(block);
        return last;
    }

    void trace(LinkedList<Stm> slist) {
        for (; ; ) {
            Stm s = slist.getFirst();
            LABEL lab = (LABEL)s;
            map.remove(lab.label);
            s = getLast(slist);
            if (s instanceof JUMP) {
                JUMP j = (JUMP)s;
                Label first = j.target;
                LinkedList<Stm> target = map.get(first);
                if (target != null) {
                    slist = target;
                } else {
                    if (theBlocks.hasNext() || first != done)
                        stms.add(s);
                    return;
                }
            } else if (s instanceof CJUMP) {
                CJUMP j = (CJUMP)s;
                LinkedList<Stm> t = map.get(j.iftrue);
                LinkedList<Stm> f = map.get(j.iffalse);
                if (f != null) {
                    stms.add(s);
                    slist = f;
                } else if (t != null) {
                    stms.add(new CJUMP(CJUMP.notRel(j.relop),
                            j.left, j.right,
                            j.iffalse, j.iftrue));
                    slist = t;
                } else {
                    Label ff = new Label();
                    stms.add(new CJUMP(j.relop, j.left, j.right,
                            j.iftrue, ff));
                    stms.add(new LABEL(ff));
                    stms.add(new JUMP(j.iffalse));
                    return;
                }
            } else
                throw new Error("Bad basic block in TraceSchedule");
        }
    }

    public TraceSchedule(BasicBlocks b, List<Stm> stmts) {
        for (LinkedList<Stm> s : b.blocks) {
            map.put(((LABEL)s.getFirst()).label, s);
        }
        done = b.done;
        theBlocks = b.blocks.iterator();
        stms = stmts;
        while (theBlocks.hasNext()) {
            LinkedList<Stm> s = theBlocks.next();
            LABEL lab = (LABEL)s.getFirst();
            if (map.get(lab.label) != null)
                trace(s);
        }
        stms.add(new LABEL(done));
        map = null;
    }
}
