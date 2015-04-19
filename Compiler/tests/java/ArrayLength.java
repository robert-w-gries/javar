class ArrayLength{
    public static void main(String[] a){
        Xinu.printint(new ArrayLength().Start(20));
    }
}
​

class ArrayLengthTest {

    int[] number ;

    public int Start(int sz){

        number = new int[sz];

        Xinu.print("number.length = ");
        Xinu.printint(number.length);

        return 5;

    }

}