import java.util.ArrayList;
import java.util.Arrays;

public final class Board {
    private final int[][] blocks;
    private final int N;
    private int X;
    private int Y;
    public Board(int[][] blocks) {
        // construct a board from an n-by-n array of blocks
        this.N = blocks.length;
        this.blocks = new int[N][];
        for (int i = 0; i < N; i++)
            this.blocks[i] = Arrays.copyOf(blocks[i], N);
        boolean flag = false;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++)
            {
                if (blocks[i][j] == 0) {
                    X = i; Y = j;
                    flag = true;
                    break;
                }
            }
            if (flag) break;
        }
    }
    // (where blocks[i][j] = block in row i, column j)
    public int dimension() {
        // board dimension n
        return N;
    }
    public int hamming() {
        // number of blocks out of place
        int count = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int correct = i*N + j + 1;
                if (correct < N*N && blocks[i][j] != correct)
                    count++;
            }
        }
        return count;
    }
    public int manhattan() {
        // sum of Manhattan distances between blocks and goal
        int m = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int current = blocks[i][j] - 1;
                if (current >= 0) {
                    int cI = current / N;
                    int cJ = current % N;
                    m += (Math.abs(cI - i) + Math.abs(cJ - j));
                }
            }
        }
        return m;
    }
    public boolean isGoal() {
        // is this board the goal board?
        return hamming() == 0;
    }
    private static int swap(int it, int ig) {
        return it;
    }
    private Board exchTwo(int[][] src, int srcX, int srcY, int offsetX, int offsetY) {
        int[][] dst = new int[N][];
        for (int i = 0; i < N; i++)
            dst[i] = Arrays.copyOf(src[i], N);
        // This is a swap trick...
        dst[srcX][srcY] = swap(dst[srcX+offsetX][srcY+offsetY],
                dst[srcX+offsetX][srcY+offsetY] = dst[srcX][srcY]);
        return new Board(dst);
    }
    public Board twin() {
        // a board that is obtained by exchanging any pair of blocks
        return exchTwo(blocks, X == 0 && (Y == 0 || Y == 1) ? 1 : 0, 0, 0, 1);
    }
    public boolean equals(Object y) {
        // does this board equal y?
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass())
            return false;
        Board c = (Board) y;
        if (c.N != this.N) return false;
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (c.blocks[i][j] != this.blocks[i][j])
                    return false;
        return true;
    }
    public Iterable<Board> neighbors() {
        // all neighboring boards
        ArrayList<Board> nBoards = new ArrayList<>();
        if (X - 1 >= 0) nBoards.add(exchTwo(blocks, X, Y, -1, 0));
        if (X + 1 < N) nBoards.add(exchTwo(blocks, X, Y, 1, 0));
        if (Y - 1 >= 0) nBoards.add(exchTwo(blocks, X, Y, 0, -1));
        if (Y + 1 < N) nBoards.add(exchTwo(blocks, X, Y, 0, 1));
        return nBoards;
    }
    public String toString() {
        // string representation of this board (in the output format specified below)
        StringBuilder sBuffer = new StringBuilder();
        sBuffer.append(N); sBuffer.append('\n');
        for (int[] line : blocks) {
            sBuffer.append(' ');
            for (int item : line) {
                sBuffer.append(item);
                sBuffer.append(' ');
            }
            sBuffer.append('\n');
        }
        return sBuffer.toString();
    }

    public static void main(String[] args) {
        // unit tests (not graded)
        int[][] blocks = {{1, 3, 2}, {5, 8, 6}, {4, 0, 7}};
        Board initBoard = new Board(blocks);
        System.out.println(initBoard);
        System.out.println(initBoard.hamming());
        System.out.println(initBoard.manhattan());
        System.out.println(initBoard.twin());
        System.out.println(initBoard);
        System.out.println(initBoard.equals(initBoard.twin()));
        for (Board board : initBoard.neighbors()) {
            System.out.println(board);
        }
    }
}
