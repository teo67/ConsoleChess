public class Empty {
    public String[] symbols = { " ", " " };
    public int color; // -1 is white, 1 is black
    public Empty(int _color) {
        this.color = _color;
    }

    public boolean move(Empty[][] map, int[] attempt, int[] location) {
        return false;
    }
}