package Absyn;

import java.io.PrintWriter;

/**
 * Visitor prints AST in reparseable form.
 */
public class PrintVisitor implements Visitor{

    private java.io.PrintWriter printOut;
    private int numTabs = 0;

    public PrintVisitor(){
         printOut = new PrintWriter(System.out);
    }

    public PrintVisitor(java.io.PrintWriter out){
         printOut = out;
    }

    private void printClass(String className) {

        printOut.printf("%-" + numTabs);
        printOut.print(className + "(");
        numTabs++;

    }

    /**
     * Description copied from interface:
     * Visitor pattern dispatch.
     */
    public void visit(java.util.AbstractList list){

        printClass("AbstractList");

        for (int i = 0; i < list.size(); i++) {
            ((Absyn)list.get(i)).accept(this);
        }

        printOut.println(")");

        return;
    }

    public void visit(AddExpr ast) {

        printClass("AddExpr");

        ast.accept(this);
        printOut.println();

        return;

    }

    public void visit(AndExpr ast){

        printClass("AndExpr");

        ast.accept(this);
        printOut.println();

        return;
    }

    public void visit(ArrayExpr ast){

        printClass("ArrayExpr");
        return;
    }

    public void visit(ArrayType ast){
        return; //TODO codavaj!!
    }

    public void visit(AssignStmt ast){
        return; //TODO codavaj!!
    }

    public void visit(BlockStmt ast){
        return; //TODO codavaj!!
    }

    public void visit(BooleanType ast){
        return; //TODO codavaj!!
    }

    public void visit(CallExpr ast){
        return; //TODO codavaj!!
    }

    public void visit(ClassDecl ast){
        return; //TODO codavaj!!
    }

    public void visit(DivExpr ast){
        return; //TODO codavaj!!
    }

    public void visit(EqualExpr ast){
        return; //TODO codavaj!!
    }

    public void visit(FalseExpr ast){
        return; //TODO codavaj!!
    }

    public void visit(FieldExpr ast){
        return; //TODO codavaj!!
    }

    public void visit(Formal ast){
        return; //TODO codavaj!!
    }

    public void visit(GreaterExpr ast){
        return; //TODO codavaj!!
    }

    public void visit(IdentifierExpr ast){
        return; //TODO codavaj!!
    }

    public void visit(IdentifierType ast){
        return; //TODO codavaj!!
    }

    public void visit(IfStmt ast){
        return; //TODO codavaj!!
    }

    public void visit(IntegerLiteral ast){
        return; //TODO codavaj!!
    }

    public void visit(IntegerType ast){
        return; //TODO codavaj!!
    }

    public void visit(LesserExpr ast){
        return; //TODO codavaj!!
    }

    public void visit(MethodDecl ast){
        return; //TODO codavaj!!
    }

    public void visit(MulExpr ast){
        return; //TODO codavaj!!
    }

    public void visit(NegExpr ast){
        return; //TODO codavaj!!
    }

    public void visit(NewArrayExpr ast){
        return; //TODO codavaj!!
    }

    public void visit(NewObjectExpr ast){
        return; //TODO codavaj!!
    }

    public void visit(NotEqExpr ast){
        return; //TODO codavaj!!
    }

    public void visit(NotExpr ast){
        return; //TODO codavaj!!
    }

    public void visit(NullExpr ast){
        return; //TODO codavaj!!
    }

    public void visit(OrExpr ast){
        return; //TODO codavaj!!
    }

    /**
     * Visitor pattern dispatch.
     */
    public void visit(Program ast){
        return; //TODO codavaj!!
    }

    public void visit(StringLiteral ast){
        return; //TODO codavaj!!
    }

    public void visit(SubExpr ast){
        return; //TODO codavaj!!
    }

    public void visit(ThisExpr ast){
        return; //TODO codavaj!!
    }

    public void visit(ThreadDecl ast){
        return; //TODO codavaj!!
    }

    public void visit(TrueExpr ast){
        return; //TODO codavaj!!
    }

    public void visit(VarDecl ast){
        return; //TODO codavaj!!
    }

    public void visit(VoidDecl ast){
        return; //TODO codavaj!!
    }

    public void visit(WhileStmt ast){
        return; //TODO codavaj!!
    }

    public void visit(XinuCallExpr ast){
        return; //TODO codavaj!!
    }

    public void visit(XinuCallStmt ast){
        return; //TODO codavaj!!
    }

}
