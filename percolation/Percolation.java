import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] open;
    private final int size;
    private int opNum;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF ufNoBottom;
    private final int top;
    private final int bottom;
    private final int[] dxdy = {-1, 0, 1, 0, -1};

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("Please put in a positive number");

        open = new boolean[n][n];
        size = open.length;
        // 2 extra "root" nodes: size*size amd size*size+1
        uf = new WeightedQuickUnionUF(size * size + 2);
        ufNoBottom = new WeightedQuickUnionUF(size * size + 1);
        top = size * size;
        bottom = size * size + 1;
    }

    // transfer a 2D coordinate into a 1D UF coordinate
    private int transToUF(int row, int col) {
        return (row - 1) * size + (col - 1);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || row > size) throw new IllegalArgumentException("Row out of bounds");
        if (col < 1 || col > size) throw new IllegalArgumentException("Column out of bounds");

        if (!isOpen(row, col)) {
            open[row - 1][col - 1] = true;
            opNum++;

            int site = transToUF(row, col);
            // four neighbor sites (r, c), respectively (-1, 0) (0, 1) (1, 0) (0, -1)
            for (int i = 0; i < dxdy.length - 1; i++) {
                int r = row + dxdy[i];
                int c = col + dxdy[i + 1];
                // if a neighbor is not out of the boundary
                if (r > 0 && r <= size && c > 0 && c <= size) {
                    if (open[r - 1][c - 1]) {
                        uf.union(site, transToUF(r, c));
                        ufNoBottom.union(site, transToUF(r, c));
                    }
                }
            }

            // boundary operations
            if (row == 1) {
                uf.union(site, top);
                ufNoBottom.union(site, top);
            }
            if (row == size) {
                uf.union(site, bottom);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || row > size) throw new IllegalArgumentException("Row out of bounds");
        if (col < 1 || col > size) throw new IllegalArgumentException("Column out of bounds");

        return open[row - 1][col - 1];
    }

    // is the site (row, col) full?
    // note: not all bottom node that connected to the bottom are full (backwash)
    public boolean isFull(int row, int col) {
        if (row < 1 || row > size) throw new IllegalArgumentException("Row out of bounds");
        if (col < 1 || col > size) throw new IllegalArgumentException("Column out of bounds");

        if (!open[row - 1][col - 1]) return false;
        else return ufNoBottom.find(transToUF(row, col)) == ufNoBottom.find(top);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return opNum;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(top) == uf.find(bottom);
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}