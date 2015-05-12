package arch;

import backend.assem.Instr;
import frontend.translate.Frag;

import java.util.List;

/**
 * Created by rgries on 5/11/15
 */
public abstract class Arch {

    protected List<Instr> instrs;
    protected String fileExt;

    public abstract List<Instr> assemble(List<Frag> frags);

    public List<Instr> getInstructions() {
        return instrs;
    }

    public String getFileExt() {
        return fileExt;
    }

}
