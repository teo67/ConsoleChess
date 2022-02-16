public class Check {
    public static int[] findKing(Empty[][] map, int color) {
        int[] kingLocation = new int[2];
        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[i].length; j++) {
                if(map[i][j].color == color && map[i][j].symbols[0] == "♔") {
                    kingLocation[0] = j;
                    kingLocation[1] = i;
                    return kingLocation;
                }
            }
        }
        return kingLocation;
    }

    public static boolean checkForCheck(Empty[][] map, int color) {
        int[] kingLocation = findKing(map, color); // { 4, 0 }

        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[i].length; j++) {
                if(map[i][j].color == (color * -1)) {
                    int[] ji = { j, i };
                    boolean canTake = map[i][j].move(map, kingLocation, ji);
                    if(canTake) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean checkForMate(Empty[][] map, int color) {
        Empty[][] fullProto = new Empty[map.length][];
        for(int a = 0; a < map.length; ++a) {
            fullProto[a] = new Empty[map[a].length];
            System.arraycopy(map[a], 0, fullProto[a], 0, map[a].length);
        } // create a prototype map

        //int[] kingLocation = findKing(map, color); // find the targeted king

        //checks every possible move on the board to see if it prevents check
        for(int i = 0; i < fullProto.length; i++) {
            for(int j = 0; j < fullProto[i].length; j++) {
                if(fullProto[i][j].color == color) {
                Empty selected = fullProto[i][j];
                int[] started = { j, i };
                    for(int k = 0; k < fullProto.length; k++) {
                        for(int l = 0; l < fullProto[k].length; l++) {
                            int[] targeted = { l, k };
                            if(selected.move(fullProto, targeted, started)) {
                                Empty saved = fullProto[k][l];
                                fullProto[k][l] = selected;
                                fullProto[i][j] = new Empty(0);
                                boolean inCheck = checkForCheck(fullProto, color);
                                fullProto[k][l] = saved;
                                fullProto[i][j] = selected;
                                if(!inCheck) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
        
    }
}