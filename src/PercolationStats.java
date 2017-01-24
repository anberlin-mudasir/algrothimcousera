import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] thresholds;
    private double mean;
    private double std;
    private double conLo;
    private double conHi;

    public PercolationStats(int n, int trials) {
       // perform trials independent experiments on an n-by-n grid
       if (n <= 0 || trials <= 0) {
           throw new IllegalArgumentException();
       }
       thresholds = new double[trials];
       for (int i = 0; i < trials; i++) {
           Percolation percolation = new Percolation(n);
           int[] rangeArr = new int[n*n];
           for (int j = 0; j < rangeArr.length; j++)
               rangeArr[j] = j;
           StdRandom.shuffle(rangeArr);
           for (int j = 0; j < rangeArr.length; j++) {
               int row = rangeArr[j] / n + 1;
               int col = rangeArr[j] % n + 1;
               percolation.open(row, col);
               if (percolation.percolates()) {
                   thresholds[i] = (j + 1) / (double) (n*n);
                   break;
               }
           }
       }
       mean = StdStats.mean(thresholds);
       std = StdStats.stddev(thresholds);
       double sqrtTrial = Math.sqrt(trials);
       double conThreshould = 1.96 * std / sqrtTrial;
       conLo = mean - conThreshould;
       conHi = mean + conThreshould;
    }
    public double mean() {
        // sample mean of percolation threshold
        return mean;
    }
    public double stddev() {
        // sample standard deviation of percolation threshold
        return std;
    }
    public double confidenceLo() {
        // low  endpoint of 95% confidence interval
        return conLo;
    }
    public double confidenceHi() {
        // high endpoint of 95% confidence interval
        return conHi;
    }

    public static void main(String[] args) {
        // test client (described below)
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats pStats = new PercolationStats(n, t);
        StdOut.printf("mean = %f\n", pStats.mean());
        StdOut.printf("stddev = %f\n", pStats.stddev());
        StdOut.printf("95%% confidence interval = %f, %f\n",
                pStats.confidenceLo(), pStats.confidenceHi());
    }
}