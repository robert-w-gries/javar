// PASSES (both "y = !x" and "if (!x)", doesn't match brylow's but should be the same functionality)
class Test {

    public static void main(String[] a) {

        boolean x = true;
        int i;
        if (!x) {
            i = 0;
        }

    }

}