public class IDAStar {
    private final int N = 4;
    private final int MAX_MOVES = 1_000_000;
    // Directions: blank moves -> tile moves
    // dx,dy correspond to blank motion; moveChar is the tile move
    private final int[] dx = {1, -1, 0, 0};  // down, up, right, left of blank
    private final int[] dy = {0, 0, 1, -1};
    private final char[] moveChar = {'U', 'D', 'L', 'R'};

    private int[][] board = new int[N][N];
    private int threshold;
    private String solution;
    private StringBuilder path;
    private int blankRow = 0;
    private int blankColumn = 0;

    public String solve(int[][] puzzle) {
        if (!Helper.isValid(puzzle)) {
            return Helper.INVALID_PUZZLE_MESSAGE;
        }

        if (!Helper.isSolvable(puzzle)) {
            return Helper.UNSOLVABLE_MESSAGE;
        }

        // Copy input
        board = Helper.deepCopy(puzzle);

        // Initial heuristic
        threshold = Helper.manhattan(board);
        path = new StringBuilder();
        solution = null;

        // locate blank (0)
        int[] blankPosition = Helper.getBlankPosition(board);
        blankRow = blankPosition[0];
        blankColumn = blankPosition[1];

        // Iterative deepening A*
        while (true) {
            int t = dfs(blankRow, blankColumn, 0, -1);
            if (solution != null) {
                if (solution.length() > MAX_MOVES) {
                    throw new RuntimeException("Solution exceeds maximum allowed moves");
                }
                return solution;
            }

            if (t == Integer.MAX_VALUE) {
                throw new RuntimeException("Unsolvable puzzle");
            }

            threshold = t;
        }
    }

    private int dfs(int blankRow, int blankColumn, int cost, int previousDirection) {
        int f = cost + Helper.manhattan(board);
        if (f > threshold) {
            return f;
        }

        if (Helper.isGoalV2(board)) {
            solution = path.toString();
            return -1;
        }

        int min = Integer.MAX_VALUE;
        for (int direction = 0; direction < N; direction++) {
            // skip reverse move
            if (previousDirection != -1 && (direction ^ 1) == previousDirection) continue;
            int newRow = blankRow + dx[direction];
            int newColumn = blankColumn + dy[direction];

            if (newRow < 0 || newRow >= N || newColumn < 0 || newColumn >= N) continue;

            // Swap blank and tile
            Helper.swap(board, blankRow, blankColumn, newRow, newColumn);
            this.blankRow = newRow;
            this.blankColumn = newColumn;

            path.append(moveChar[direction]);

            int t = dfs(newRow, newColumn, cost + 1, direction);
            // found solution
            if (t == -1) {
                return -1;
            }
            if (t < min) {
                min = t;
            }

            // backtrack
            path.setLength(path.length() - 1);
            Helper.swap(board, newRow, newColumn, blankRow, blankColumn);
            this.blankRow = blankRow;
            this.blankColumn = blankColumn;
        }

        return min;
    }

    public int[][] getBoard() {
        return board;
    }
}