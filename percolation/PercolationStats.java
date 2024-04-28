import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] thre;
    private double mean;
    private double stddev;
    private double confidenceLo;
    private double confidenceHi;
    
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0) throw new IllegalArgumentException("Size should be a positive number");
        if (trials <= 0) throw new IllegalArgumentException("Trials should be a positive number");

        thre = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation per = new Percolation(n);
            int count = 0;

            while (!per.percolates()) {
                int row = StdRandom.uniformInt(n) + 1;
                int col = StdRandom.uniformInt(n) + 1;

                if (!per.isOpen(row, col)) {
                    per.open(row, col);
                    count++;
                }
            }

            thre[i] = (double) count / (n * n);
        }

        mean = StdStats.mean(thre);
        stddev = StdStats.stddev(thre);
        double devi = 1.96 * stddev / Math.sqrt(trials);
        confidenceLo = mean - devi;
        confidenceHi = mean + devi;
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return confidenceLo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return confidenceHi;
    }

   // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats perS = new PercolationStats(n, trials);

        System.out.println("mean                    = " + perS.mean());
        System.out.println("stddev                  = " + perS.stddev());
        System.out.println("95% confidence interval = [" + perS.confidenceLo() + ", " + perS.confidenceHi() + "]");
    }

}
