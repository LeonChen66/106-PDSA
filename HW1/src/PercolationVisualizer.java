import java.awt.Font;

public class PercolationVisualizer {
    // delay in miliseconds (controls animation speed)
    private static final int DELAY = 100;

    // draw N-by-N percolation system
    public static void draw(Percolation perc, int N) {
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setXscale(-.05*N, 1.05*N);
        StdDraw.setYscale(-.05*N, 1.05*N);   // leave a border to write text
        StdDraw.filledSquare(N/2.0, N/2.0, N/2.0);

        // draw N-by-N grid
        int opened = 0;
        for (int row = 1; row <= N; row++) {
            for (int col = 1; col <= N; col++) {
                if (perc.isFull(row, col)) {
                    StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
                    opened++;
                }
                else if (perc.isOpen(row, col)) {
                    StdDraw.setPenColor(StdDraw.WHITE);
                    opened++;
                }
                else
                    StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.filledSquare(col - 0.5, N - row + 0.5, 0.45);
            }
        }

        // write status text
        StdDraw.setFont(new Font("SansSerif", Font.PLAIN, 12));
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.text(.25*N, -N*.025, opened + " open sites");
        if (perc.isPercolated())
            StdDraw.text(.75*N, -N*.025, "percolates");
        else
            StdDraw.text(.75*N, -N*.025, "does not percolate");

    }




    public static void main(String[] args) {
        In br = new In(args[0]);
        String data = br.readLine();
        int N = Integer.parseInt(data);
        //StdOut.print(N + "\n");
        //String line;
        // turn on animation mode
        StdDraw.show(0);
        Percolation pr = new Percolation(N);
        draw(pr, N);
        StdDraw.show(DELAY);
        while (br.hasNextLine()) {
            String vals = br.readLine();
            //StdOut.print(vals);
            //StdOut.print(vals.split(",")[0]+"," + vals.split(",")[1]+"\n");
            int i = Integer.parseInt(vals.split(",")[0]);
            int j = Integer.parseInt(vals.split(",")[1]);
            pr.open_node(i, j);
            draw(pr,N);
            StdDraw.show(DELAY);
        }
    }
}
