import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF unionFind;
    private WeightedQuickUnionUF uFindFinal;
    private int size;
    private boolean[][] grid;
    public Percolation(int n) {
        // create n-by-n grid, with all sites blocked
        if (n <= 0)
            throw new IllegalArgumentException("Grid size <= 0");
        unionFind = new WeightedQuickUnionUF((n+2)*(n+2));
        uFindFinal = new WeightedQuickUnionUF((n+2)*(n+2));
        size = n;
        grid = new boolean[n+2][n+2];
    }
    public void open(int row, int col) {
        // open site (row, col) if it is not open already
        if (!isOpen(row, col)) {
            grid[row][col] = true;
            if (row == 1) {
                unionFind.union(getLI(row, col), 0);
                uFindFinal.union(getLI(row, col), 0);
            }
            if (row == size)
                uFindFinal.union(getLI(row, col), (size+2)*(size+2)-1);
            if (grid[row-1][col]) {
                unionFind.union(getLI(row, col), getLI(row-1, col));
                uFindFinal.union(getLI(row, col), getLI(row-1, col));
            }
            if (grid[row+1][col]) {
                unionFind.union(getLI(row, col), getLI(row+1, col));
                uFindFinal.union(getLI(row, col), getLI(row+1, col));
            }
            if (grid[row][col-1]) {
                unionFind.union(getLI(row, col), getLI(row, col-1));
                uFindFinal.union(getLI(row, col), getLI(row, col-1));
            }
            if (grid[row][col+1]) {
                unionFind.union(getLI(row, col), getLI(row, col+1));
                uFindFinal.union(getLI(row, col), getLI(row, col+1));
            }
        }
    }
    public boolean isOpen(int row, int col) {
        // is site (row, col) open?
        if (row <= 0 || row > size || col <= 0 || col > size)
            throw new IndexOutOfBoundsException();
        return grid[row][col];
    }
    public boolean isFull(int row, int col) {
        // is site (row, col) full?
        return isOpen(row, col) && unionFind.connected(getLI(row, col), 0);
    }
    public int numberOfOpenSites() {
        // number of open sites
        int counter = 0;
        for (int i = 1; i <= size; i++)
            for (int j = 1; j <= size; j++)
                if (grid[i][j])
                    counter++;
        return counter;
    }
    public boolean percolates() {
        // does the system percolate?
        return uFindFinal.connected(0, (size+2)*(size+2)-1);
    }
    private int getLI(int row, int col) {
        return row*(size+2) + col;
    }

    public static void main(String[] args) {
        Percolation percolation = new Percolation(3);
        percolation.open(1, 1);
        percolation.open(1, 2);
        System.out.println(!percolation.percolates());
        percolation.open(2, 2);
        percolation.open(3, 2);
        System.out.println(percolation.percolates());
        percolation = new Percolation(1);
        System.out.println(!percolation.isFull(1, 1));
        percolation.open(1, 1);
        System.out.println(percolation.isFull(1, 1));
        System.out.println(percolation.percolates());
        percolation = new Percolation(4);
        percolation.open(4, 1);
        percolation.open(3, 1);
        percolation.open(2, 1);
        percolation.open(1, 1);
        percolation.open(1, 4);
        percolation.open(2, 4);
        percolation.open(4, 4);
        System.out.println(!percolation.isFull(4, 4));
    }
}
