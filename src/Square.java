public class Square {
    private int x;
    private int y;
    private int value;
    private int lookupIndex;

    public Square(int x, int y, int value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getLookupIndex() {
        return lookupIndex;
    }

    public void setLookupIndex(int lookupIndex) {
        this.lookupIndex = lookupIndex;
    }

    public void move(Direction direction) {
        switch (direction) {
            case UP:
                y--;
                break;
            case DOWN:
                y++;
                break;
            case LEFT:
                x--;
                break;
            case RIGHT:
                x++;
                break;
            default:
                break;
        }
    }

    @Override
    public String toString() {
        return "[" + x + "," + y + "]: " + value;
    }

}
