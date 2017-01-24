import java.util.Arrays;

public class UnionFind {
    private int[] parents;
    public UnionFind(int n) {
        parents = new int[n];
        Arrays.fill(parents, -1);
    }

    public boolean union(int x, int y) {
        x = root(x);
        y = root(y);
        if (x != y) {
            if (x < y) {
                parents[x] += parents[y];
                parents[y] = x;
            } else {
                parents[y] += parents[x];
                parents[x] = y;
            }
            return true;
        }
        return false;
    }

    public boolean connected(int x, int y) {
        return root(x) == root(y);
    }

    private int root(int n) {
        if (parents[n] < 0)
            return n;
        parents[n] = root(parents[n]);
        return parents[n];
    }
}

