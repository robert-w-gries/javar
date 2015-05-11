class BinarySearch{
    public static void main(String[] a){
        Xinu.printint(new BS().Start(20));
    }
}
// This class contains an array of integers and
// methods to initialize, print and search the array
// using Binary Search

class BS{

    int[] number ;

    public int Start(int sz){

        number = new int[sz];

        Xinu.print("number.length = ");
        Xinu.printint(number.length);

        return 5;

    }

}
