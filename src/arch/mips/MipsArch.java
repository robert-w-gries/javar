package arch.mips;

import arch.Arch;
import arch.Frame;
import backend.alloc.RegAlloc;
import backend.assem.DIRECTIVE;
import backend.assem.Instr;
import backend.assem.MOVE;
import backend.canon.BasicBlocks;
import backend.canon.Canon;
import backend.canon.TraceSchedule;
import frontend.translate.DataFrag;
import frontend.translate.Frag;
import frontend.translate.ProcFrag;
import frontend.translate.irtree.Stm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by rgries on 5/11/15
 */
public class MipsArch extends Arch {

    public MipsArch() {
        instrs = new ArrayList<>();
        fileExt = "S";
    }

    public List<Instr> assemble(List<Frag> frags) {

        instrs.add(new DIRECTIVE("#include <mips.h>"));

        // canonicalize IR fragments, generate assembly instructions
        proccessFrags(frags);

        //remove first data frag
        frags.stream()
                .filter(frag -> frag instanceof DataFrag)
                .findFirst().ifPresent(frags::remove);

        //add all other data frags
        frags.stream().forEach(frag ->  {

            if (frag instanceof DataFrag) {
                instrs.add(new DIRECTIVE(frag.toString()));
            } else {
                ProcFrag p = (ProcFrag) frag;
                Frame pFrame = p.getFrame();
                List<Instr> procCode = p.getProcCode();
                pFrame.procEntryExit2(procCode);
                // register allocation
                new RegAlloc(pFrame, procCode);
                pFrame.procEntryExit3(procCode);

                // Remove redundant MOVEs
                for (Iterator<Instr> it = procCode.iterator(); it.hasNext(); ) {
                    Instr instr = it.next();
                    if (instr instanceof MOVE && ((MOVE)instr).redundantMove())
                        it.remove();
                }

                // format assem string
                procCode.stream().forEach(Instr::formatInstruction);

                //add procFrag instructions to final program
                instrs.addAll(procCode);

            }

        });

        return instrs;

    }

    private static void proccessFrags(List<Frag> frags) {
        frags.stream().filter(frag -> frag instanceof ProcFrag).forEach(frag -> {
            ProcFrag p = (ProcFrag)frag;
            List<Stm> traced = new LinkedList<>();
            if (p.getBody() != null) {
                LinkedList<Stm> stms = Canon.linearize(p.getBody());
                BasicBlocks blocks = new BasicBlocks(stms);
                new TraceSchedule(blocks, traced);
            }
            p.getFrame().procEntryExit1(traced);
            p.setCode(p.getFrame().codeGen(traced));
        });
    }

}
