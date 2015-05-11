class myClass{
    public static void main(String[] a){
        Xinu.printint(new Element().Equal(null));
    }
}

class Element {

    int Age;
    boolean Married;

    public int GetAge() {
        return Age;
    }

    public boolean GetMarried() {
        return Married;
    }


    public int Equal(Element other) {

        boolean ret_val;
        int nt;

        //Neither methods work
        //ret_val = other.GetMarried();
        nt = other.GetAge();

        return 1;
    }

}