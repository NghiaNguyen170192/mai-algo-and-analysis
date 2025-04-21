package puzzles;

public final class UnsolvablePuzzle {
    // Swap 14 & 15 → inversion parity flipped
    public static int[] unsolvable1D_1 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 15, 14, 0};

    // Swap 11 & 12 → inversion parity flipped
    public static int[] unsolvable1D_2 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 11, 13, 14, 15, 0};

    // Swap 7 & 8 → inversion parity flipped
    public static int[] unsolvable1D_3 = {1, 2, 3, 4, 5, 6, 8, 7, 9, 10, 11, 12, 13, 14, 15, 0};

    // Blank space in the wrong row for solvability
    public static int[] unsolvable1D_4 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 0, 15};






    // dup puzzle
    public static int[] duplicate1D_1 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 14, 0};
}
