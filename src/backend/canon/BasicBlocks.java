/* Copyright (C) 1997-2003, Purdue Research Foundation of Purdue University.
 * All rights reserved.  */
package backend.canon;

import arch.Label;
import frontend.translate.irtree.CJUMP;
import frontend.translate.irtree.JUMP;
import frontend.translate.irtree.LABEL;
import frontend.translate.irtree.Stm;

import java.util.List;
import java.util.LinkedList;

/**
 * Groups statements into sequences of straight-line code.
 */

public class BasicBlocks {
    public List<LinkedList<Stm>> blocks;
    public Label done;
    private LinkedList<Stm> lastStm;

    private void addStm(Stm s) {
        lastStm.add(s);
    }

    private void doStms(LinkedList<Stm> slist) {
        if (slist.isEmpty()) {
            slist.add(new JUMP(done));
            doStms(slist);
        } else {
            Stm s = slist.removeFirst();
            if (s instanceof JUMP || s instanceof CJUMP) {
                addStm(s);
                mkBlocks(slist);
            } else if (s instanceof LABEL) {
                slist.addFirst(s);
                slist.addFirst(new JUMP(((LABEL)s).label));
                doStms(slist);
            } else {
                addStm(s);
                doStms(slist);
            }
        }
    }

    void mkBlocks(LinkedList<Stm> slist) {
        if (!slist.isEmpty()) {
            Stm s = slist.removeFirst();
            if (s instanceof LABEL) {
                lastStm = new LinkedList<Stm>();
                lastStm.add(s);
                blocks.add(lastStm);
                doStms(slist);
            } else {
                slist.addFirst(s);
                slist.addFirst(new LABEL(new Label()));
                mkBlocks(slist);
            }
        }
    }

    public BasicBlocks(LinkedList<Stm> stms) {
        done = new Label();
        blocks = new LinkedList<LinkedList<Stm>>();
        mkBlocks(stms);
    }
}
