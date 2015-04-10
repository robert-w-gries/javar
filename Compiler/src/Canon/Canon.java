package Canon;

import Tree.*;

import java.util.LinkedList;
import java.util.List;

public class Canon {

    /**
     * Top-level method, cleans all ESEQs out of a procedure statement tree
     * and turns it into a linear list of atomic statements
     * @param stm statement tree to canonicalize
     * @return a list of linear statements
     */
    public static List<Stm> canonicalize(Stm stm) {
        Stm clean = clean_stm(stm);
        List<Stm> linear = new LinkedList<Stm>();
        linearize(clean, linear);
        return linear;
    }

    /**
     * CLEAN STATEMENTS
     */

    /**
     * Recursively cleans a statement tree, taking care of special cases
     * @param stm statement tree to be cleaned
     * @return a clean statement tree
     */
    private static Stm clean_stm(Stm stm) {
        if (stm instanceof SEQ) return clean_stm((SEQ)stm);
        else if (stm instanceof MOVE) return clean_stm((MOVE)stm);
        else if (stm instanceof EXP_STM) return clean_stm((EXP_STM)stm);
        else return reorder_stm(stm);
    }

    /**
     * Recursively cleans a SEQ by separately cleaning the branches
     * and reassembling them
     * @param seq SEQ to be cleaned
     * @return a clean statement tree
     */
    private static Stm clean_stm(SEQ seq) {
        return seq(clean_stm(seq.left), clean_stm(seq.right));
    }

    /**
     * Handles the special cases of MOVE statements
     * @param move MOVE statement to be cleaned
     * @return a clean statement tree
     */
    private static Stm clean_stm(MOVE move) {
        if (move.dst instanceof TEMP && move.src instanceof CALL) // if moving a CALL result into a TEMP, wrap in a MoveCall
            return reorder_stm(new MoveCall((TEMP)move.dst, (CALL)move.src));
        else if (move.dst instanceof ESEQ) // remove ESEQs from the left side of MOVEs
            return clean_stm(new SEQ(((ESEQ)move.dst).stm, new MOVE(((ESEQ)move.dst).exp, move.src)));
        else return reorder_stm(move); // dst is a MEM, treat normally
    }

    /**
     * Handles the special cases of EXP_STM statements:
     * If the internal expression is a CALL, wrap it in an ExpCall
     * @param stm EXP_STM to be cleaned
     * @return a clean statement tree
     */
    private static Stm clean_stm(EXP_STM stm) {
        if (stm.exp instanceof CALL) return reorder_stm(new ExpCall((CALL)stm.exp));
        else return reorder_stm(stm);
    }

    /**
     * Extracts side-effects from a statement's subexpressions
     * @param stm statement to be reordered
     * @return a reordered statement tree
     */
    private static Stm reorder_stm(Stm stm) {
        // gets any immediate child exps of the statement and extracts side-effects (no ESEQs or CALLs in the exp list)
        StmExpList stm_exps = reorder(stm.kids());
        // creates a new SEQ with side-effects on the left and the (now clean) original stm on the right
        return seq(stm_exps.stm, stm.build(stm_exps.exps));
    }

    /**
     * CLEAN EXPRESSIONS
     */

    /**
     * Extracts side-effects from a list of expressions
     * and returns resulting statements and expressions in a StmExpList
     * @param exps list of expressions to be reordered
     * @return StmExpList with side-effects separated from expressions
     */
    private static StmExpList reorder(List<Exp> exps) {
        // if there are no exps then there are no side-effects, return NOP
        if (exps == null || exps.size() == 0) return new StmExpList(new EXP_STM(new CONST(0)), null);

        // otherwise, return a StmExpList w/ side-effects on left and clean exps on right
        Exp exp = exps.get(0);

        if (exp instanceof CALL) {
            Temp.Temp t = new Temp.Temp();
            exps.set(0, new ESEQ(new MOVE(new TEMP(t), exp), new TEMP(t))); // replace call with clean call
            return reorder(exps); // start over
        } else {
            ESEQ clean = clean_exp(exp); // clean the expression of side-effects
            StmExpList stm_exps = reorder(exps.subList(1, exps.size())); // reorder remaining expressions

            if (can_commute(stm_exps.stm, clean.exp)) {
                stm_exps.exps.add(0, clean.exp); // insert the cleaned exp at the start of the list
                stm_exps.stm = seq(clean.stm, stm_exps.stm); // insert the cleaned stm at the start of the list
                return stm_exps;
            } else {
                // merge the cleaned ESEQ into the cleaned list using a TEMP to maintain order
                Temp.Temp t = new Temp.Temp();
                stm_exps.exps.add(0, new TEMP(t));
                stm_exps.stm = seq(clean.stm, seq(new MOVE(new TEMP(t), clean.exp), stm_exps.stm));
                return stm_exps;
            }
        }
    }

    /**
     * Turns an expression tree into a single ESEQ with no sub-ESEQs
     * @param exp an expression to be cleaned
     * @return an ESEQ with all side-effects in the top statement
     */
    private static ESEQ clean_exp(Exp exp) {
        if (exp instanceof ESEQ) return clean_exp((ESEQ)exp);
        else return reorder_exp(exp);
    }

    /**
     * Removes ESEQs from the expression of an ESEQ
     * @param dirty a dirty ESEQ to be cleaned
     * @return a single, clean ESEQ
     */
    private static ESEQ clean_exp(ESEQ dirty) {
        ESEQ clean = clean_exp(dirty.exp);
        return new ESEQ(seq(clean_stm(dirty.stm),clean.stm), clean.exp);
    }

    /**
     * Extracts side-effects from an expression and assembles an ESEQ
     * @param exp an expression to be reordered
     * @return a clean ESEQ
     */
    private static ESEQ reorder_exp(Exp exp) {
        StmExpList stm_exps = reorder(exp.kids());
        return new ESEQ(stm_exps.stm, exp.build(stm_exps.exps));
    }

    /**
     * LINEARIZE
     */

    /**
     * Flatten a statement tree into a list of statements
     * @param stm a statement tree
     * @param stms a list of statements to be added to
     */
    private static void linearize(Stm stm, List<Stm> stms) {
        if (stm instanceof SEQ) {
            // split all SEQs and recursively add their branches
            SEQ seq = (SEQ)stm;
            linearize(seq.left, stms);
            linearize(seq.right, stms);
        } else {
            stms.add(stm);
        }
    }

    /**
     * UTILITY
     */

    /**
     * Creates a new SEQ from two statements,
     * or just returns one or the other in the case of NOPs
     * @param a a statement
     * @param b another statement
     * @return a statement tree
     */
    private static Stm seq(Stm a, Stm b) {
        if (is_nop(a)) return b;
        else if (is_nop(b)) return a;
        else return new SEQ(a,b);
    }

    /**
     * Naively determines whether or not a statement and expression can commute
     * @param stm a statement
     * @param exp an expression
     * @return whether or not they can commute
     */
    private static boolean can_commute(Stm stm, Exp exp) {
        return is_nop(stm) || exp instanceof NAME || exp instanceof CONST;
    }

    /**
     * Returns true if a statement is an EXP_STM containing a CONST
     * @param stm a statement
     * @return whether the statement is a NOP
     */
    private static boolean is_nop(Stm stm) {
        return stm instanceof EXP_STM && ((EXP_STM)stm).exp instanceof CONST;
    }

    /**
     * A tuple containing a statement and a list of expressions
     */
    private static class StmExpList {
        public Stm stm;
        public List<Exp> exps;

        public StmExpList(Stm stm, List<Exp> exps) {
            this.stm = stm;
            this.exps=exps;
        }
    }

    /**
     * A wrapper statement for a MOVE(TEMP CALL) that hides the CALL
     */
    private static class MoveCall extends Stm {
        private TEMP dst;
        private CALL src;

        public MoveCall(Tree.TEMP d, Tree.CALL s) {
            dst=d; src=s;
        }

        public List<Exp> kids() {
            return src.kids();
        }

        public Stm build(List<Exp> kids) {
            return new MOVE(dst, src.build(kids));
        }

        @Override public void accept(IntVisitor v) { }
    }

    /**
     * A wrapper statement for an EXP(CALL) that hides the call
     */
    private static class ExpCall extends Tree.Stm {
        private Tree.CALL call;

        public ExpCall(Tree.CALL c) {
            call = c;
        }

        public List<Exp> kids() {
            return call.kids();
        }

        public Tree.Stm build(List<Exp> kids) {
            return new EXP_STM(call.build(kids));
        }

        @Override public void accept(IntVisitor v) { }
    }
}