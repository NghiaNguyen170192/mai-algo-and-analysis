import java.util.HashSet;

import javax.naming.directory.InvalidAttributesException;

public class RMIT_15_Puzzle_Solver {
    private final int maximumMove = 1000000;
    private final StringBuilder movesBuilder = new StringBuilder();
    private int gridSize;
    private int rows;
    private int columns;
    private int[] puzzleLookup1D;
    private int[][] puzzle2D;

    public String solve(int[][] puzzle) throws InvalidAttributesException {
        if (!isValid(puzzle)) {
            throw new InvalidAttributesException("Invalid puzzle");
        }

        // initDefaultConfiguration();
        initGrid(puzzle);

        return "";
    }

    public void initGrid(int[][] puzzle) {
        rows = puzzle.length;
        columns = puzzle[0].length;
        gridSize = puzzle.length * puzzle[0].length;
        int[] puzzle1D = to1DGrid(puzzle);
        puzzleLookup1D = initPuzzleLookup(puzzle1D);
        puzzle2D = puzzle;
    }

    // public void initDefaultConfiguration() {
    // int[][] default2D = initDefault2D();
    // int[] default1D = initDefault1D();
    // }

    // #region convert methods
    // O(N*N)
    public int[] to1DGrid(int[][] grid) {
        int[] array = new int[gridSize];

        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                array[index] = grid[i][j];
                index++;
            }
        }

        return array;
    }

    // O(N*N)
    public int[][] to2DArray(int[] grid1D, int size) {
        int[][] grid2D = new int[size][size];
        int index = 0;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid2D[i][j] = grid1D[index];
                index++;
            }
        }

        return grid2D;
    }

    // #endregion

    // O(N*N)
    public int[][] initDefault2D() {
        int[][] default2D = new int[rows][columns];
        int number = 1;

        // Fill the grid sequentially
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                default2D[i][j] = (number == gridSize) ? 0 : number++; // Last cell is empty space (0)
            }
        }
        return default2D;
    }

    // O(N)
    public int[] initDefault1D() {
        int[] default1D = new int[gridSize];

        for (int i = 0; i < gridSize * gridSize - 1; i++) {
            default1D[i] = i + 1;
        }
        default1D[gridSize - 1] = 0; // Last element is empty space (0)

        return default1D;
    }

    // #region grid validation methods
    public boolean isSolvable(int[][] grid) {

        return false;
    }

    // O(N*N)
    public boolean isValid(int[][] puzzle) {
        if (puzzle == null || puzzle.length == 0 || puzzle[0].length == 0) {
            return false;
        }

        int row = puzzle.length;

        // Check if the puzzle is square
        for (int i = 0; i < row; i++) {
            if (puzzle[i].length != row)
                return false;
        }

        int maxNumber = row * row - 1;
        HashSet<Integer> seenNumbers = new HashSet<>();

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < row; j++) {
                int number = puzzle[i][j];
                if (number < 0 || number > maxNumber || !seenNumbers.add(number)) {
                    return false;
                }
            }
        }

        return true;
    }

    // #endregion

    // #region grid access
    public int findEmptyRow(int[] positionLookup, int size) {
        int emptyIndex = positionLookup[0]; // Direct lookup for empty tile (0)
        return (emptyIndex / size) + 1; // Convert to row index from bottom
    }

    // Initialize position lookup table
    public int[] initPositionLookup(int[] puzzle) {
        int[] positionLookup = new int[16]; // Stores tile positions

        for (int i = 0; i < puzzle.length; i++) {
            if (puzzle[i] == 0) {
                positionLookup[0] = i; // Always store blank tile's location at index `0`
            } else {
                positionLookup[puzzle[i]] = i; // Store numbered tile locations
            }
        }

        return positionLookup;
    }

    // Get the tile number at a specific index in the puzzle
    public int getValue(int index, int[] positionLookup) {
        for (int num = 0; num < positionLookup.length; num++) {
            if (positionLookup[num] == index) {
                return num; // Found tile number that maps to this index
            }
        }
        return -1; // Should never happen if lookup is valid
    }

    // Get the index where a specific tile number is located
    //this is a lookup table to return quick location of a number

    public int getIndex(int value, int[] positionLookup) {
        if (value < 0 || value >= positionLookup.length) {
            return -1; // Invalid tile number
        }
        return positionLookup[value]; // Direct lookup for tile position
    }

    // #endregion
    public static void main(String[] args) {
        RMIT_15_Puzzle_Solver solver = new RMIT_15_Puzzle_Solver();
        // int [][] puzzle = solver.to
    }
}