public class Node {
    int[][] board;
    int blankRow, blankColumn;
    String path;
    int cost;
    int priority;
    int previousDirection;
    long hash;

    Node(int[][] board, int blankRow, int blankColumn, String path, int cost, int previousDirection) {
        this.board = Helper.deepCopy(board);
        this.blankRow = blankRow;
        this.blankColumn = blankColumn;
        this.path = path;
        this.cost = cost;
        priority = this.cost + Helper.manhattan(this.board);
        this.previousDirection = previousDirection;
    }

    Node(int[][] b, int br, int bc, String p, int c, int pd, long h) {
        board = b;
        blankRow = br;
        blankColumn = bc;
        path = p;
        cost = c;
        this.previousDirection = pd;
        hash = h;
        priority = cost + Helper.manhattan(board);
    }
}