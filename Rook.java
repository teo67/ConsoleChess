public class Rook extends Empty {
    //inherits location, symbol
    public Rook(int _color) {
        super(_color);
        this.symbols = new String[2];
        this.symbols[0] = "♖";
        this.symbols[1] = "♜";
    }

    public boolean move(Empty[][] map, int[] attempt, int[] location) {
        int aX = attempt[0];
        int aY = attempt[1];
        Empty selected = map[aY][aX];
        if(selected.color == this.color) {
            return false;
        }

        if(location[0] == aX && location[1] == aY) {
            return false;
        }
        if((location[0] - aX) != 0 && (location[1] - aY) != 0) {
            return false;
        }
        if(BigMove.isBlocked(map, location, attempt)) {
            return false;
        }
        return true;
    }
}