/* Copyright (C) 1997-2003, Purdue Research Foundation of Purdue University.
 * All rights reserved.  */
package backend.canon;

import arch.Temp;
import frontend.translate.irtree.*;

import java.util.LinkedList;
import java.util.List;

class MoveCall extends Stm {
    private TEMP dst;
    private CALL src;

    MoveCall(TEMP d, CALL s) {
        dst = d;
        src = s;
    }

    public List<Exp> kids() {
        return src.kids();
    }

    public Stm build(List<Exp> kids) {
        return new MOVE(dst, src.build(kids));
    }

    public void accept(IntVisitor v) {
        throw new Error();
    }

}

class ExpCall extends Stm {
    private CALL call;

    ExpCall(CALL c) {
        call = c;
    }

    public List<Exp> kids() {
        return call.kids();
    }

    public Stm build(List<Exp> kids) {
        return new EXP_STM(call.build(kids));
    }

    public void accept(IntVisitor v) {
        throw new Error();
    }
    
}

class StmExpList {
    Stm stm;
    LinkedList<Exp> exps;

    StmExpList(Stm s, LinkedList<Exp> e) {
        stm = s;
        exps = e;
    }
}

/**
 * Implements the transformation into canonical trees.
 */

public class Canon {
    private static boolean isNop(Stm a) {
        return a instanceof EXP_STM
                && ((EXP_STM)a).exp instanceof CONST;
    }

    private static Stm seq(Stm a, Stm b) {
        if (isNop(a))
            return b;
        else if (isNop(b))
            return a;
        else
            return new SEQ(a, b);
    }

    private static boolean commute(Stm a, Exp b) {
        return isNop(a) || b instanceof NAME || b instanceof CONST;
    }

    private static Stm do_stm(SEQ s) {
        return seq(do_stm(s.left), do_stm(s.right));
    }

    private static Stm do_stm(MOVE s) {
        if (s.dst instanceof TEMP && s.src instanceof CALL)
            return reorder_stm(new MoveCall((TEMP)s.dst,
                    (CALL)s.src));
        else if (s.dst instanceof ESEQ)
            return do_stm(new SEQ(((ESEQ)s.dst).stm,
                    new MOVE(((ESEQ)s.dst).exp,
                            s.src)));
        else
            return reorder_stm(s);
    }

    private static Stm do_stm(EXP_STM s) {
        if (s.exp instanceof CALL)
            return reorder_stm(new ExpCall((CALL)s.exp));
        else
            return reorder_stm(s);
    }

    private static Stm do_stm(Stm s) {
        if (s instanceof SEQ)
            return do_stm((SEQ)s);
        else if (s instanceof MOVE)
            return do_stm((MOVE)s);
        else if (s instanceof EXP_STM)
            return do_stm((EXP_STM)s);
        else
            return reorder_stm(s);
    }

    private static Stm reorder_stm(Stm s) {
        StmExpList x = reorder((LinkedList<Exp>)s.kids());
        return seq(x.stm, s.build(x.exps));
    }

    private static ESEQ do_exp(ESEQ e) {
        Stm stms = do_stm(e.stm);
        ESEQ b = do_exp(e.exp);
        return new ESEQ(seq(stms, b.stm), b.exp);
    }

    private static ESEQ do_exp(CALL e) {
        Temp t = new Temp();
        Exp a = new ESEQ(new MOVE(new TEMP(t), e),
                new TEMP(t));
        return do_exp(a);
    }

    private static ESEQ do_exp(Exp e) {
        if (e instanceof ESEQ)
            return do_exp((ESEQ)e);
        else if (e instanceof CALL)
            return do_exp((CALL)e);
        else
            return reorder_exp(e);
    }

    private static ESEQ reorder_exp(Exp e) {
        StmExpList x = reorder((LinkedList<Exp>)e.kids());
        return new ESEQ(x.stm, e.build(x.exps));
    }

    private static final Stm nopNull = new EXP_STM(new CONST(0));

    private static StmExpList reorder(LinkedList<Exp> exps) {
        if (exps.isEmpty())
            return new StmExpList(nopNull, exps);
        else {
            Exp a = exps.removeFirst();
            ESEQ aa = do_exp(a);
            StmExpList bb = reorder(exps);
            if (commute(bb.stm, aa.exp)) {
                bb.exps.addFirst(aa.exp);
                return new StmExpList(seq(aa.stm, bb.stm), bb.exps);
            } else {
                Temp t = new Temp();
                bb.exps.addFirst(new TEMP(t));
                return
                        new StmExpList(seq(aa.stm,
                                seq(new MOVE(new TEMP(t),
                                                aa.exp),
                                        bb.stm)),
                                bb.exps);
            }
        }
    }

    private static LinkedList<Stm> linear(SEQ s, LinkedList<Stm> l) {
        return linear(s.left, linear(s.right, l));
    }

    private static LinkedList<Stm> linear(Stm s, LinkedList<Stm> l) {
        if (s instanceof SEQ)
            return linear((SEQ)s, l);
        else {
            l.addFirst(s);
            return l;
        }
    }

    static public LinkedList<Stm> linearize(Stm s) {
        return linear(do_stm(s), new LinkedList<>());
    }
}
