package arch.mips;

import arch.Arch;
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
 * Created by rgries on 5/11/15.
 */
public class MipsArch extends Arch {

    public MipsArch() {
        instrs = new ArrayList<>();
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
                p.frame.procEntryExit2(p.code);
                // register allocation
                new RegAlloc(p.frame, p.code);
                p.frame.procEntryExit3(p.code);

                // Remove redundant MOVEs
                for (Iterator<Instr> it = p.code.iterator(); it.hasNext(); ) {
                    Instr instr = it.next();
                    if (instr instanceof MOVE && ((MOVE)instr).redundantMove())
                        it.remove();
                }

                // format assem string
                p.code.stream().forEach(Instr::formatInstruction);

                //add procFrag instructions to final program
                instrs.addAll(p.code);

            }

        });

        return instrs;

    }

    private static void proccessFrags(List<Frag> frags) {
        frags.stream().filter(frag -> frag instanceof ProcFrag).forEach(frag -> {
            ProcFrag p = (ProcFrag)frag;
            List<Stm> traced = new LinkedList<>();
            if (p.body != null) {
                LinkedList<Stm> stms = Canon.linearize(p.body);
                BasicBlocks blocks = new BasicBlocks(stms);
                new TraceSchedule(blocks, traced);
            }
            p.frame.procEntryExit1(traced);
            p.code = p.frame.codeGen(traced);
        });
    }

}
