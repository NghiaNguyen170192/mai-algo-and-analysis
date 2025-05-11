public class ida_star {
    private static final int N = 4;
    private static final int MAX_MOVES = 1_000_000;
    // Directions: blank moves -> tile moves
    // dx,dy correspond to blank motion; moveChar is the tile move
    private static final int[] dx = {1, -1, 0, 0};  // down, up, right, left of blank
    private static final int[] dy = {0, 0, 1, -1};
    private static final char[] moveChar = {'U', 'D', 'L', 'R'};

    private int[][] board = new int[N][N];
    private int threshold;
    private String solution;
    private StringBuilder path;
    private int currentBlankRow = 0;
    private int currentBlankColumn = 0;

    public String solve(int[][] puzzle) {
        // Copy input
        for (int i = 0; i < N; i++) {
            System.arraycopy(puzzle[i], 0, board[i], 0, N);
        }

        // Initial heuristic
        threshold = manhattan(board);
        path = new StringBuilder();
        solution = null;

        // locate blank (0)
        getBlankPosition(board);

        // Iterative deepening A*
        while (true) {
            int t = dfs(currentBlankRow, currentBlankColumn, 0, -1);
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

    /**
     * Depth-first search with cost pruning
     *
     * @param blankRow          blank row
     * @param blankColumn       blank col
     * @param cost              cost so far
     * @param previousDirection previous move direction index (to avoid backtracking)
     * @return next threshold or Integer.MAX_VALUE if no moves
     */
    private int dfs(int blankRow, int blankColumn, int cost, int previousDirection) {
        int f = cost + manhattan(board);
        if (f > threshold) {
            return f;
        }

        if (isGoal()) {
            solution = path.toString();
            return -1;
        }

        int min = Integer.MAX_VALUE;
        for (int direction = 0; direction < N; direction++) {
            // skip reverse move
            if (previousDirection != -1 && (direction ^ 1) == previousDirection) continue;
            int newRow = blankRow + dx[direction], newColumn = blankColumn + dy[direction];

            if (newRow < 0 || newRow >= N || newColumn < 0 || newColumn >= N) continue;

            // Swap blank and tile
            swap(blankRow, blankColumn, newRow, newColumn);
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
            swap(newRow, newColumn, blankRow, blankColumn);
        }

        return min;
    }

    private void swap(int sourceRow, int sourceColumn, int destinationRow, int destinationColumn) {
        board[sourceRow][sourceColumn] = board[destinationRow][destinationColumn];
        board[destinationRow][destinationColumn] = 0;
        currentBlankRow = destinationRow;
        currentBlankColumn = destinationColumn;
    }

    /**
     * Compute Manhattan distance heuristic
     */
    private int manhattan(int[][] grid) {
        int distance = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int tile = grid[i][j];
                if (tile != 0) {
                    distance += calculateManhattan(i, j, tile);
                }
            }
        }

        return distance;
    }

    private int calculateManhattan(int row, int column, int tile) {
        int targetRow = (tile - 1) / N;
        int targetColumn = (tile - 1) % N;
        return Math.abs(row - targetRow) + Math.abs(column - targetColumn);
    }

    /**
     * Check if board is in goal state
     */
    private boolean isGoal() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                // i * N + j is the position,
                // i * N + j  + 1 is the expected number in that position,
                int expected = i * N + j + 1;
                if (expected == N * N) {
                    expected = 0;
                }

                if (board[i][j] != expected) {
                    return false;
                }
            }
        }

        return true;
    }

    //avoid calculation i*N+j+1
    private boolean isGoalV2() {
        int expected = 1;

        for (int i = 0; i < N * N - 1; i++) {
            if (board[i / N][i % N] != expected++) {
                return false;
            }
        }

        return board[N - 1][N - 1] == 0; // Ensure the last tile is blank (0)
    }

    private void getBlankPosition(int[][] grid) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (grid[i][j] == 0) {
                    currentBlankRow = i;
                    currentBlankColumn = j;
                    return;
                }
            }
        }
    }

    //O(N*N)
    public void printBoard(int[][] grid) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(grid[i][j] + "\t");
            }
            System.out.println();
        }
    }

    public void printBoard() {
        printBoard(board);
    }

    // Optional main for quick testing
    public static void main(String[] args) {
        ida_star solver = new ida_star();
        // Sample puzzles (0 = blank)
        int[][] puzzle1 = {
                {14, 5, 7, 9},
                {2, 13, 11, 0},
                {6, 1, 12, 3},
                {15, 10, 8, 4}
        };

        int[][] puzzle2 = {
                {8, 10, 0, 5},
                {14, 9, 1, 4},
                {6, 12, 13, 15},
                {7, 2, 3, 11}
        };

        int[][] puzzle3 = {
                {15, 4, 1, 12},
                {6, 7, 14, 0},
                {8, 13, 2, 10},
                {3, 11, 5, 9}
        };

        int[][] puzzle4 = {
                {2, 9, 15, 0},
                {6, 11, 5, 14},
                {13, 4, 1, 3},
                {8, 10, 7, 12}
        };

        int[][] puzzle5 = {
                {4, 3, 13, 8},
                {2, 11, 0, 12},
                {10, 1, 7, 14},
                {15, 6, 9, 5}
        };

        int[][] puzzle6 = {
                {9, 7, 6, 0},
                {15, 4, 5, 12},
                {8, 3, 10, 2},
                {14, 1, 11, 13}
        };

        int[][] puzzle7 = {
                {13, 14, 0, 3},
                {6, 15, 7, 2},
                {1, 4, 5, 11},
                {10, 8, 12, 9}
        };

        int[][] puzzle8 = {
                {4, 2, 5, 10},
                {15, 3, 9, 8},
                {6, 11, 13, 14},
                {12, 0, 7, 1}
        };

        int[][] puzzle9 = {
                {4, 13, 9, 6},
                {8, 14, 10, 0},
                {11, 15, 7, 5},
                {12, 1, 2, 3}
        };

        int[][] puzzle10 = {
                {4, 3, 0, 8},
                {7, 2, 11, 10},
                {12, 15, 5, 13},
                {1, 9, 6, 14}
        };
        int[][][] tests = {
                puzzle1, puzzle2, puzzle3, puzzle4, puzzle5,
                puzzle6, puzzle7, puzzle8, puzzle9, puzzle10
        };

        for (int i = 0; i < tests.length; i++) {
            System.out.println("---- Test " + (i + 1) + " ----");
            System.out.println("Initial:");
            solver.printBoard(tests[i]);

            long t0 = System.currentTimeMillis();
            String moves = solver.solve(tests[i]);
            long t1 = System.currentTimeMillis();

            System.out.println("Solution (" + moves.length() + " moves, " + (t1 - t0) + " ms):");
            System.out.println(moves);

            System.out.println("Final:");
            solver.printBoard();
            System.out.println();
        }
    }
}
