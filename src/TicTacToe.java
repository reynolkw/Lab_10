import java.util.Arrays;
import java.util.Scanner;

public class TicTacToe {
    private static final int DIMENSION = 3;
    private static String[][] board = new String[DIMENSION][DIMENSION];
    private static boolean isGameOver = false;
    private static boolean isWin = false;
    private static String winner = "";
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        boolean playAgain = false;
        do {
            String playerOne = "X";
            String playerTwo = "O";
            int moves = 0;
            int BEGIN_SCANNING = 5;

            clearBoard();
            isGameOver = false;
            isWin = false;
            winner = "";
            displayBoard();
            do {
                // get and validate Player 1 game input
                int playerOneRow, playerOneCol;
                boolean isValidInput = false;
                do {
                    playerOneRow = SafeInput.getRangedInt(in,"TTT: Player 1, enter the row number of your move", 1, 3) - 1;
                    playerOneCol = SafeInput.getRangedInt(in,"TTT: Player 1, enter the column number of your move", 1, 3) - 1;

                    if (isValidMove(playerOneRow, playerOneCol)){
                        isValidInput = true;
                    } else System.out.println("TTT: Invalid move, enter a different move.");

                } while (!isValidInput);

                board[playerOneRow][playerOneCol] = playerOne;
                moves++;

                displayBoard();
                if (moves >= BEGIN_SCANNING)
                    scanBoard();

                if (isGameOver){
                    break;
                }

                // get and validate Player 2 game input
                int playerTwoRow, playerTwoCol;
                isValidInput = false;
                do {
                    playerTwoRow = SafeInput.getRangedInt(in,"TTT: Player 2, enter the row number of your move", 1, 3) - 1;
                    playerTwoCol = SafeInput.getRangedInt(in,"TTT: Player 2, enter the column number of your move", 1, 3) - 1;
                    if (isValidMove(playerTwoRow, playerTwoCol)){
                        isValidInput = true;
                    } else System.out.println("TTT: Invalid move, enter a different move.");
                } while (!isValidInput);

                board[playerTwoRow][playerTwoCol] = playerTwo;
                moves++;

                displayBoard();
                if (moves >= BEGIN_SCANNING)
                    scanBoard();

            } while (!isGameOver);

            if (isWin){
                System.out.println("TTT: Game Over, " + winner + " won the game!");
            } else
                System.out.println("TTT: Game Over, the game is a stalemate!");

            playAgain = SafeInput.getYNConfirm(in,"TTT: Would you like to play again?");
        } while (playAgain);
    }

    /**
     * sets all the board array locations to a space
     */
    private static void clearBoard(){
        String BLANK = " ";
        for (String[] row : board){
            Arrays.fill(row, BLANK);
        }
    }

    /**
     * prints out to the console a display of the tic-tac-toe game
     */
    private static void displayBoard(){
        // creates whitespace above the board
        System.out.println();

        // prints a row element for each row index
        for (int i = 0; i < DIMENSION; i++){
            String[] row = board[i];
            for (int col = 0; col < DIMENSION; col++){
                System.out.printf("  %1s  ", row[col]);

                // handles the grid formatting based on the position
                if (col < DIMENSION - 1)
                    System.out.print("|");
                else
                    System.out.print("\n");
            }

            // prints out the horizontal grid lines
            int BOARD_WIDTH = 17;
            if (i < DIMENSION - 1){
                for (int e = 0; e < BOARD_WIDTH; e++){
                    System.out.print("-");
                }
                System.out.println();
            }
        }

        // creates whitespace below the board
        System.out.println();
    }

    /**
     * determines if a move can be made at a given hasSpace on the board
     * @param row row number of the move
     * @param column column number of the move
     * @return true if the move is a legal move
     */
    private static boolean isValidMove(int row, int column){
        return board[row][column].equals(" ");
    }

    /**
     * scans the board to determine the win state
     * equivalent to the proposed isWin() method
     */
    private static void scanBoard() {
        String[] winVectors = getWinVectors();
        boolean[] isSpace = new boolean[8];
        boolean[] isStalemate = new boolean[8];

        for (int i = 0; i < winVectors.length; i++) {
            // equivalent to the proposed isColWin(), isRowWin(), and isDiagonalWin() methods
            // checks the win vectors for a win condition
            if (winVectors[i].equals("XXX")) {
                isGameOver = true;
                isWin = true;
                winner = "Player 1";
                break;
            } else if (winVectors[i].equals("OOO")) {
                isGameOver = true;
                isWin = true;
                winner = "Player 2";
                break;
            }
            // there is no win condition
            // populate the boolean arrays for determining a stalemate condition
            else {
                isSpace[i] = winVectors[i].contains(" ");
                isStalemate[i] = winVectors[i].contains("X") && winVectors[i].contains("O");
            }
        }

        // equivalent to the proposed isTie() method
        // checks the win vectors for a stalemate condition
        if (!isGameOver)
            isGameOver = isAllTrue(isStalemate) || !isOneTrue(isSpace);
    }

    /**
     * maps the board state into the eight possible win vectors, represented as three-character Strings
     * @return an array containing the eight three-character String win vectors
     */
    private static String[] getWinVectors(){
        String[] winVectors = new String[8];
        winVectors[0] = board[0][0] + board[0][1] + board[0][2]; // Top row
        winVectors[1] = board[1][0] + board[1][1] + board[1][2]; // Middle row
        winVectors[2] = board[2][0] + board[2][1] + board[2][2]; // Bottom row
        winVectors[3] = board[0][0] + board[1][0] + board[2][0]; // Left column
        winVectors[4] = board[0][1] + board[1][1] + board[2][1]; // Middle column
        winVectors[5] = board[0][2] + board[1][2] + board[2][2]; // Right column
        winVectors[6] = board[0][0] + board[1][1] + board[2][2]; // Diagonal from top-left to bottom-right
        winVectors[7] = board[0][2] + board[1][1] + board[2][0]; // Diagonal from top-right to bottom-left
        return winVectors;
    }

    /**
     * determines if all values in a boolean array are true
     * @param values the boolean array
     * @return true if all the values are true
     */
    private static boolean isAllTrue(boolean[] values){
        for (boolean value : values ) if (!value) return false;
        return true;
    }

    /**
     * determines if one of the values in a boolean array are true
     * @param values the boolean array
     * @return true if one of the values is true
     */
    private static boolean isOneTrue(boolean[] values){
        for (boolean value : values ) if (value) return true;
        return false;
    }
}