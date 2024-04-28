import java.util.Deque;
import java.util.LinkedList;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private SearchNode ansNode;

    private static class SearchNode implements Comparable<SearchNode> {
        private final Board board;
        private final int moves;
        private final int priority;
        private final SearchNode prev;

        public SearchNode(Board board, SearchNode prev) {
            this.board = board;
            this.prev = prev;
            if (prev != null) this.moves = prev.moves + 1;
            else this.moves = 0;
            this.priority = this.board.manhattan() + this.moves;
        }

        @Override
        public int compareTo(SearchNode that) {
            return Integer.compare(this.priority, that.priority);
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException("Board is null.");

        MinPQ<SearchNode> pq = new MinPQ<>();
        MinPQ<SearchNode> twinpq = new MinPQ<>();
        pq.insert(new SearchNode(initial, null));
        twinpq.insert(new SearchNode(initial.twin(), null));

        while (true) {
            SearchNode oriNode = pq.delMin();
            SearchNode twinNode = twinpq.delMin();

            if (oriNode.board.isGoal()) {
                ansNode = oriNode;
                return;
            }
            else if (twinNode.board.isGoal()) {
                ansNode = null;
                return;
            }

            for (Board neighbor : oriNode.board.neighbors()) {
                if (oriNode.prev != null && oriNode.prev.board.equals(neighbor)) continue;
                pq.insert(new SearchNode(neighbor, oriNode));
            }
            for (Board neighbor : twinNode.board.neighbors()) {
                if (twinNode.prev != null && twinNode.prev.board.equals(neighbor)) continue;
                twinpq.insert(new SearchNode(neighbor, twinNode));
            }
        }
    }


    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return ansNode != null;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) return -1;
        return ansNode.moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
        Deque<Board> sol = new LinkedList<>();
        SearchNode solNode = ansNode;
        while (solNode != null) {
            sol.addFirst(solNode.board);
            solNode = solNode.prev;
        }
        return sol;
    }

    // test client (see below) 
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

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