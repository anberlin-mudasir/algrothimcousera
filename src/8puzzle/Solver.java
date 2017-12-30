import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private final class SearchNode implements Comparable<SearchNode> {
        private final int depth;
        private final Board board;
        private final SearchNode preNode;
        public SearchNode(Board board) {
            this.board = board;
            this.depth = 0;
            this.preNode = null;
        }
        public SearchNode(Board board, SearchNode pNode) {
            this.board = board;
            this.depth = pNode.depth + 1;
            this.preNode = pNode;
        }
        public Integer priority() {
            return this.board.manhattan() + this.depth;
        }
        public int compareTo(SearchNode prioBoard) {
            return this.priority().compareTo(prioBoard.priority());
        }
    }
    private MinPQ<SearchNode> mPq;
    private MinPQ<SearchNode> mPq2;
    private SearchNode goalNode = null;
    private SearchNode invGoalNode = null;
    private Stack<Board> stackSolution = null;
    public Solver(Board initial) {
        // find a solution to the initial board (using the A* algorithm)
        mPq = new MinPQ<>();
        mPq2 = new MinPQ<>();
        mPq.insert(new SearchNode(initial));
        mPq2.insert(new SearchNode(initial.twin()));
        if (initial.isGoal()) {
            goalNode = new SearchNode(initial);
        } else {
            while (goalNode == null && invGoalNode == null) {
                goalNode = process(mPq);
                invGoalNode = process(mPq2);
            }
        }
        mPq = null;
        mPq2 = null;

        if (isSolvable()) {
            stackSolution = new Stack<>();
            for (SearchNode i = goalNode; i != null; i = i.preNode)
                stackSolution.push(i.board);
        }
    }
    private SearchNode process(MinPQ<SearchNode> minPQ) {
        SearchNode crtNode = minPQ.delMin();
        Board dupBoard = crtNode.preNode == null ? null : crtNode.preNode.board;
        for (Board nBoard : crtNode.board.neighbors()) {
            // if find a path, return the goal node
            if (nBoard.isGoal())
                return new SearchNode(nBoard, crtNode);
            if (!nBoard.equals(dupBoard))
                minPQ.insert(new SearchNode(nBoard, crtNode));
        }
        return null;
    }
    public boolean isSolvable() {
        // is the initial board solvable?
        return goalNode != null;
    }
    public int moves() {
        // min number of moves to solve initial board; -1 if unsolvable
        return isSolvable() ? goalNode.depth : -1;
    }
    public Iterable<Board> solution() {
        // sequence of boards in a shortest solution; null if unsolvable
        return stackSolution;
    }
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}