import java.util.Scanner;
import java.lang.Math;

class Main {
    public static Empty[][] map = new Empty[8][8];
    public static boolean[] player1Moved = { false, false, false };// left rook, king, right rook
    public static boolean[] player2Moved = { false, false, false };

    public static Scanner scanner = new Scanner(System.in);

    public static char[] letters = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h' };
    public static char[] numbers = { '8', '7', '6', '5', '4', '3', '2', '1' };

    public static boolean isCastling(int[][] position, int team) {
        int[] starting = position[0];
        int[] ending = position[1];
        if(map[starting[1]][starting[0]].symbols[0] != "♔") {
            return false;
        }
        boolean[] checking;
        if(team == -1) {
            checking = player1Moved;
        } else {
            checking = player2Moved;
        }
        if(checking[1]) {
            return false;
        }
        if(ending[0] == 2) {
            if(checking[0]) {
                return false;
            }
            if(map[starting[1]][1].symbols[0] != " " || map[starting[1]][2].symbols[0] != " " || map[starting[1]][3].symbols[0] != " ") {
                return false;
            }
            return true;
        }
        if(ending[0] == 6) {
            if(checking[2]) {
                return false;
            }
            if(map[starting[1]][5].symbols[0] != " " || map[starting[1]][6].symbols[0] != " ") {
                return false;
            }
            return true;
        }
        return false;
    }

    public static int[][] getInput(int team) { // example: a1-a2
        if(scanner.hasNext()) {
            String input = scanner.next();
            int[] indices = { -1, -1, -1, -1 };
            if(input.length() > 4) {
                for(int i = 0; i < letters.length; i++) {
                    if(letters[i] == input.charAt(0)) { indices[0] = i; }
                    if(letters[i] == input.charAt(3)) { indices[2] = i; }
                    if(numbers[i] == input.charAt(1)) { indices[1] = i; }
                    if(numbers[i] == input.charAt(4)) { indices[3] = i; }
                }
            }
            if(indices[0] != -1 && indices[1] != -1 && indices[2] != -1 && indices[3] != -1) {
                int[][] returning = { { indices[0], indices[1] }, { indices[2], indices[3] } };
                if(map[returning[0][1]][returning[0][0]].color == team && isCastling(returning, team)) {
                    return returning;
                }
                if(map[returning[0][1]][returning[0][0]].color == team && map[returning[0][1]][returning[0][0]].move(map, returning[1], returning[0])) {
                    return returning;
                }
            }
        }
        System.out.print("\nError - try again. Some things that could've gone wrong: you used the wrong syntax (example: try e2-e4), you tried making an invalid move, or you tried castling when you had already moved your king or rook.\n");
        scanner.nextLine();
        return getInput(team);
    }

    public static String returnBoard() {
        String returning = "";
        for(int i = 0; i < letters.length; i++) {
            returning += ("  " + letters[i]);
        }
        for(int i = 0; i < numbers.length; i++) {
            returning += ("\n" + numbers[i] + " ");
            for(int j = 0; j < letters.length; j++) {
                if(map[i][j].color == 0) {
                    returning += "☐  ";
                } else {
                    returning += (map[i][j].symbols[(map[i][j].color + 1) / 2] + "  "); //...
                }
            }
        }
        return returning;
    }

    public static boolean takeTurn(int playerColor) { // returns true if game is over
        int[][] playerInput;
        Empty savedStart;
        Empty savedFinish;
        Empty savedStart2 = new Empty(0);// only for castling rook
        Empty savedFinish2 = new Empty(0); // ^
        int rookStartX = -1; // ^
        int rookEndX = -1; // ^
        boolean triedCastling = false;

        playerInput = getInput(playerColor);

        boolean initialCheck = Check.checkForCheck(map, playerColor);
        
        savedStart = map[playerInput[0][1]][playerInput[0][0]];
        savedFinish = map[playerInput[1][1]][playerInput[1][0]];
        map[playerInput[1][1]][playerInput[1][0]] = map[playerInput[0][1]][playerInput[0][0]];
        map[playerInput[0][1]][playerInput[0][0]] = new Empty(0);
        if(savedStart.symbols[0] == "♔" && Math.abs(playerInput[1][0] - playerInput[0][0]) > 1) {
            triedCastling = true;
            if(playerInput[1][0] == 2) {
                savedStart2 = map[playerInput[0][1]][0];
                savedFinish2 = map[playerInput[0][1]][3];
                rookStartX = 0;
                rookEndX = 3;
            } else if(playerInput[1][0] == 6) {
                savedStart2 = map[playerInput[0][1]][7];
                savedFinish2 = map[playerInput[0][1]][5];
                rookStartX = 7;
                rookEndX = 5;
            }
            map[playerInput[0][1]][rookEndX] = map[playerInput[0][1]][rookStartX];
            map[playerInput[0][1]][rookStartX] = new Empty(0);
        }
        while(Check.checkForCheck(map, playerColor) || (initialCheck && triedCastling)) {
            map[playerInput[1][1]][playerInput[1][0]] = savedFinish;
            map[playerInput[0][1]][playerInput[0][0]] = savedStart;
            if(triedCastling) {
                map[playerInput[0][1]][rookStartX] = savedStart2;
                map[playerInput[0][1]][rookEndX] = savedFinish2;
            }
            System.out.println("Hm, it looks like that move would put you in check, or you tried castling out of check.");
            playerInput = getInput(playerColor);
            savedStart = map[playerInput[0][1]][playerInput[0][0]];
            savedFinish = map[playerInput[1][1]][playerInput[1][0]];
            triedCastling = false;
            if(savedStart.symbols[0] == "♔" && Math.abs(playerInput[1][0] - playerInput[0][0]) > 1) {
                triedCastling = true;
                if(playerInput[1][0] == 2) {
                    savedStart2 = map[playerInput[0][1]][0];
                    savedFinish2 = map[playerInput[0][1]][3];
                    rookStartX = 0;
                    rookEndX = 3;
                } else if(playerInput[1][0] == 6) {
                    savedStart2 = map[playerInput[0][1]][7];
                    savedFinish2 = map[playerInput[0][1]][5];
                    rookStartX = 7;
                    rookEndX = 5;
                }
                map[playerInput[0][1]][rookEndX] = map[playerInput[0][1]][rookStartX];
                map[playerInput[0][1]][rookStartX] = new Empty(0);
            }
            map[playerInput[1][1]][playerInput[1][0]] = map[playerInput[0][1]][playerInput[0][0]];
            map[playerInput[0][1]][playerInput[0][0]] = new Empty(0);  
        }
        int yLevel = 7 - ((7 * playerColor + 7) / 2);
        System.out.println(yLevel);
        boolean[] checking;
        if(playerColor == -1) { checking = player1Moved; } else { checking = player2Moved; }
        if(map[yLevel][0].symbols[0] != "♖") { checking[0] = true; }
        if(map[yLevel][4].symbols[0] != "♔") { checking[1] = true; }
        if(map[yLevel][7].symbols[0] != "♖") { checking[2] = true; }
        for(int i = 0; i < 7; i++) {
            if(map[7 - yLevel][i].symbols[0] == "♙" && map[7 - yLevel][i].color == playerColor) {
                map[7 - yLevel][i] = new Queen(playerColor);
            }
        }
        System.out.println("\n".repeat(50));
        System.out.println(returnBoard());

        boolean lastCheck = Check.checkForCheck(map, playerColor * -1);
        if(lastCheck) {
            System.out.println("Player " + (3 + (playerColor * -1)) / 2 + " is now in check!");
        }
        if(Check.checkForMate(map, playerColor * -1)) {
            if(lastCheck) {
                System.out.println("It's checkmate! Player " + (3 + playerColor) / 2 + " wins!");
            } else {
                System.out.println("It's a stalemate!");
            }
            return true;
        }
        
        System.out.println("\nPlayer " + (3 + (playerColor * -1)) / 2 + ", it's your turn.");
        return false;
    }
 
    public static void main(String[] args) {
        for(int i = 0; i < 2; i++) {
            map[7 * i][0] = new Rook(i * -2 + 1);
            map[7 * i][1] = new Knight(i * -2 + 1);
            map[7 * i][2] = new Bishop(i * -2 + 1);
            map[7 * i][3] = new Queen(i * -2 + 1);
            map[7 * i][4] = new King(i * -2 + 1);
            map[7 * i][5] = new Bishop(i * -2 + 1);
            map[7 * i][6] = new Knight(i * -2 + 1);
            map[7 * i][7] = new Rook(i * -2 + 1);
        }
        for(int i = 0; i < 8; i++) {
            map[1][i] = new Pawn(1);
            for(int j = 2; j < 6; j++) {
                map[j][i] = new Empty(0);
            }
            map[6][i] = new Pawn(-1);
        }

        System.out.println("\n" + returnBoard());
        System.out.println("\n\nWelcome to Chess! Player 1 [white], it's your turn to go first! Here's an example move: e2-e4.");

        while(true) {
            if(takeTurn(-1)) {
                return;
            }
            if(takeTurn(1)) {
                return;
            }
        }
    }
}