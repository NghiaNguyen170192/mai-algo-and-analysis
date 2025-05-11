public class astar {
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


    private String aStarSearch() {
        path = new StringBuilder(); // Track solution path

        // Compute initial heuristic (Manhattan distance)
        int initialHeuristic = manhattan(board);
        threshold = initialHeuristic;

        // Min heap for priority sorting based on f(n)
        int[][] heap = new int[MAX_MOVES][N * N]; // Stores board states in tile form
        int[] fCostHeap = new int[MAX_MOVES]; // Stores f-costs (priority)
        int heapSize = 0;

        // Insert the initial board state
        copyBoard(board, heap[heapSize]);
        fCostHeap[heapSize++] = initialHeuristic;

        while (heapSize > 0) {
            // Extract the best state (lowest f-cost)
            int bestIndex = extractMinHeap(heap, fCostHeap, heapSize);
            int[][] currentBoard = reconstructBoard(heap[bestIndex]);
            heapSize--;

            // Find blank tile
            locateBlank(currentBoard);

            // Check if solved
            if (isGoal(currentBoard)) {
                solution = path.toString();
                return solution;
            }

            // Expand valid moves
            for (int direction = 0; direction < 4; direction++) {
                int newRow = currentBlankRow + dx[direction];
                int newColumn = currentBlankColumn + dy[direction];
                if (newRow < 0 || newRow >= N || newColumn < 0 || newColumn >= N) continue;

                // Generate new board state
                int[][] newBoard = copyBoard(currentBoard);
                swap(newBoard, currentBlankRow, currentBlankColumn, newRow, newColumn);

                // Compute f(n)
                int gCost = path.length() + 1;
                int hCost = manhattan(newBoard);
                int fCost = gCost + hCost;

                // Insert into heap
                copyBoard(newBoard, heap[heapSize]);
                fCostHeap[heapSize++] = fCost;
                path.append(moveChar[direction]);
            }
        }

        return "No solution found";
    }

    // Extract lowest f-cost state
    private int extractMinHeap(int[][] heap, int[] fCostHeap, int heapSize) {
        int minIndex = 0;
        for (int i = 1; i < heapSize; i++) {
            if (fCostHeap[i] < fCostHeap[minIndex]) {
                minIndex = i;
            }
        }
        return minIndex;
    }

    // Helper to locate the blank tile in the current board
    private void locateBlank(int[][] board) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] == 0) {
                    currentBlankRow = i;
                    currentBlankColumn = j;
                    return;
                }
            }
        }
    }

    // Helper to copy board state
    private void copyBoard(int[][] source, int[][] destination) {
        for (int i = 0; i < N; i++) {
            System.arraycopy(source[i], 0, destination[i], 0, N);
        }
    }

    // Helper to reconstruct board from heap data
    private int[][] reconstructBoard(int[] flatBoard) {
        int[][] board = new int[N][N];
        for (int i = 0; i < N * N; i++) {
            board[i / N][i % N] = flatBoard[i];
        }
        return board;
    }

    // Manhattan heuristic computation
    private int manhattan(int[][] grid) {
        int distance = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int value = grid[i][j];
                if (value != 0) {
                    distance += calculateManhattan(i, j, value);
                }
            }
        }
        return distance;
    }

    // Manhattan distance for a single tile
    private int calculateManhattan(int row, int column, int tile) {
        int targetRow = (tile - 1) / N;
        int targetColumn = (tile - 1) % N;
        return Math.abs(row - targetRow) + Math.abs(column - targetColumn);
    }

    // Helper: Swap tiles
    private void swap(int[][] board, int row1, int col1, int row2, int col2) {
        int temp = board[row1][col1];
        board[row1][col1] = board[row2][col2];
        board[row2][col2] = temp;
    }

    // Check if board is solved
    private boolean isGoal(int[][] board) {
        int expected = 1;
        for (int i = 0; i < N * N - 1; i++) {
            if (board[i / N][i % N] != expected++) {
                return false;
            }
        }
        return board[N - 1][N - 1] == 0;
    }
}
