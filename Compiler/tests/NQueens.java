/* A MiniJava program to solve the 8-queens problem */
class NQueens {
    public static void main (String[] a) {
	int n;
	int x;
	Queens2 q;
	Xinu.print("Number of queens? ");
	n = Xinu.readint();
	q = new Queens2().init(n);
	x = q.run(0);
    }

}

class Queens2 {
    int N;
    int[] row;
    int[] col;
    int[] diag1;
    int[] diag2;

    public Queens2 init(int n) {
	N = n;
	row = new int[n];
	col = new int[n];
	diag1 = new int[n+n-1];
	diag2 = new int[n+n-1];
	return this;
    }

    public int run (int c) {
	int x;
	int r = 0;
	if (c == this.N)
	    x = this.printboard();
	else {
	    r = 0;
	    while (r < this.N) {
		if (this.row[r] == 0
		    && this.diag1[r+c] == 0
		    && this.diag2[r+N-1-c] == 0) {
		    row[r]       = 1;
		    diag1[r+c]   = 1;
		    diag2[r+N-1-c] = 1;
		    col[c]       = r;
		    x= this.run(c+1);
		    row[r]       = 0;
		    diag1[r+c]   = 0;
		    diag2[r+N-1-c] = 0;
		}
		r = r+1;
	    }
	}	
	return 0;
    }

    public int printboard() {
	int i = 0;
	int j = 0;
	while (i < this.N) {
	    j = 0;
	    while (j < this.N) {
		if (this.col[i] == j)
		    Xinu.print(" Q");
		else
		    Xinu.print(" .");
		j = j+1;
	    }
	    Xinu.println();
	    i = i+1;
	}
	Xinu.println();
	return 0;
    }
}
