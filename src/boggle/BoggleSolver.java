import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Bag;
// import edu.princeton.cs.algs4.TST;
import java.util.HashSet;

public class BoggleSolver
{
    private static final int[] scoreArray = new int[]{0, 0, 0, 1, 1, 2, 3, 5, 11};
    private static final int R = 26;
    // private final TST<Integer> dict;
    private static class Node {
        private boolean hasWord;
        private Node[] next = new Node[R];
    }
    private Node root;
    private int n;
    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        for (String s: dictionary) {
            if (s.length() >= 3)
                add(s);
        }
    }

    private void add(String key) {
        if (key == null) throw new IllegalArgumentException("argument to add() is null");
        root = add(root, key, 0);
    }

    private Node add(Node x, String key, int d) {
        if (x == null) x = new Node();
        if (d == key.length()) {
            if (!x.hasWord) n++;
            x.hasWord = true;
        }
        else {
            char c = (char)(key.charAt(d) - 'A');
            x.next[c] = add(x.next[c], key, d+1);
        }
        return x;
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        if (root == null)
            return null;
        HashSet<String> slist = new HashSet<>();
        int r = board.rows();
        int c = board.cols();
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                slist.addAll(getValidWithRoot(i, j, board));
            }
        }
        return slist;
    }

    private void getValidDFS(int i, int j, BoggleBoard board, boolean[][] marked,
        StringBuilder sb, HashSet<String> slist, Node current) {
        marked[i][j] = true;
        char ch = board.getLetter(i, j);
        sb.append(ch);
        Node next = current.next[(char)(ch - 'A')];
        if (ch == 'Q') {
            sb.append('U');
            if (next != null)
                next = next.next['U' - 'A'];
        }

        if (next != null) {
            String word = sb.toString();
            if (next.hasWord)
                slist.add(word);
            int c = board.cols();
            for (int h: adj(i, j, board)) {
                int ii = h / c;
                int jj = h % c;
                if (!marked[ii][jj])
                    getValidDFS(ii, jj, board, marked, sb, slist, next);
            }
        }
        marked[i][j] = false;
        sb.deleteCharAt(sb.length() - 1);
        if (ch == 'Q')
            sb.deleteCharAt(sb.length() - 1);
    }

    private HashSet<String> getValidWithRoot(int i, int j, BoggleBoard board) {
        StringBuilder sb = new StringBuilder();
        int r = board.rows();
        int c = board.cols();
        boolean[][] marked = new boolean[r][c];
        HashSet<String> slist = new HashSet<>();
        getValidDFS(i, j, board, marked, sb, slist, root);
        return slist;
    }

    private Bag<Integer> adj(int i, int j, BoggleBoard board) {
        Bag<Integer> adjPoints = new Bag<>();
        int r = board.rows();
        int c = board.cols();
        int[] is = new int[]{i-1, i, i+1};
        int[] js = new int[]{j-1, j, j+1};
        for (int ii: is) {
            for (int jj: js) {
                if ((ii == i && jj == j) || (ii < 0) || (jj < 0) || (ii >= r) || (jj >= c))
                    continue;
                adjPoints.add(ii*c+jj);
            }
        }
        return adjPoints;
    }

    private Node get(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) return x;
        char c = (char)(key.charAt(d) - 'A');
        return get(x.next[c], key, d+1);
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        Node node = get(root, word, 0);
        if (node != null && node.hasWord)
            return scoreArray[Math.min(8, word.length())];
        return 0;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        // long startTime = System.nanoTime();
        // for (int i = 0; i < 1000; i++) {
        //     BoggleBoard board = new BoggleBoard();
        //     solver.getAllValidWords(board);
        // }
        // long endTime = System.nanoTime();
        // StdOut.println((endTime - startTime) / 1000000);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
        // StdOut.println(board.getLetter(2,1));
    }

}