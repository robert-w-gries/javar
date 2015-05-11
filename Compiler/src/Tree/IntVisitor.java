package Tree;
public interface IntVisitor {

    abstract void visit(Tree.BINOP n);

    abstract void visit(Tree.CALL n);

    abstract void visit(Tree.CJUMP n);

    abstract void visit(Tree.CONST n);

    abstract void visit(Tree.ESEQ n);

    abstract void visit(Tree.EXP_STM n);

    abstract void visit(Tree.JUMP n);

    abstract void visit(Tree.LABEL n);

    abstract void visit(Tree.MEM n);

    abstract void visit(Tree.MOVE n);

    abstract void visit(Tree.NAME n);

    abstract void visit(Tree.SEQ n);

    abstract void visit(Tree.TEMP n);

}
