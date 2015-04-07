package Translate;

import Tree.*;

/**
 * Created with IntelliJ IDEA.
 * User: jchitel
 * Date: 3/22/15
 * Time: 11:21 PM
 */
public class IfThenElseExp extends Exp {

    Exp cond, a, b;
    Temp.Label t = new Temp.Label();
    Temp.Label f = new Temp.Label();
    Temp.Label join;

    IfThenElseExp(Exp cond, Exp a, Exp b) {
        this.cond = cond;
        this.a = a;
        this.b = b;
    }

    Tree.Stm unCx(Temp.Label tt, Temp.Label ff) {

        Tree.Stm trueStm = a.unCx(tt, ff);
        Tree.Stm falseStm = b.unCx(tt, ff);

        SEQ trueSeq = new SEQ(new LABEL(t), trueStm);
        SEQ falseSeq = new SEQ(new LABEL(f), falseStm);

        return new SEQ(cond.unCx(t,f), new SEQ(trueSeq, falseSeq));

    }

    Tree.Exp unEx() {

        Temp.Temp r = new Temp.Temp();
        join = new Temp.Label();

        Tree.Exp tExp = a.unEx();
        Tree.Exp fExp = b.unEx();

        SEQ tSeq = new SEQ(new LABEL(t), new SEQ(new MOVE(new Tree.TEMP(r), tExp), new JUMP(join)));
        SEQ fSeq = new SEQ(new LABEL(f), new SEQ(new MOVE(new Tree.TEMP(r), fExp), new JUMP(join)));

        SEQ cjumpSeq = new SEQ(cond.unCx(t, f), new SEQ(tSeq, fSeq));

        return new ESEQ(new SEQ(cjumpSeq, new LABEL(join)), new TEMP(r));

    }

    Tree.Stm unNx() {

        join = new Temp.Label();

        Tree.Stm tStm = a.unNx();
        Tree.Stm fStm = b.unNx();

        SEQ tSeq = new SEQ(new LABEL(t), new SEQ(tStm, new JUMP(join)));
        SEQ fSeq = new SEQ(new LABEL(f), new SEQ(fStm, new JUMP(join)));

        SEQ cjumpSeq = new SEQ(cond.unCx(t, f), new SEQ(tSeq, fSeq));

        return new SEQ(cjumpSeq, new LABEL(join));

    }

}
