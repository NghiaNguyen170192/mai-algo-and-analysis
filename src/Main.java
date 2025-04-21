import puzzles.SolveablePuzzle;
import puzzles.UnsolvablePuzzle;

public class Main {
    public static void main(String[] args) {
        RMIT_15_Puzzle_Solver solver = new RMIT_15_Puzzle_Solver();
        int[][] solvable2d = Helper.to2DArray(SolveablePuzzle.solvable1D_1);
        int[][] unsolvable2d = Helper.to2DArray(UnsolvablePuzzle.unsolvable1D_1);

        try {
            solver.solve(solvable2d);

            //convert from 2d to 1d and vice versa
            solver.initPositionLookup(unsolvable2d);
            solver.getCurrentGrid1DLookup();

            //print - both for puzzle and lookup
            System.out.println("Print whole puzzle");
            System.out.println("Solvable Puzzle");
            Helper.print(solvable2d);

            System.out.println("Unsolvable Puzzle");
            Helper.print(unsolvable2d);

            //lookup position in 1d
            //array index is number in puzzle
            //array value is the location of the number in puzzle
            System.out.println("Lookup position in 1d");
            int[] lookup = solver.getCurrentGrid1DLookup();
            Helper.printWithIndex(lookup);

            //retrieve lookup index for O(1) instead of traversing every time - both for puzzle and lookup access
            System.out.println("Get index of empty cell 0");
            System.out.println(solver.getLookupIndex(0));

            //get number with index access on the puzzle(not the lookup)
            // input is the location on the puzzle(not the lookup), return the value
            System.out.println("Get value at index");
            System.out.println(Helper.getValue(SolveablePuzzle.solvable1D_1, 15));
            System.out.println(Helper.getValue(unsolvable2d, 3, 3));


            System.out.println("Before move");
            Helper.print(solver.getCurrentGrid2D());
            System.out.println(solver.move(3, 3, 3, 2));
            System.out.println("After move");
            Helper.print(solver.getCurrentGrid2D());


            Helper.isComplete(solvable2d, solver.getCurrentGrid2D());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
