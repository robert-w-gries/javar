package frontend.translate;

import arch.Label;
import arch.Temp;
import frontend.translate.irtree.*;

/**
 * Created with IntelliJ IDEA.
 * User: jchitel
 * Date: 3/22/15
 * Time: 11:21 PM
 */
class IfThenElseExp extends frontend.translate.Exp {

    private frontend.translate.Exp cond;
    private frontend.translate.Exp a;
    private frontend.translate.Exp b;
    private Label t = new Label();
    private Label f = new Label();
    private Label join;

    IfThenElseExp(frontend.translate.Exp cond, frontend.translate.Exp a, frontend.translate.Exp b) {
        this.cond = cond;
        this.a = a;
        this.b = b;
    }

    Stm unCx(Label tt, Label ff) {

        Stm trueStm = a.unCx(tt, ff);
        Stm falseStm = b.unCx(tt, ff);

        SEQ trueSeq = new SEQ(new LABEL(t), trueStm);
        SEQ falseSeq = new SEQ(new LABEL(f), falseStm);

        return new SEQ(cond.unCx(t,f), new SEQ(trueSeq, falseSeq));

    }

    frontend.translate.irtree.Exp unEx() {

        Temp r = new Temp();
        join = new Label();

        frontend.translate.irtree.Exp tExp = a.unEx();
        frontend.translate.irtree.Exp fExp = b.unEx();

        SEQ tSeq = new SEQ(new LABEL(t), new SEQ(new MOVE(new TEMP(r), tExp), new JUMP(join)));
        SEQ fSeq = new SEQ(new LABEL(f), new SEQ(new MOVE(new TEMP(r), fExp), new JUMP(join)));

        SEQ cjumpSeq = new SEQ(cond.unCx(t, f), new SEQ(tSeq, fSeq));

        return new ESEQ(new SEQ(cjumpSeq, new LABEL(join)), new TEMP(r));

    }

    Stm unNx() {

        join = new Label();

        Stm tStm = a.unNx();
        Stm fStm = b.unNx();

        SEQ tSeq = new SEQ(new LABEL(t), new SEQ(tStm, new JUMP(join)));
        SEQ fSeq = new SEQ(new LABEL(f), new SEQ(fStm, new JUMP(join)));

        SEQ cjumpSeq = new SEQ(cond.unCx(t, f), new SEQ(tSeq, fSeq));

        return new SEQ(cjumpSeq, new LABEL(join));

    }

}
