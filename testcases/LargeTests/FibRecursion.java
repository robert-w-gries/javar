class FibRecursion {
    public static void main (String[] a) {
        int n = 0;
        Fib f = new Fib();
        Xinu.print("Enter the n: ");
        n = Xinu.readint();
        Xinu.println();
        n = f.fib(n);
        Xinu.printint(n);
    }

}

class Fib {

    public int fib(int n) {

        int x;
        if (n < 2) {
            x = n;
        } else {
            x = this.fib(n-1) + this.fib(n-2);
        }

        return x;

    }

}
