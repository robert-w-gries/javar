class ArrayLength {
    public static void main (String[] a) {
        int[] b;

        b = new int[3];
        b[0] = 7;

        Xinu.printint(b.length);

    }
}