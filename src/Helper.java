public class Helper {
    public static final String INVALID_PUZZLE_MESSAGE = "Invalid puzzle";
    public static final String UNSOLVABLE_MESSAGE = "Puzzle is not solvable";

    //O(N*N)
    // print 2d array
    public static void print(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            System.out.print("|");
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + "\t");
            }
            System.out.print("|");
            System.out.println();
        }
    }

    public static boolean isGoal(int[][] board) {
        int N = board.length;
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
    public static boolean isGoalV2(int[][] board) {
        int expected = 1;
        int N = board.length;
        for (int i = 0; i < N * N - 1; i++) {
            if (board[i / N][i % N] != expected++) {
                return false;
            }
        }

        return board[N - 1][N - 1] == 0; // Ensure the last tile is blank (0)
    }

    public static boolean isValid(int[][] puzzle) {
        int N = puzzle.length;
        int boardSize = N * N;
        boolean[] seen = new boolean[boardSize]; // Track occurrences of numbers

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int number = puzzle[i][j];

                // Invalid number check
                if (number < 0 || number >= boardSize) {
                    return false;
                }

                // Duplicate detected
                if (seen[number]) {
                    return false;
                }

                // Mark number as seen
                seen[number] = true;
            }
        }

        return true; // No duplicates found
    }

    public static boolean isSolvable(int[][] puzzle) {
        int N = puzzle.length;
        int[] flattenedPuzzle = new int[N * N];
        int blankRow = 0;
        int parity = 0;
        int row = 0;

        // Convert 2D puzzle to 1D array
        int index = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                flattenedPuzzle[index++] = puzzle[i][j];
                if (puzzle[i][j] == 0) {
                    blankRow = i + 1; // Track blank tile row (counting from top)
                }
            }
        }

        // Count inversions & track blank row (counting from bottom)
        for (int i = 0; i < flattenedPuzzle.length; i++) {
            if (i % N == 0) { // Move to next row
                row++;
            }
            if (flattenedPuzzle[i] == 0) { // Found blank tile
                blankRow = N - row; // Convert top-down index to bottom-up index
                continue;
            }
            for (int j = i + 1; j < flattenedPuzzle.length; j++) {
                if (flattenedPuzzle[j] != 0 && flattenedPuzzle[i] > flattenedPuzzle[j]) {
                    parity++; // Count inversions
                }
            }
        }

        // Solvability conditions
        boolean isEvenboard = (N % 2 == 0);
        boolean parityCheck = (parity % 2 == 0);

        if (isEvenboard) { // Even board case
            if (blankRow % 2 == 0) { // Blank is in odd row (from bottom)
                return parityCheck;
            } else { // Blank is in even row (from bottom)
                return !parityCheck;
            }
        } else { // Odd board case
            return parityCheck;
        }
    }

    public static int[][] deepCopy(int[][] board) {
        int N = board.length;
        int[][] copy = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                copy[i][j] = board[i][j];
            }
        }

        return copy;
    }

    public static int manhattan(int[][] board) {
        int N = board.length;
        int distance = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int tile = board[i][j];
                if (tile != 0) {
                    distance += calculateManhattan(i, j, tile, N);
                }
            }
        }

        return distance;
    }

    public static int calculateManhattan(int row, int column, int tile, int N) {
        int targetRow = (tile - 1) / N;
        int targetColumn = (tile - 1) % N;
        return Math.abs(row - targetRow) + Math.abs(column - targetColumn);
    }

    public static void swap(int[][] board, int sourceRow, int sourceColumn, int destinationRow, int destinationColumn) {
        int temp = board[sourceRow][sourceColumn];
        board[sourceRow][sourceColumn] = board[destinationRow][destinationColumn];
        board[destinationRow][destinationColumn] = temp;
    }

    public static int[] getBlankPosition(int[][] board) {
        int N = board.length;
        int blankRow = -1;
        int blankColumn = -1;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] == 0) {
                    blankRow = i;
                    blankColumn = j;
                    return new int[]{blankRow, blankColumn};
                }
            }
        }
        return new int[]{blankRow, blankColumn};
    }

    public static int[][] testSolution(int[][] puzzle, String solution) {
        char[] moves = solution.toCharArray();
        int[][] testBoard = Helper.deepCopy(puzzle);
        int[] blankPosition = Helper.getBlankPosition(testBoard);
        int blankRow = blankPosition[0];
        int blankColumn = blankPosition[1];

        for (int i = 0; i < moves.length; i++) {
            char move = moves[i];
            int newRow = blankRow;
            int newColumn = blankColumn;

            switch (move) {
                case 'U':
                    newRow++;
                    break;
                case 'D':
                    newRow--;
                    break;
                case 'L':
                    newColumn++;
                    break;
                case 'R':
                    newColumn--;
                    break;
                default:
                    throw new RuntimeException("Invalid move");
            }

            Helper.swap(testBoard, blankRow, blankColumn, newRow, newColumn);
            blankRow = newRow;
            blankColumn = newColumn;
        }

        return testBoard;
    }

    public static String hash(int[][] board) {
        StringBuilder sb = new StringBuilder();
        int N = board.length;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                sb.append(board[i][j]);
                sb.append(',');
            }
        }
        return sb.toString();
    }
}