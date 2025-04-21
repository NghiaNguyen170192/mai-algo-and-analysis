public class RMIT_15_Puzzle_Solver {
    private int moveCount = 0;
    private final int maximumMove = 1000000;
    private final StringBuilder movesBuilder = new StringBuilder();
    private int gridSize;
    private int rowCount;
    private int columnCount;

    private int[][] givenPuzzle2D;
    private int[][] currentGrid2D;
    private int[] currentGrid1DLookup;
    private boolean isSolvable = false;
    //    private int currentEmptyIndex = 0;
    private int currentEmptyRow = 0;
    private int currentEmptyColumn = 0;

    public String solve(int[][] puzzle) throws Exception {
        if (!isValid(puzzle)) {
            throw new Exception("Invalid puzzle");
        }

        initGrid(puzzle);

        long startTime = System.currentTimeMillis();
        //Implement algorithm here
        //call move(int fromRow, int fromColumn, int toRow, int toColumn) to make tile move
        //use movesBuilder.append to append move for final result
        //

        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println("Time taken: " + estimatedTime + "ms");

        return movesBuilder.toString();
    }

    //save for local usage
    public void initGrid(int[][] puzzle) {
        rowCount = puzzle.length;
        columnCount = puzzle[0].length;
        gridSize = puzzle.length * puzzle[0].length;
        int[] puzzle1D = Helper.to1DGrid(puzzle);

        givenPuzzle2D = puzzle;
        currentGrid2D = initDefault2D();
        currentGrid1DLookup = initPositionLookup(currentGrid2D);

        //default location of
        currentEmptyRow = rowCount - 1;
        currentEmptyColumn = columnCount - 1;
    }

    // O(N*N)
    public int[][] initDefault2D() {
        int[][] default2D = new int[rowCount][columnCount];
        int number = 1;

        // Fill the grid sequentially
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                default2D[i][j] = (number == gridSize) ? 0 : number++; // Last cell is empty (0)
            }
        }
        return default2D;
    }

    // O(N*N) - WIP
    public boolean isValid(int[][] puzzle) {
        if (puzzle == null || puzzle.length == 0 || puzzle[0].length == 0) {
            return false;
        }

        // Check if the puzzle is square
        for (int i = 0; i < rowCount; i++) {
            if (puzzle[i].length != rowCount) {
                return false;
            }
        }

        int maxNumber = gridSize - 1;

        // Array to track occurrences of numbers
        int[] occurrences = new int[gridSize];

        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                int number = puzzle[rowIndex][columnIndex];
                if (number < 0 || number > maxNumber) {
                    return false; // Number out of range
                }
                occurrences[number]++;
                if (occurrences[number] > 1) {
                    return false; // Duplicate number found
                }
            }
        }

        return true;
    }

    //WIP
//    public int findEmptyRow() {
//        int emptyIndex = getLookupIndex(0);
//        return (emptyIndex / gridSize) + 1; // Convert to row index from bottom
//    }

    //O(N*N)
    public int[] initPositionLookup(int[][] grid) {
        int row = grid.length;
        int column = grid[0].length;
        int[] positionLookup = new int[row * column];

        for (int rowIndex = 0; rowIndex < row; rowIndex++) {
            for (int columnIndex = 0; columnIndex < column; columnIndex++) {
                //positionLookup[0] presents empty cell
                //positionLookup value presents index location
                if (grid[rowIndex][columnIndex] == 0) {

                    positionLookup[0] = Helper.to1DIndex(rowIndex, columnIndex, columnCount);
                } else {
                    positionLookup[grid[rowIndex][columnIndex]] = Helper.to1DIndex(rowIndex, columnIndex, columnCount);
                }
            }
        }

        return positionLookup;
    }

    // Get the index where a specific tile number is located
    //this is a lookup table to return quick location of a number
    //O(1)
    public int getLookupIndex(int number) {
        return currentGrid1DLookup[number]; // Direct lookup for tile position
    }

    //move actual tile to empty space, not the other way around to move empty space to tile
    public String move(int fromRow, int fromColumn, int toRow, int toColumn) throws Exception {
        if (fromRow < 0 || fromRow >= rowCount || fromColumn < 0 || fromColumn >= columnCount) {
            throw new Exception("Invalid from Row or from Column move");
        }

        if (toRow < 0 || toRow >= rowCount || toColumn < 0 || toColumn >= columnCount) {
            throw new Exception("Invalid toRow or to Clumn move");
        }

        boolean isValidMove = false;
        // Check if the move is exactly one step horizontally
        boolean isMoveLeft = toRow == currentEmptyRow && toColumn == currentEmptyColumn - 1;
        boolean isMoveRight = toRow == currentEmptyRow && toColumn == currentEmptyColumn + 1;

        // Check if the move is exactly one step vertically
        boolean isMoveUp = toColumn == currentEmptyColumn && toRow == currentEmptyRow - 1;
        boolean isMoveDown = toColumn == currentEmptyColumn && toRow == currentEmptyRow + 1;

        // Update isValidMove if it is a valid horizontal or vertical move
        if (isMoveLeft || isMoveRight || isMoveUp || isMoveDown) {
            isValidMove = true;
        }

        if (!isValidMove) {
            throw new Exception("Invalid move");
        }

        int numberToMove = currentGrid2D[fromRow][fromColumn];
        currentGrid2D[fromRow][fromColumn] = currentGrid2D[toRow][toColumn];
        currentGrid2D[toRow][toColumn] = numberToMove;

        //update empty cell new position index
        currentGrid1DLookup[0] = Helper.to1DIndex(fromRow, fromColumn, columnCount);
        //update number to new position index
        currentGrid1DLookup[numberToMove] = Helper.to1DIndex(toRow, toColumn, columnCount);
        moveCount++;

        if (isMoveUp) {
            return "U";
        }

        if (isMoveDown) {
            return "D";
        }

        if (isMoveLeft) {
            return "L";
        }

        if (isMoveRight) {
            return "R";
        }

        System.out.println("Invalid move");
        return "";
    }

    public int[] getMovablePositions() {
        int[] movablePositions = new int[4]; // Array to store possible movable positions
        int count = 0; // Counter for valid movable positions

        // Check position above the blank space
        if (currentEmptyRow > 0) { // Ensure it's not at the top edge
            movablePositions[count++] = Helper.to1DIndex(currentEmptyRow - 1, currentEmptyColumn, columnCount);
        }

        // Check position below the blank space
        if (currentEmptyRow < rowCount - 1) { // Ensure it's not at the bottom edge
            movablePositions[count++] = Helper.to1DIndex(currentEmptyRow + 1, currentEmptyColumn, columnCount);
        }

        // Check position to the left of the blank space
        if (currentEmptyColumn > 0) { // Ensure it's not at the left edge
            movablePositions[count++] = Helper.to1DIndex(currentEmptyRow, currentEmptyColumn - 1, columnCount);
        }

        // Check position to the right of the blank space
        if (currentEmptyColumn < columnCount - 1) { // Ensure it's not at the right edge
            movablePositions[count++] = Helper.to1DIndex(currentEmptyRow, currentEmptyColumn + 1, columnCount);
        }

        int[] validMovablePositions = new int[count];
        for (int i = 0; i < count; i++) {
            validMovablePositions[i] = movablePositions[i];
        }

        return validMovablePositions;
    }

    // #region grid validation methods - WIP
    public boolean isSolvable(int[][] grid) {
        return false;
    }

    // Getters - Setters
    public int[][] getCurrentGrid2D() {
        return currentGrid2D;
    }

    public int[] getCurrentGrid1DLookup() {
        return currentGrid1DLookup;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public String getMoves() {
        return movesBuilder.toString();
    }

    public boolean getIsSolvable() {
        return isSolvable;
    }
}