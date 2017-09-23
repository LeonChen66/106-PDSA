
/*
created by Leon for PDSA
Percolation Question
 */
import java.util.ArrayList;
// index means turn 2d to 1d present

public class Percolation implements Cloneable{
    // UF function
    public WeightedQuickUnionUF UF;
    public WeightedQuickUnionUF UF_isFull;
    // main matrix
    private int matrix_size;
    public final boolean[][] matrix;
    // Top and BottomSite
    private int virtual_top;
    private int virtual_bot;

    public Percolation(int n) {
        if (n<=0){
            throw new IllegalArgumentException("N must be at least 1");
        }
        matrix_size = n;
        matrix = new boolean[n][n];

        UF = new WeightedQuickUnionUF(n*n+2);
        UF_isFull = new WeightedQuickUnionUF(n*n+1);

        virtual_top = 0;
        virtual_bot = n*n+1;
    }


    public void open_node (int i,int j) {
        if (isOpen(i, j)==false) { // Check the node if already been opend.
            int Index = get1d_size(i, j);
            // first row
            if (i == 1) {
                UF.union(virtual_top, Index);
                UF_isFull.union(virtual_top, Index);
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
    // find the next open point
    public boolean isNext(int i, int j){
        ArrayList<Integer> checkList = new ArrayList<Integer>();
        if(isOpen(i,j)==false){
            checkList.add(check_node(i+1,j));
            checkList.add(check_node(i-1,j));
            checkList.add(check_node(i,j+1));
            checkList.add(check_node(i,j-1));
        }
        else{
            return false;
        }
        if (checkList.contains(UF.find(virtual_top)) && checkList.contains(UF.find(virtual_bot))){
            return true;
        }
        else if (i==matrix_size && checkList.contains(UF.find(virtual_top))){
            return true;
        }
        else if (i==1 && checkList.contains(UF.find(virtual_bot))){
            return true;
        }
        else{
            return false;
        }
    }

    public int check_node(int i, int j){
        try{
            if(isOpen(i,j)) {
                int neighbor = get1d_size(i, j);
                return UF.find(neighbor);
            }
            else{
                return -1;
            }
        }catch (Exception e){
            return -1;
        }
    }

    // Check is percolated
    public boolean isPercolated(){
        return UF.connected(virtual_top, virtual_bot);
    }

    // connect the nodes if nodes open
    private void connect_open(int Index, int i, int j){
        try{
            if(isOpen(i, j)){
                int neighbor = get1d_size(i, j);
                UF.union(neighbor, Index);
                UF_isFull.union(neighbor, Index);
            }
        }catch(Exception e) {// not to connect the out of bound node
            }
        }


    private int get1d_size(int i, int j){
        return (i-1) * matrix_size + j;
    }

    public static void main(String[] args) throws Exception {
        try {
            In br = new In(args[0]);
            String data = br.readLine();
            int N = Integer.parseInt(data);
            Percolation pr = new Percolation(N);
            ArrayList<ArrayList<Integer>> list = new ArrayList<ArrayList<Integer>>();
            list.add( new ArrayList<Integer>());
            list.add( new ArrayList<Integer>());
            int count = 0;
            while (br.hasNextLine()) {
                String vals = br.readLine();
                int i = Integer.parseInt(vals.split(",")[0]);
                int j = Integer.parseInt(vals.split(",")[1]);
                list.get(0).add(i);
                list.get(1).add(j);
                pr.open_node(i, j);
            }
            int ptsNumber = list.get(0).size();
            if (pr.isPercolated()) {
                StdOut.print(0);
            }
            else if (N==1){
                StdOut.print(N+","+N);
            }
            else {
                // test
                outerloop:
                for(int i=1;i<=N;i++){
                    for(int j=1;j<=N;j++){
                        Percolation pr2 = new Percolation(N);
                        for (int k=0;k<ptsNumber;k++){
                            int p = list.get(0).get(k);
                            int q = list.get(1).get(k);
                            pr2.open_node(p,q);
                    }
                        pr2.open_node(i,j);
                        if (pr2.isPercolated()){
                            StdOut.print(i+","+j);
                            break outerloop;
                        }
                        else if (i==N && j==N){
                            StdOut.print(-1);
                        }
                }
            }
            // Fast find next open node
//                outerloop:
//                for(int i=1;i<=N;i++) {
//                    for (int j = 1; j <= N; j++) {
//                        if (pr.isNext(i,j)){
//                            StdOut.print(i+","+j);
//                            break outerloop;
//                        }
//                        else if (i==N && j==N){
//                            StdOut.print(-1);
//                    }
//                    }
//                }
        }
        }
        catch (Exception e){}
    }
}

