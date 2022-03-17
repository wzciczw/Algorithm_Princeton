import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation {
    private graph g;

    private class graph {
        boolean[] status;
        WeightedQuickUnionUF wqu;
        int size;

        public graph(int n) {
            size = n;
            status = new boolean[n * n + 2];
            for (int i = 0; i < status.length; i++) {
                status[i] = false;//false means close
            }
            wqu = new WeightedQuickUnionUF(n * n + 2);
            for (int i = 1; i < n + 1; i++) {
                wqu.union(0, i);
            }
            for (int i = n * (n - 1) + 1; i < n * n + 1; i++) {
                wqu.union(n * n + 1, i);
            }

        }
    }

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        g = new graph(n);
    }

    // opens the site (row, col) if it is not open already and connect the graph
    public void open(int row, int col) {
        if (!isOpen(row, col)) {
            g.status[trans(row, col)] = true;
            if (!isOverCorner(row - 1, col) && isOpen(row - 1, col))
                g.wqu.union(trans(row, col), trans(row - 1, col));
            if (!isOverCorner(row + 1, col) && isOpen(row + 1, col))
                g.wqu.union(trans(row, col), trans(row + 1, col));
            if (!isOverCorner(row, col - 1) && isOpen(row, col - 1))
                g.wqu.union(trans(row, col), trans(row, col - 1));
            if (!isOverCorner(row, col + 1) && isOpen(row, col + 1))
                g.wqu.union(trans(row, col), trans(row, col + 1));
        }
    }

    //is the site(row,col) overcorner?
    private boolean isOverCorner(int row, int col) {
        if (row > g.size || row < 1 || col >  g.size || col < 1) {
            return true;         //return true if overcorner
        }
        return false;
    }

    //transform the site(row,col) into index
    private int trans(int row, int col) {
        return (row-1) * g.size + col;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return g.status[trans(row, col)];  //return false if closed
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (g.wqu.find(trans(row, col)) == g.wqu.find(0) && isOpen(row, col)) {
            return true;
        }
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int num = 0;
        for (int i = 1; i < g.size * g.size + 1; i++) {
            if (g.status[i]) {
                num++;
            }
        }
        return num;
    }

    // does the system percolate?  return true if percolate
    public boolean percolates() {
        if(g.size==1){
            return isOpen(1,1);
        }
        return g.wqu.find(0) == g.wqu.find(g.size * g.size + 1);
    }


}