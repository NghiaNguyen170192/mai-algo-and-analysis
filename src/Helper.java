public class Helper {
    // O(1)
    public static int getValue(int[] array, int index) {
        if (index < 0 || index >= array.length) {
            return -1;
        }

        return array[index];
    }

    // O(1)
    public static int getValue(int[][] grid, int rowIndex, int columnIndex) {
        if (rowIndex < 0 || rowIndex >= grid.length || columnIndex < 0 || columnIndex >= grid[0].length) {
            return -1;
        }
        return grid[rowIndex][columnIndex];
    }

    //O(1)
    public static int to1DIndex(int rowIndex, int columnIndex, int columnCount) {
        return rowIndex * columnCount + columnIndex;
    }

    //O(1)
    public static int[] to2DIndex(int index, int rowCount, int columnCount) {
        return new int[]{index / rowCount, index % columnCount};
    }

    //O(N*N)
    public static boolean isComplete(int[][] givenPuzzle2D, int[][] currentGrid2D) {
        for (int rowIndex = 0; rowIndex < givenPuzzle2D.length; rowIndex++) {
            for (int columnIndex = 0; columnIndex < givenPuzzle2D[0].length; columnIndex++) {
                if (currentGrid2D[rowIndex][columnIndex] != givenPuzzle2D[rowIndex][columnIndex]) {
                    return false;
                }
            }
        }

        return true;
    }

    // print 2d array
    public static void print(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            System.out.print("|");
            for (int j = 0; j < grid[i].length; j++) {
                System.out.print(grid[i][j] + "\t");
            }
            System.out.print("|");
            System.out.println();
        }
    }

    // print 1d array
    public static void print(int[] array) {
        System.out.print("|");
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.print("|");
        System.out.println();
    }

    public static void printWithIndex(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.println("[" + i + "]: " + array[i] + " ");
        }
    }

    public static int[] to1DGrid(int[][] grid) {
        if (grid == null || grid.length == 0) {
            return new int[0];
        }

        int rowCount = grid.length;
        int columnCount = grid[0].length;
        int[] array = new int[rowCount * columnCount];

        int index = 0;
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                array[index] = grid[i][j];
                index++;
            }
        }

        return array;
    }

    // O(N*N)
    public static int[][] to2DArray(int[] grid1D) {
        if (grid1D == null || grid1D.length == 0) {
            return new int[0][0];
        }

        int size = (int) Math.sqrt(grid1D.length);
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
}
