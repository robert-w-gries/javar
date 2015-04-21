class Parse1 {
    public static void main (String[] a) {
        int[][] b;

        b = new int[3][];
        b[0] = new int[3];
        b[1] = new int[4];
        b[2] = new int[5];

        b[0][0] = 2;
        Xinu.printint(b[0][0]);

    }
}