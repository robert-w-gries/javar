package Tree;
public interface IntVisitor {

    abstract void visit(Tree.BINOP n, int d);

    abstract void visit(Tree.CALL n, int d);

    abstract void visit(Tree.CJUMP n, int d);

    abstract void visit(Tree.CONST n, int d);

    abstract void visit(Tree.ESEQ n, int d);

    abstract void visit(Tree.EXP n, int d);

    abstract void visit(Tree.JUMP n, int d);

    abstract void visit(Tree.LABEL n, int d);

    abstract void visit(Tree.MEM n, int d);

    abstract void visit(Tree.MOVE n, int d);

    abstract void visit(Tree.NAME n, int d);

    abstract void visit(Tree.SEQ n, int d);

    abstract void visit(Tree.TEMP n, int d);

}
