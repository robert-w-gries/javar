package frontend.translate;

import arch.Frame;
import backend.assem.Instr;
import frontend.translate.irtree.Stm;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jchitel
 * Date: 3/22/15
 * Time: 11:18 PM
 */
public class ProcFrag extends Frag {

    public Stm body;
    public Frame frame;
    public List<Instr> code;

    public ProcFrag(Stm body, Frame frame) {
        this.body = body;
        this.frame = frame;
    }

    public ProcFrag(List<Instr> code, Frame frame) {
        this.code = code;
        this.frame = frame;
    }
}
