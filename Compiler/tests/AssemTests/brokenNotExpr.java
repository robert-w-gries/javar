class notExpr {
    public static void main(String[] a){
        Xinu.printint(new LS().Start(10));
    }
}

class LS {
    
    public int Start(int sz) {

        int aux01;
        int aux02;

        aux01 = 12;
        aux02 = 12+1;

        if (!(aux01 < aux02)) {
            Xinu.print("if");
            Xinu.println();
        }

        return 5;

    }

}
