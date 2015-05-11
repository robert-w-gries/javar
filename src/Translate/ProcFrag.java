package Translate;

import Assem.Instr;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jchitel
 * Date: 3/22/15
 * Time: 11:18 PM
 */
public class ProcFrag extends Frag {

    public Tree.Stm body;
    public Frame.Frame frame;
    public List<Instr> code;

    public ProcFrag(Tree.Stm body, Frame.Frame frame) {
        this.body = body;
        this.frame = frame;
    }

    public ProcFrag(List<Instr> code, Frame.Frame frame) {
        this.code = code;
        this.frame = frame;
    }
}