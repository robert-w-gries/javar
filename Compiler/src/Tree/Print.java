package Tree;
public class Print implements Tree.IntVisitor {

    private java.io.PrintWriter writer;

    public Print(java.io.PrintWriter o, Tree.Stm s) {
        writer = o;
        s.accept(this, 0); //TODO: check whether this is the correct way to Print
    }

    public void visit(Tree.BINOP e, int d){
        return; //TODO codavaj!!
    }

    public void visit(Tree.CALL e, int d){
        return; //TODO codavaj!!
    }

    public void visit(Tree.CJUMP s, int d){
        return; //TODO codavaj!!
    }

    public void visit(Tree.CONST e, int d){
        return; //TODO codavaj!!
    }

    public void visit(Tree.ESEQ e, int d){
        return; //TODO codavaj!!
    }

    public void visit(Tree.EXP s, int d){
        return; //TODO codavaj!!
    }

    public void visit(Tree.JUMP s, int d){
        return; //TODO codavaj!!
    }

    public void visit(Tree.LABEL s, int d){
        return; //TODO codavaj!!
    }

    public void visit(Tree.MEM e, int d){
        return; //TODO codavaj!!
    }

    public void visit(Tree.MOVE s, int d){
        return; //TODO codavaj!!
    }

    public void visit(Tree.NAME e, int d){
        return; //TODO codavaj!!
    }

    public void visit(Tree.SEQ s, int d){
        return; //TODO codavaj!!
    }

    public void visit(Tree.TEMP e, int d){
        return; //TODO codavaj!!
    }

}
