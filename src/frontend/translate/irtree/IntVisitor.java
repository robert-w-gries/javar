package frontend.translate.irtree;
public interface IntVisitor {

    abstract void visit(BINOP n);

    abstract void visit(CALL n);

    abstract void visit(CJUMP n);

    abstract void visit(CONST n);

    abstract void visit(ESEQ n);

    abstract void visit(EXP_STM n);

    abstract void visit(JUMP n);

    abstract void visit(LABEL n);

    abstract void visit(MEM n);

    abstract void visit(MOVE n);

    abstract void visit(NAME n);

    abstract void visit(SEQ n);

    abstract void visit(TEMP n);

}
