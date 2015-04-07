package Tree;
public class Print implements Tree.IntVisitor {

    private java.io.PrintWriter writer;
    private int numTabs;

    public Print(java.io.PrintWriter o, Tree.Stm s) {
        writer = o;
        numTabs = 0;
        s.accept(this);
    }

    private void printTabs() {
        for (int i = 0; i < numTabs; i++) {
            writer.print(" ");
        }
    }

    private void printClass(String name) {

        printTabs();
        writer.print(name + "(");

    }

    private void incrementTab() {
        numTabs++;
    }

    private void decrementTab() {
        writer.print(")");
        numTabs--;
    }

    public void visit(Tree.BINOP e) {

        //print BINOP and opening paren; increment the number of tabs
        printClass(e.getClass().getSimpleName());
        incrementTab();

        //Print the BINOP operation
        writer.println(e.binop.toString());

        //visit the left and right expressions
        e.left.accept(this);
        writer.println();
        e.right.accept(this);

        //print closing paren and decrement number of tabs
        decrementTab();

    }

    public void visit(Tree.CALL e){

        printClass(e.getClass().getSimpleName());

        incrementTab();
        writer.println();

        e.func.accept(this);
        writer.println();

        for (Exp myExp : e.args) {
            myExp.accept(this);
            writer.println();
        }

        //special case of printing tabs and closing parens
        numTabs--;
        printTabs();
        writer.print(")");

    }

    public void visit(Tree.CJUMP s) {

        printClass(s.getClass().getSimpleName());

        incrementTab();
        writer.println(s.relop.toString());

        s.left.accept(this);
        writer.println();

        s.right.accept(this);
        writer.println();

        printTabs();
        writer.print(s.iffalse.toString() + " " + s.iftrue.toString());

        decrementTab();

    }

    public void visit(Tree.CONST e) {

        printClass(e.getClass().getSimpleName());

        writer.print(e.value + ")");

    }

    public void visit(Tree.ESEQ e) {

        printClass(e.getClass().getSimpleName());

        incrementTab();
        writer.println();

        e.stm.accept(this);
        writer.println();
        e.exp.accept(this);

        decrementTab();

    }

    public void visit(Tree.EXP_STM s) {

        printClass("EXP");

        incrementTab();
        writer.println();

        s.exp.accept(this);

        decrementTab();

    }

    public void visit(Tree.JUMP s){

        printClass(s.getClass().getSimpleName());

        incrementTab();
        writer.println();

        s.exp.accept(this);

        decrementTab();

    }

    public void visit(Tree.LABEL s) {

        printClass(s.getClass().getSimpleName());

        writer.print(s.label.toString() + ")");

    }

    public void visit(Tree.MEM e){

        printClass(e.getClass().getSimpleName());

        incrementTab();
        writer.println();

        e.exp.accept(this);

        decrementTab();

    }

    public void visit(Tree.MOVE s){

        printClass(s.getClass().getSimpleName());

        incrementTab();
        writer.println();

        s.dst.accept(this);
        writer.println();
        s.src.accept(this);

        decrementTab();

    }

    public void visit(Tree.NAME e){

        printClass(e.getClass().getSimpleName());

        writer.print(e.label.toString());
        writer.print(")");

    }

    public void visit(Tree.SEQ s){

        printClass(s.getClass().getSimpleName());

        incrementTab();
        writer.println();

        s.left.accept(this);
        writer.println();
        s.right.accept(this);

        decrementTab();

    }

    public void visit(Tree.TEMP e) {

        printClass(e.getClass().getSimpleName());

        writer.print(e.temp.toString() + ")");

    }

}
