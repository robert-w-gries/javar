package frontend.translate;

import arch.Label;
import arch.Temp;
import frontend.translate.irtree.*;

/**
 * Created with IntelliJ IDEA.
 * User: jchitel
 * Date: 3/22/15
 * Time: 11:20 PM
 */
public abstract class Cx extends Exp {

    public static final CONST TRUE = new CONST(1),
                                FALSE = new CONST(0);

    public frontend.translate.irtree.Exp unEx() {
        // expression result register
        TEMP result = new TEMP(new Temp());

        // labels to jump to on true/false
        LABEL t_label = new LABEL(new Label());
        LABEL f_label = new LABEL(new Label());

        // MOVEs to set the result register to true or false
        MOVE set_result_true = new MOVE(result, TRUE);
        MOVE set_result_false = new MOVE(result, FALSE);

        // SEQ for the else block: false label, result = false, true label
        SEQ else_block = seq(f_label, seq(set_result_false, t_label));

        // full true/false block: result = true, cjump evaluation, else block
        SEQ set_result = seq(set_result_true, seq(unCx(t_label.label, f_label.label), else_block));

        // resulting pseudo-assembly:
        // --------------------------
        // result = true;
        // unCx(t_label, f_label);
        // f_label: result = false;
        // t_label: ( return result )

        return eseq(set_result, result);

    }

    private ESEQ eseq(Stm stm, frontend.translate.irtree.Exp exp) {
        return new ESEQ(stm, exp);
    }

    private SEQ seq(Stm stm, Stm stm2) {
        return new SEQ(stm, stm2);
    }

    //TODO: figure out if this is the correct way to do it
    public Stm unNx() {

        Label join = new Label();

        return new SEQ(unCx(join, join), new LABEL(join));

    }

    public abstract Stm unCx(Label t, Label f);

}
