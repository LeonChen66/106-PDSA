
/*
created by Leon for PDSA
Percolation Question
 */


// index means turn 2d to 1d present

public class Percolation{
    // UF function
    private final WeightedQuickUnionUF UF;
    public final WeightedQuickUnionUF UF_isFull;
    // main matrix
    private int matrix_size;
    public final boolean[][] matrix;
    // Top and BottomSite
    private int virtual_top;
    private int virtual_bot;

    public Percolation(int n) {
//        if (n<=0){
//            throw new IllegalArgumentException("N must be at least 1");
//        }
        matrix_size = n;
        matrix = new boolean[n][n];

        UF = new WeightedQuickUnionUF(n*n+2);
        UF_isFull = new WeightedQuickUnionUF(n*n+1);

        virtual_top = 0;
        virtual_bot = n*n+1;
    }
//    //copy object
//    protected Percolation clone() throws CloneNotSupportedException {
//        return (Percolation) super.clone();
//   }

    public void open_node (int i,int j) {
        if (isOpen(i, j)==false) { // Check the node if already been opend.
            int Index = get1d_size(i, j);
            // first row
            if (i == 1) {
                UF.union(Index, virtual_top);
                UF_isFull.union(Index, virtual_top);
            }
            if (i == matrix_size) {
                UF.union(virtual_bot, Index);
            }

            //connect rest of the nodes if open
            connect_open(Index, i + 1, j);
            connect_open(Index, i, j + 1);
            connect_open(Index, i - 1, j);
            connect_open(Index, i, j - 1);

            matrix[i - 1][j - 1] = true;
        }
    }
    public boolean isOpen(int i, int j){
        return matrix[i-1][j-1];
    }

    public boolean isFull(int i, int j){
        if(isOpen(i, j)){
            int Index = get1d_size(i, j);
            return UF_isFull.connected(Index, virtual_top);
        }
        return false;
    }
    // Check is percolated
    public boolean isPercolated(){
        return UF.connected(virtual_bot, virtual_top);
    }

    // connect the nodes if nodes open
    private void connect_open(int Index, int i, int j){
        try{
            if(isOpen(i, j)){
                int neighbor = get1d_size(i, j);
                UF.union(Index, neighbor);
                UF_isFull.union(Index, neighbor);
            }
        }catch(Exception e) {// not to connect the out of bound node
            }
        }


    private int get1d_size(int i, int j){
        return (i-1) * matrix_size + j;
    }

    public static void main(String[] args) {
        In br = new In(args[0]);
        String data = br.readLine();
        int N = Integer.parseInt(data);
        //StdOut.print(N + "\n");
        //String line;
        Percolation pr = new Percolation(N);
        while (br.hasNextLine()) {
            String vals = br.readLine();
            //StdOut.print(vals);
            //StdOut.print(vals.split(",")[0]+"," + vals.split(",")[1]+"\n");
            int i = Integer.parseInt(vals.split(",")[0]);
            int j = Integer.parseInt(vals.split(",")[1]);
            pr.open_node(i, j);
        }
        if (pr.isPercolated()) {
            StdOut.print(0);
        } else {
            // test
            outerloop:
            for (int i = 1; i <= N; i++) {
                for (int j = 1; j <= N; j++) {
                    In br2 = new In(args[0]);
                    String data2 = br2.readLine();
                    Percolation pr2 = new Percolation(N);
                    while (br2.hasNextLine()) {
                        String vals2 = br2.readLine();
                        //StdOut.print(vals);
                        //StdOut.print(vals.split(",")[0]+"," + vals.split(",")[1]+"\n");
                        int p = Integer.parseInt(vals2.split(",")[0]);
                        int q = Integer.parseInt(vals2.split(",")[1]);
                        pr2.open_node(p, q);
                    }
                    pr2.open_node(i, j);
                    if (pr2.isPercolated()) {
                        StdOut.print(i + "," + j + "\n");
                        break outerloop;
                    }
                    else if(i==N&&j==N){
                        StdOut.print(-1);
                    }
                }
            }
        }
    }
}

