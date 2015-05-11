package Translate;

import Tree.LABEL;
import Tree.SEQ;

/**
 * Created with IntelliJ IDEA.
 * User: jchitel
 * Date: 3/22/15
 * Time: 11:20 PM
 */
public abstract class Cx extends Exp {

    public static final Tree.CONST TRUE = new Tree.CONST(1),
                                FALSE = new Tree.CONST(0);

    public Tree.Exp unEx() {
        // expression result register
        Tree.TEMP result = new Tree.TEMP(new Temp.Temp());

        // labels to jump to on true/false
        Tree.LABEL t_label = new Tree.LABEL(new Temp.Label());
        Tree.LABEL f_label = new Tree.LABEL(new Temp.Label());

        // MOVEs to set the result register to true or false
        Tree.MOVE set_result_true = new Tree.MOVE(result, TRUE);
        Tree.MOVE set_result_false = new Tree.MOVE(result, FALSE);

        // SEQ for the else block: false label, result = false, true label
        Tree.SEQ else_block = seq(f_label, seq(set_result_false, t_label));

        // full true/false block: result = true, cjump evaluation, else block
        Tree.SEQ set_result = seq(set_result_true, seq(unCx(t_label.label, f_label.label), else_block));

        // resulting pseudo-assembly:
        // --------------------------
        // result = true;
        // unCx(t_label, f_label);
        // f_label: result = false;
        // t_label: ( return result )

        return eseq(set_result, result);

    }

    private Tree.ESEQ eseq(Tree.Stm stm, Tree.Exp exp) {
        return new Tree.ESEQ(stm, exp);
    }

    private Tree.SEQ seq(Tree.Stm stm, Tree.Stm stm2) {
        return new Tree.SEQ(stm, stm2);
    }

    //TODO: figure out if this is the correct way to do it
    public Tree.Stm unNx() {

        Temp.Label join = new Temp.Label();

        return new SEQ(unCx(join, join), new LABEL(join));

    }

    public abstract Tree.Stm unCx(Temp.Label t, Temp.Label f);

}
