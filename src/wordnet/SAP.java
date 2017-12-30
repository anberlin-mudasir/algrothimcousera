import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;

public class SAP {
    private static final int INFINITY = Integer.MAX_VALUE;
    private final Digraph digraph;

    public SAP(Digraph G) {
        digraph = new Digraph(G);
    }

    private boolean isValid(int v) {
        return v >= 0 && v < digraph.V();
    }

    private boolean isValid(Iterable<Integer> v) {
        for (int vv : v)
            if (!isValid(vv))
                return false;
        return true;
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        int minAncestor = ancestor(v, w);
        BreadthFirstDirectedPaths vPath = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths wPath = new BreadthFirstDirectedPaths(digraph, w);
        if (minAncestor == -1) {
            return -1;
        }
        return vPath.distTo(minAncestor) + wPath.distTo(minAncestor);
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        if (!isValid(v) || !isValid(w))
            throw new IllegalArgumentException();
        BreadthFirstDirectedPaths vPath = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths wPath = new BreadthFirstDirectedPaths(digraph, w);
        ArrayList<Integer> ancestorList = new ArrayList<>(digraph.V()/2);
        int minAncestor = -1;
        int minLength = INFINITY;
        for (int i = 0; i < digraph.V(); i++) {
            if (vPath.hasPathTo(i) && wPath.hasPathTo(i)) {
                ancestorList.add(i);
            }
        }
        for (int i: ancestorList) {
            int ancestorDist = vPath.distTo(i) + wPath.distTo(i);
            if (ancestorDist < minLength) {
                minLength = ancestorDist;
                minAncestor = i;
            }
        }
        return minAncestor;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        int minAncestor = ancestor(v, w);
        BreadthFirstDirectedPaths vPath = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths wPath = new BreadthFirstDirectedPaths(digraph, w);
        if (minAncestor == -1) {
            return -1;
        }
        return vPath.distTo(minAncestor) + wPath.distTo(minAncestor);
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null || !isValid(v) || !isValid(w))
            throw new IllegalArgumentException();
        BreadthFirstDirectedPaths vPath = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths wPath = new BreadthFirstDirectedPaths(digraph, w);
        ArrayList<Integer> ancestorList = new ArrayList<>(digraph.V()/2);
        int minAncestor = -1;
        int minLength = INFINITY;
        for (int i = 0; i < digraph.V(); i++) {
            if (vPath.hasPathTo(i) && wPath.hasPathTo(i)) {
                ancestorList.add(i);
            }
        }
        for (int i: ancestorList) {
            int ancestorDist = vPath.distTo(i) + wPath.distTo(i);
            if (ancestorDist < minLength) {
                minLength = ancestorDist;
                minAncestor = i;
            }
        }
        return minAncestor;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In("input/wordnet/digraph1.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        int length   = sap.length(3, 11);
        int ancestor = sap.ancestor(3, 11);
        // 4 1
        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        length   = sap.length(9, 12);
        ancestor = sap.ancestor(9, 12);
        // 3 5
        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        length   = sap.length(7, 2);
        ancestor = sap.ancestor(7, 2);
        // 4 0
        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        length   = sap.length(1, 6);
        ancestor = sap.ancestor(1, 6);
        // -1 -1
        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
    }
}