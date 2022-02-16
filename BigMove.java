import java.lang.Math;

public class BigMove {
    public static boolean isBlocked(Empty[][] map, int[] starting, int[] ending) {
        //starting and ending are exclusive
        if(starting[1] == ending[1] && starting[0] == ending[0]) {
            return false;
        }
        
        if(starting[1] == ending[1]) {
            int lowX = Math.min(starting[0], ending[0]);
            int highX = Math.max(starting[0], ending[0]);
            for(int i = lowX + 1; i < highX; i++) {
                if(map[starting[1]][i].symbols[0] != " ") {
                    return true;
                }
            }
        } else if(starting[0] == ending[0]) {
            int highY = Math.max(starting[1], ending[1]);
            int lowY = Math.min(starting[1], ending[1]);    
            for(int i = lowY + 1; i < highY; i++) {
                if(map[i][starting[0]].symbols[0] != " ") {
                    return true;
                }
            }
        } else if(Math.abs(starting[0] - ending[0]) == Math.abs(starting[1] - ending[1])) {
            int xDirection = Integer.signum(ending[0] - starting[0]);
            int yDirection = Integer.signum(ending[1] - starting[1]);
            for(int i = 1; i < (Math.abs(starting[0] - ending[0])); i++) {
                if(map[starting[1] + (yDirection * i)][starting[0] + (xDirection * i)].symbols[0] != " ") {
                    return true;
                }
            }
        }
        return false;
    }
}