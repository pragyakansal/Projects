import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.util.*;

public class ConnectFourWithBoard {
    private static final int RECT_WIDTH = 100;
    private static final int RECT_LENGTH = 100;
    private static final int NUMBER_ROWS = 6;
    private static final int NUMBER_COLUMNS = 7;

    public static void main (String [] args) {
        char[][] board = new char[NUMBER_ROWS][NUMBER_COLUMNS];
        // This initializes the board, which is a two-dimensional array. Since there are 6 rows and 7 columns, this
        //puts a value of empty character in all 42 positions of the board, which will be replaced by 'R' or 'Y' when the players take their turns.
        for (int row = 1; row <= board.length; row++) {
            for (int column = 1; column <= board[0].length; column++) {
                board[row - 1][column - 1] = ' ';
            }
        }
        Scanner scanner = null;
        try {
            scanner = new Scanner(System.in);


            // The Scanner asks the users to enter either 'R' or 'Y' to take their turn during the game.
            int turn = 1;
            char player = 'R';
            Color graphicPlayer = Color.RED;
            // This defines Player 1 as Red and Player 2 as Yellow.
            boolean winner = false;
            Board b = new Board(-1, Color.BLUE);
            while (turn <= 42 && winner == false) {
                boolean validInput = true;
                int position = 0;
                do {
                    printBoard(board);
                    // This checks if the user has selected a column within the range 1 to 7 and a column that is empty.
                    if (!validInput) {
                        System.out.println("Player " + player + ", You have entered an invalid column. Please choose again.");
                    } else {
                        System.out.println("Player " + player + ": Choose a column from 1 to 7 to enter your piece in.");
                    }
                    // Keeps prompting the player for an input until they enter an integer from 1 to 7.
                    // For instance, if the player enters a special character or a letter, the program
                    // will prompt the user to enter an integer from 1 to 7.
                    boolean isInputValid = false;
                    while (!isInputValid) {
                        if (scanner.hasNextInt()) {
                            position = scanner.nextInt();
                            isInputValid = true;
                        } else {
                            System.out.println("Player " + player + ", You have entered an invalid input. Please enter an integer value from 1 to 7.");
                            scanner.next();
                            continue;
                        }
                    }
                    validInput = checkForValidInput(position, board);
                } while (!validInput);
                int x = 0;
                int y = 0;
                int offset = 22;
                for (int row = board.length - 1; row >= 0; row--) {
                    if (board[row][position - 1] == ' ') {
                        // Initializes the board with either 'R' or 'Y', indicating the player has selected that position.
                        board[row][position - 1] = player;
                        // Initializes the x and y coordinates of each cell of the graphical board.
                        x = (position - 1) * RECT_WIDTH;
                        y = row * RECT_LENGTH;
                        // Sets color of each player on the graphical board (either Red or Yellow).
                        Component component = b.getComponentAt(x, y);
                        Graphics g = component.getGraphics();
                        g.setColor(graphicPlayer);
                        if (row != 0) {
                            y = y - offset;
                        }
                        g.fillOval(x, y, RECT_WIDTH, RECT_LENGTH);
                        break;
                    }
                }
                winner = determineWinner(player, board);
                if (winner) {
                    printBoard(board);
                    break;
                }
                turn++;
                // Switch player.
                if (player == 'R' ) {
                    player = 'Y';
                    graphicPlayer = Color.YELLOW;
                } else {
                    player = 'R';
                    graphicPlayer = Color.RED;
                }
            }
            // Declare the winner.
            if (winner) {
                if (player == 'R') {
                    declareWinner("R", b, winner);
                }
                else {
                    declareWinner("Y", b, winner);
                }
            }
            else {
                declareWinner("Player R and Player Y", b, winner);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (scanner != null) {
                try {
                    scanner.close();
                } catch (Exception e) {
                    System.out.println("Error in closing scanner");
                }
            }
        }
    }

    /**
     * This method prints the board using pipes and underscores. Since there are 7 columns and 6 rows, there will be 7 underscores
     * and 6 pipes for each line of the board.
     * @param board
     */
    public static void printBoard (char[][] board) {
        System.out.println(" 1 2 3 4 5 6 7");
        for (int row = 0; row < board.length; ++row) {
            System.out.print("|");
            for (int column = 0; column < board[0].length; ++column) {
                System.out.print(board[row][column]);
                System.out.print("|");
            }
            System.out.println();
            System.out.println("_______________");
        }

    }

    /**
     * This method checks the validity of the argument entered by the user.
     * If the user enters a row less than 0, a row greater than 6, a column less than 0,
     * a column greater than 7, or if the position in the array is not empty,
     * the method will return false. Otherwise, true.
     * @param column
     * @param board
     * @return
     */
    public static boolean checkForValidInput(int column, char[][] board) {
        if ((column < 1 || column > board[0].length) || board[0][column - 1] != ' ') {
            return false;
        }
        return true;
    }


    /**
     * This method declares the winner by putting a message on the console and the graphical board.
     * @param player
     * @param b
     * @param winner
     */
    public static void declareWinner(String player, Board b, boolean winner) {
        if (winner) {
            System.out.println("PLAYER " + player + " WON THE GAME!");
        } else {
            System.out.println("Player R and Player Y have tied.");
        }
        Graphics g = b.getGraphics();
        g.drawRect(175, 250, 350, 100);
        g.setColor(Color.BLUE);
        g.fillRect(175, 250, 350, 100);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Serif", Font.BOLD, 20));
        if (winner) {
            g.drawString("PLAYER " + player + " WON THE GAME!", 210, 300);
        }
        else {
            g.drawString(player + " have tied.", 210, 300);
        }

    }


    /**
     * This method determines a winner by checking for four consecutive chars of either 'R' or 'Y' either diagonally,
     * vertically, or horizontally. If there are four consecutive chars, the method will return true. If there are not
     * four consecutive chars, the method will return false.
     * @param player
     * @param board
     * @return
     */
    public static boolean determineWinner (char player, char[][] board) {
        boolean winner = false;
        winner = checkUpwardDiagonal(player, board);
        if (winner) {
            return true;
        }
        winner = checkDownwardDiagonal(player, board);
        if (winner) {
            return true;
        }
        winner = checkVertically(player, board);
        if (winner) {
            return true;
        }
        winner = checkHorizontally(player, board);
        return winner;
    }

    /**
     * This method checks the board for an upward diagonal. If there is an upward diagonal of four consecutive chars
     * of either 'R' or 'Y', the method will return true. On the far left side of the board, the upward diagonal must
     * start from row 4 or below. If the diagonal starts from above row 4, there won't be four consecutive 'R' or 'Y' chars.
     * @param player
     * @param board
     * @return
     */
    public static boolean checkUpwardDiagonal (char player, char[][] board) {

        for (int row = board.length - 3; row < board.length; row++) {
            for (int column = 0; column < board[0].length - 3; column++) {
                if (board[row][column] ==  player &&
                        board[row - 1][column + 1] == player &&
                        board[row - 2][column + 2] == player &&
                        board[row - 3][column + 3] == player) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * This method checks the board for a downward diagonal. If there is a downward diagonal of four consecutive chars
     * of either 'R' or 'Y', the method will return true. On the far left side of the board, the downward diagonal must
     * start from row 3 or above. If the diagonal starts from below row 3, there won't be four consecutive 'R' or 'Y' chars.
     * @param player
     * @param board
     * @return
     */
    public static boolean checkDownwardDiagonal (char player, char[][] board) {
        //
        for (int row = 0; row < board.length - 3 ; row++) {
            for (int column = 0; column < board[0].length - 3; column++) {
                if (board[row][column] == player &&
                        board[row + 1][column + 1] == player  &&
                        board[row + 2][column + 2] == player  &&
                        board[row + 3][column + 3] == player)  {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * This method checks the board vertically (up and down). If there are four consecutive chars of either 'R' or 'Y'
     * vertically, the method will return true.
     * @param player
     * @param board
     * @return
     */
    public static boolean checkVertically (char player, char[][] board) {

        for (int row = 0; row < board.length - 3; row++) {
            for (int column = 0; column < board[0].length; column++) {
                if (board[row][column] == player &&
                        board[row + 1][column] == player &&
                        board[row + 2][column] == player &&
                        board[row + 3][column] == player) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * This method checks the board horizontally (left and right). If there are four consecutive chars of either 'R' or 'Y'
     * horizontally, the method will return true.
     * @param player
     * @param board
     * @return
     */
    public static boolean checkHorizontally (char player, char[][] board) {

        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[0].length - 3; column++) {
                if (board[row][column] == player &&
                        board[row][column + 1] == player &&
                        board[row][column + 2] == player &&
                        board[row][column + 3] == player) {
                    return true;
                }
            }
        }
        return false;
    }
}


