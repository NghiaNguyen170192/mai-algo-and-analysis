public class AStar {
    private final int N = 4;
    private final int MAX_MOVES = 1_000_000;
    private final int[] dx = {1, -1, 0, 0};
    private final int[] dy = {0, 0, 1, -1};
    private final char[] moveChar = {'U', 'D', 'L', 'R'};

    private int[][] board = new int[N][N];
    private int blankRow = 0;
    private int blankColumn = 0;

    public String solve(int[][] puzzle) {
        board = Helper.deepCopy(puzzle);
        int[] blankPosition = Helper.getBlankPosition(board);
        blankRow = blankPosition[0];
        blankColumn = blankPosition[1];

        MinHeap open = new MinHeap(MAX_MOVES);
        HashSet closed = new HashSet(MAX_MOVES);

        Node start = new Node(board, blankRow, blankColumn, "", 0, -1);
        open.insert(start);

        try {
            while (!open.isEmpty()) {
                Node current = open.remove();

                if (Helper.isGoalV2(current.board)) {
                    board = Helper.testSolution(board, current.path);

                    //gracefully null big size object
                    open = null;
                    closed = null;
                    return current.path;
                }

                var hash = Helper.hash(current.board);
                if (closed.contains(hash)) continue;
                closed.add(hash);

                for (int direction = 0; direction < 4; direction++) {
                    if (current.previousDirection != -1 && (direction ^ 1) == current.previousDirection) continue;

                    int newRow = current.blankRow + dx[direction];
                    int newCol = current.blankColumn + dy[direction];

                    if (newRow < 0 || newRow >= N || newCol < 0 || newCol >= N)
                        continue;

                    int newCost = current.cost + 1;
                    if (newCost > MAX_MOVES) continue;

                    int[][] newGrid = Helper.deepCopy(current.board);
                    Helper.swap(newGrid, current.blankRow, current.blankColumn, newRow, newCol);
                    String newPath = current.path + moveChar[direction];

                    Node neighbor = new Node(newGrid, newRow, newCol, newPath, newCost, direction);
                    if (neighbor.priority > MAX_MOVES) continue;
                    open.insert(neighbor);

                    neighbor = null;
                }

                current = null;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
        } finally {
            open = null;
            closed = null;
        }

        return "No solution";
    }

    public int[][] getBoard() {
        return board;
    }
}
