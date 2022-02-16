import java.lang.Math;

public class Bishop extends Empty {
    //inherits location, symbol
    public Bishop(int _color) {
        super(_color);
        this.symbols = new String[2];
        this.symbols[0] = "♗";
        this.symbols[1] = "♝";
    }

    public boolean move(Empty[][] map, int[] attempt, int[] location) {
        int aX = attempt[0];
        int aY = attempt[1];
        Empty selected = map[aY][aX];
        
        if(selected.color == this.color) {
            return false;
        }

        if(aX == location[0] && aY == location[1]) {
            return false;
        }
        if(Math.abs(aX - location[0]) != Math.abs(aY - location[1])) {
            return false;
        }
        if(BigMove.isBlocked(map, location, attempt)) {
            return false;
        }
        return true;
    }
}