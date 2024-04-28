import java.util.ArrayList;
// import edu.princeton.cs.algs4.StdRandom;

public class Board {
    private int[][] tiles;
    private final int size;
    private int r0, c0;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        size = tiles.length;
        this.tiles = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.tiles[i][j] = tiles[i][j];
                if (tiles[i][j] == 0) {
                    r0 = i;
                    c0 = j;
                }
            }
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder str = new StringBuilder();

        str.append(size + "\n");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                str.append(tiles[i][j] + " ");
            }
            str.append("\n");
        }

        return str.toString();
    }

    // board dimension n
    public int dimension() {
        return size;
    }

    // change (row, col) into a linear coordinate
    private int to1D(int row, int col) {
        return row * size + (col + 1);
    }

    // return true if not saving 0 or right value
    private boolean notInPos(int row, int col) {
        return tiles[row][col] != to1D(row, col) && tiles[row][col] != 0;
    }

    // number of tiles out of place
    public int hamming() {
        int count = 0;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (notInPos(i, j)) count++;
            }
        }

        return count;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int sum = 0;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (notInPos(i, j)) {
                    int val = tiles[i][j] - 1;
                    int x = val / size;
                    int y = val % size;
                    sum += Math.abs(x - i) + Math.abs(y - j);
                }
            }
        }

        return sum;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (notInPos(i, j)) return false;
            }
        }
        return true;
    }

    // does this board equal y?
    @Override
    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null || getClass() != y.getClass()) return false;
        Board that = (Board) y;
        if (this.dimension() != that.dimension()) return false;
        else {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (tiles[i][j] != that.tiles[i][j]) return false;
                }
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> neighborTiles = new ArrayList<>();
        int[] dxdy = {-1, 0, 1, 0, -1};

        for (int i = 0; i < 4; i++) {
            int newRow = r0 + dxdy[i];
            int newCol = c0 + dxdy[i + 1];
            if (neighborConditions(i)) {
                int[][] nei = copyTiles(tiles);
                nei[r0][c0] = tiles[newRow][newCol];
                nei[newRow][newCol] = tiles[r0][c0];
                neighborTiles.add(new Board(nei));
            }
        }

        return neighborTiles;
    }

    private boolean neighborConditions(int condition) {
        switch (condition) {
            // -1, 0
            case 0:
                return r0 != 0;
            //  0, 1
            case 1:
                return c0 != size - 1;
            //  1, 0
            case 2:
                return r0 != size - 1;
            //  0,-1
            case 3:
                return c0 != 0;
            default:
                return false;
        }
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] twinTiles = copyTiles(tiles);

        // int r1, c1, r2, c2;
        // do {
        //     r1 = StdRandom.uniformInt(size);
        //     c1 = StdRandom.uniformInt(size);
        // } while (tiles[r1][c1] == 0);
        // do {
        //     r2 = StdRandom.uniformInt(size);
        //     c2 = StdRandom.uniformInt(size);
        // } while (tiles[r1][c1] == 0 || (r1 == r2 && c1 == c2));

        // int temp = tiles[r1][c1];
        // tiles[r1][c2] = tiles[r2][c2];
        // tiles[r2][c2] = temp;

        if (tiles[0][0] != 0 && tiles[0][1] != 0) {
            twinTiles[0][0] = tiles[0][1];
            twinTiles[0][1] = tiles[0][0];
        }
        else {
            twinTiles[1][0] = tiles[1][1];
            twinTiles[1][1] = tiles[1][0];
        }

        return new Board(twinTiles);
    }

        // return a same but new tiles
    private int[][] copyTiles(int[][] arr) {
        int[][] copyTiles = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                copyTiles[i][j] = arr[i][j];
            }
        }

        return copyTiles;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
    }

}