import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        char[][] board = new char[3][3]; // Creating board
        
        // Initializing the board with empty spaces
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                board[row][col] = ' ';
            }
        }

        char player = 'X'; // Initializing player value 
        boolean gameOver = false;
        Scanner scanner = new Scanner(System.in);

        while (!gameOver) {
            printBoard(board);
            System.out.println("Player " + player + ", enter your move (row and column): ");

            int row = scanner.nextInt();
            int col = scanner.nextInt();
            System.out.println();

            // Check if the input is within bounds
            if (row < 0 || row >= 3 || col < 0 || col >= 3) {
                System.out.println("Invalid move! Row and column should be between 0 and 2.");
                continue;
            }

            // Check if the chosen position is available
            if (board[row][col] == ' ') {
                board[row][col] = player;
                gameOver = hasWon(board, player);

                if (gameOver) {
                    printBoard(board);
                    System.out.println("Player " + player + " has won!");
                } else {
                    // Switch the player
                    player = (player == 'X') ? 'O' : 'X';
                }
            } else {
                System.out.println("Invalid move! The cell is already occupied.");
            }
        }
        scanner.close();
    }

    // Print the board in a readable format
    public static void printBoard(char[][] board) {
        System.out.println("-------------");
        for (int row = 0; row < board.length; row++) {
            System.out.print("| ");
            for (int col = 0; col < board[row].length; col++) {
                System.out.print(board[row][col] + " | ");
            }
            System.out.println();
            System.out.println("-------------");
        }
    }

    // Check if the current player has won
    public static boolean hasWon(char[][] board, char player) {
        // Checking rows
        for (int row = 0; row < 3; row++) {
            if (board[row][0] == player && board[row][1] == player && board[row][2] == player) {
                return true;
            }
        }

        // Checking columns
        for (int col = 0; col < 3; col++) {
            if (board[0][col] == player && board[1][col] == player && board[2][col] == player) {
                return true;
            }
        }

        // Checking diagonals
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
            return true;
        }

        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
            return true;
        }

        return false;
    }
}
