import java.lang.Math;

public class Pawn extends Empty {
    //inherits location, symbol
    public Pawn(int _color) {
        super(_color);
        this.symbols = new String[2];
        this.symbols[0] = "♙";
        this.symbols[1] = "♟︎";
    }

    public boolean move(Empty[][] map, int[] attempt, int[] location) {
        //location is current location of the piece, attempt is where it's trying to go
        int aX = attempt[0];
        int aY = attempt[1];
        Empty selected = map[aY][aX];
        if(selected.color == this.color) {
            return false;
        }
        if((this.color * (aY - location[1]) <= 0) || (this.color * (aY - location[1]) > 2)) {
            return false;
        }
        if(selected.symbols[0] == " ") {
            if(aX != location[0]) {
                return false;
            }
            if((this.color * (aY - location[1]) == 2)) {
                if(location[1] != (int)(3.5 - (2.5 * (double)this.color))) {
                    return false;
                }
                if(BigMove.isBlocked(map, location, attempt)) {
                    return false;
                }
            }
        } else {
            if(this.color * (aY - location[1]) != 1) {
                return false;
            }
            if(Math.abs(aX - location[0]) != 1) {
                return false;
            }
        }
        return true;
    }
}