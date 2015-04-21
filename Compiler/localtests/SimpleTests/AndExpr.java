// PASSES for "z = x && y", FAILS for "if (x && y)"
class Test {

    public static void main(String[] a) {

        boolean x = true;
        boolean y = false;
        int i;
        if (x && y) {
            i = 0;
        }

    }

}