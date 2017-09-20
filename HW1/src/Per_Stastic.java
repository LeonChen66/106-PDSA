import java.util.Random;
public class Per_Stastic {
    public static void main(String[] args) {
        int n = 10;
        int trial = 10000000;
        Percolation pr = new Percolation(n);
        Random ran = new Random();
        int count = 0;
        float average_sum = 0;
        float average;
        for(int k=0;k<trial;k++) {
            while (!pr.isPercolated()) {
                int i = ran.nextInt(10) + 1;
                int j = ran.nextInt(10) + 1;
                if (pr.isOpen(i, j) == false) {
                    pr.open_node(i, j);
                    count++;
                }
            }
            average = (float)count/(n*n);
            average_sum += average;
        }
        average_sum = average_sum/trial;
        StdOut.print(average_sum);
    }
}
