import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UnitTests {

    private final int[][] GOAL = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 0}
    };

    private final int[][][] solvableTestCases = SolvablePuzzle.testCases;
    private final int[][][] unsolvableTestCases = UnsolvablePuzzle.unsolvableTestCases;
    private final int[][][] invalidTestCases = UnsolvablePuzzle.invalidTestCases;

    @Test
    public void isGoal_should_return_false_for_solvable_puzzles() {
        for (var testCase : solvableTestCases) {
            assertFalse(Helper.isGoal(testCase), "isGoal should be recognized as solved.");
        }
    }

    @Test
    public void isGoal_should_return_false_for_unsolvable_puzzles() {
        for (var testCase : unsolvableTestCases) {
            assertFalse(Helper.isGoal(testCase), "isGoal should return false..");
        }
    }

    @Test
    public void isGoalV2_should_return_false_for_solvable_puzzles() {
        for (var testCase : solvableTestCases) {
            assertFalse(Helper.isGoalV2(testCase), "isGoalV2 should be recognized as solved.");
        }
    }

    @Test
    public void isGoalV2_should_return_false_for_unsolvable_puzzles() {
        for (var testCase : unsolvableTestCases) {
            assertFalse(Helper.isGoalV2(testCase), "isGoalV2 should return false.");
        }
    }

    @Test
    public void isGoal_should_return_true() {
        int[][] case1 = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 0}};
        int[][] case2 = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0},};
        int[][] case3 = {{1, 2}, {3, 0},};
        assertTrue(Helper.isGoal(case1));
        assertTrue(Helper.isGoal(case2));
        assertTrue(Helper.isGoal(case3));
    }

    @Test
    public void isGoalV2_should_return_true() {
        int[][] case1 = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 0}};
        int[][] case2 = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0},};
        int[][] case3 = {{1, 2}, {3, 0},};

        assertTrue(Helper.isGoalV2(case1));
        assertTrue(Helper.isGoalV2(case2));
        assertTrue(Helper.isGoalV2(case3));
    }

    @Test
    public void isValid_should_return_true_for_valid_puzzles() {
        for (var testCase : solvableTestCases) {
            assertTrue(Helper.isValid(testCase), "isValid should return true for valid puzzles.");
        }
    }

    @Test
    public void isValid_should_return_true_for_unsolvable_puzzles() {
        for (var testCase : unsolvableTestCases) {
            assertTrue(Helper.isValid(testCase), "isValid should return false for unsolvable puzzles.");
        }
    }

    @Test
    public void isValid_should_return_false_for_invalid_puzzles() {
        for (var testCase : invalidTestCases) {
            assertFalse(Helper.isValid(testCase), "isValid should return false for invalid puzzles.");
        }
    }

    @Test
    public void getBlankPosition_should_return_correct_position_for_valid_puzzles() {
        int[][] grid1 = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 0} // Blank at (3,3)
        };

        int[][] grid2 = {
                {1, 0, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 2} // Blank at (0,1)
        };

        assertArrayEquals(new int[]{3, 3}, Helper.getBlankPosition(grid1), "Blank tile should be at (3,3).");
        assertArrayEquals(new int[]{0, 1}, Helper.getBlankPosition(grid2), "Blank tile should be at (0,1).");
    }

    @Test
    public void getBlankPosition_should_return_negative_one_for_invalid_puzzles() {
        int[][] gridNoBlank = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16} // No blank tile
        };

        assertArrayEquals(new int[]{-1, -1}, Helper.getBlankPosition(gridNoBlank), "Should return (-1,-1) if no blank tile.");
    }

    @Test
    public void getBlankPosition_should_return_first_blank_tile_position() {

        int[][] gridMultipleBlanks = {
                {1, 2, 0, 4},
                {5, 6, 7, 0},
                {9, 10, 11, 12},
                {13, 14, 15, 3} // Multiple blank tiles
        };

        assertArrayEquals(new int[]{0, 2}, Helper.getBlankPosition(gridMultipleBlanks), "Should return first blank tile position (0,2).");
    }

    @Test
    public void isSolvable_should_return_true_for_valid_puzzles() {
        for (var testCase : solvableTestCases) {
            assertTrue(Helper.isSolvable(testCase), "isSolvable should return true for valid puzzles.");
        }
    }

    @Test
    public void isSolvable_should_return_false_for_invalid_puzzles() {
        for (var testCase : unsolvableTestCases) {
            assertFalse(Helper.isSolvable(testCase), "isSolvable should return false for invalid puzzles.");
        }
    }

    @Test
    public void swap_should_swap_values() {
        int[][] actual = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 0, 15}
        };
        int[][] expected = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 0}
        };

        Helper.swap(actual, 3, 2, 3, 3);
        assertArrayEquals(expected, actual, "Helper.swap should swap values.");
    }

    @Test
    public void testCalculateManhattan() {
        int N = 4; // Grid size for 15-puzzle

        // Tile is already at the correct position
        assertEquals(0, Helper.calculateManhattan(0, 0, 1, N));
        assertEquals(0, Helper.calculateManhattan(1, 1, 6, N));
        assertEquals(0, Helper.calculateManhattan(2, 2, 11, N));

        // Tile is 1 row away
        assertEquals(1, Helper.calculateManhattan(1, 0, 1, N));

        // Tile is multiple rows/columns away
        assertEquals(2, Helper.calculateManhattan(2, 0, 1, N)); // Two rows away
        assertEquals(6, Helper.calculateManhattan(3, 3, 1, N)); // Three rows & one column away
    }

    @Test
    public void testManhattan() {
        int[][] grid1 = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 0} // Solved state, Manhattan distance should be 0
        };
        assertEquals(0, Helper.manhattan(grid1));

        int[][] grid2 = {
                {1, 2, 3, 4},
                {5, 6, 7, 0}, // Blank misplaced
                {9, 10, 11, 8}, // 8 is misplaced
                {13, 14, 15, 12} // 12 is misplaced
        };
        assertEquals(2, Helper.manhattan(grid2)); // Sum of misplaced tile distances

        int[][] grid3 = {
                {0, 2, 3, 4}, // 1 is misplaced (should be at [0,0])
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 1} // 1 moved to [3,3]
        };
        assertEquals(6, Helper.manhattan(grid3)); // Moving 1 back to [0,0] costs 6 moves
    }

    @Test
    public void deepCopy_should_return_a_copy_of_the_original_array() {
        int[][] original = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 0}
        };
        int[][] copy = Helper.deepCopy(original);

        assertArrayEquals(original, copy);
    }

    @Test
    public void testSolution_should_return_goal_board_for_IDAStar() {
        IDAStar idaStar = new IDAStar();

        for (var testCase : solvableTestCases) {
            String solution = idaStar.solve(testCase);
            int[][] testBoard = Helper.testSolution(testCase, solution);

            assertArrayEquals(GOAL, testBoard, "Solution should be the goal board.");
        }
    }

    @Test
    public void testSolution_should_return_goal_board_for_AStar() {
        AStar idaStar = new AStar();

        for (var testCase : solvableTestCases) {
            String solution = idaStar.solve(testCase);
            int[][] testBoard = Helper.testSolution(testCase, solution);

            assertArrayEquals(GOAL, testBoard, "Solution should be the goal board.");
        }
    }
}