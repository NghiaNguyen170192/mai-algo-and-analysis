public class ida_star {
    private static final int N = 4;
    private static final int MAX_MOVES = 1_000_000;
    // Directions: blank moves -> tile moves
    // dx,dy correspond to blank motion; moveChar is the tile move
    private static final int[] dx = {1, -1, 0, 0};  // down, up, right, left of blank
    private static final int[] dy = {0, 0, 1, -1};
    private static final char[] moveChar = {'U', 'D', 'L', 'R'};

    private int[][] board = new int[N][N];
    private int[] board1d = new int[N * N];
    private int[] boardLookup = new int[N * N];

    private int threshold;
    private String solution;
    private StringBuilder path;

    public String solve(int[][] puzzle) {
        // Copy input
        for (int i = 0; i < N; i++) {
            System.arraycopy(puzzle[i], 0, board[i], 0, N);
        }

        // Initial heuristic
        threshold = manhattan(board);
        path = new StringBuilder();
        solution = null;
        boardLookup = initLookup(board);
        board1d = to1D(board);

        // Iterative deepening A*
        while (true) {
            int blankR = 0, blankC = 0;
            // locate blank (0)
            outer:
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (board[i][j] == 0) {
                        blankR = i;
                        blankC = j;
                        break outer;
                    }
                }
            }

            int t = dfs(blankR, blankC, 0, -1);
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
            board[blankRow][blankColumn] = board[newRow][newColumn];
            board[newRow][newColumn] = 0;
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
            board[newRow][newColumn] = board[blankRow][blankColumn];
            board[blankRow][blankColumn] = 0;
        }
        return min;
    }

    /**
     * Compute Manhattan distance heuristic
     */
    private int manhattan(int[][] grid) {
        int distance = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int val = grid[i][j];
                if (val != 0) {
                    int targetRow = (val - 1) / N;
                    int targetColumn = (val - 1) % N;
                    distance += Math.abs(i - targetRow) + Math.abs(j - targetColumn);
                }
            }
        }


        return distance;
    }

    /**
     * Check if board is in goal state
     */
    private boolean isGoal() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int expected = i * N + j + 1;
                if (expected == N * N) {
                    expected = 0;
                }
                if (board[i][j] != expected) return false;
            }
        }

        return true;
    }

    private int[] to1D(int[][] board) {
        if (board == null || board.length == 0) {
            return new int[0];
        }

        int[] array = new int[N * N];
        int index = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                array[index] = board[i][j];
                index++;
            }
        }

        return array;
    }

    //O(N*N)
    public int[] initLookup(int[][] grid) {
        int[] lookupArray = new int[N * N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                //lookupArray[0] presents empty cell
                //lookupArray value presents index location
                lookupArray[grid[i][j]] = to1DIndex(i, j);
            }
        }

        return lookupArray;
    }

    // Get the index where a specific tile number is located
    //this is a lookup table to return quick location of a number
    //O(1)
    private int getLookupIndex(int number) {
        return boardLookup[number]; // Direct lookup for tile position
    }

    private int to1DIndex(int rowIndex, int columnIndex) {
        return rowIndex * N + columnIndex;
    }

    //O(1)
    private int[] to2DIndex(int index) {
        return new int[]{index / N, index % N};
    }

    //O(N*N)
    public void printBoard() {
        printBoard(board);
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
