public class PerformanceTests {
    public static void main(String[] args) {
        int[][][] solvablePuzzles = SolvablePuzzle.testCases;
        IDAStar idaStar = new IDAStar();
        AStar aStar = new AStar();

        for (int i = 0; i < solvablePuzzles.length; i++) {
            System.out.println(String.format("%-10s %-15s %-10s %-50s", "Puzzle: " + (i + 1), "Time(ns)", "isGoal", "Moves"));
            long start = System.nanoTime();
            String moves1 = idaStar.solve(solvablePuzzles[i]);
            long end = System.nanoTime();
            long time1 = end - start;
            int[][] solutionBoard1 = Helper.testSolution(solvablePuzzles[i], moves1);
            boolean isGoal1 = Helper.isGoalV2(solutionBoard1);

            System.out.println(String.format("%-10s %-15s %-10s %-50s", "IDA*", time1, isGoal1, moves1));

            start = System.nanoTime();
            String moves2 = aStar.solve(solvablePuzzles[i]);
            end = System.nanoTime();
            long time2 = end - start;
            int[][] solutionBoard2 = Helper.testSolution(solvablePuzzles[i], moves1);
            boolean isGoal2 = Helper.isGoalV2(solutionBoard2);

            System.out.println(String.format("%-10s %-15s %-10s %-50s", "A*", time2, isGoal2, moves2));
        }
    }
}