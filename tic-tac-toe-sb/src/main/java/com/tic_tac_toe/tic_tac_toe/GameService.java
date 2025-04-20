package com.tic_tac_toe.tic_tac_toe;

import org.springframework.stereotype.Service;

@Service
public class GameService {

    private char[][] board;
    private char currentPlayer;
    private String winner;

    // Constructor to initialize the game state
    public GameService() {
        resetGame();
    }

    // Reset the game state
    public void resetGame() {
        board = new char[3][3];
        currentPlayer = 'X';
        winner = null;

        // Initialize board with empty spaces
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
    }

    // Get the current state of the board
    public char[][] getBoard() {
        return board;
    }

    // Get the current player
    public String getCurrentPlayer() {
        return String.valueOf(currentPlayer);
    }

    // Get the winner of the game
    public String getWinner() {
        return winner;
    }

    // Make a move on the board
    public boolean makeMove(int row, int col) {
        if (board[row][col] != ' ' || winner != null) {
            return false; // Invalid move if cell is already occupied or if the game is over
        }

        // Make the move
        board[row][col] = currentPlayer;

        // Check if the current player has won
        if (hasWon(currentPlayer)) {
            winner = String.valueOf(currentPlayer);
        } else if (isDraw()) {
            winner = "Draw"; // Set winner to "Draw" if it's a draw
        } else {
            // Switch to the other player
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        }

        return true;
    }

    // Check if the current player has won
    private boolean hasWon(char player) {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
                return true;
            }
        }

        // Check columns
        for (int i = 0; i < 3; i++) {
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) {
                return true;
            }
        }

        // Check diagonals
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
            return true;
        }
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
            return true;
        }

        return false;
    }

    // Check if the game is a draw (board is full and no winner)
    private boolean isDraw() {
        // If there's already a winner, it's not a draw
        if (winner != null) {
            return false;
        }

        // Check if all cells are filled
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false; // If there's an empty cell, it's not a draw
                }
            }
        }

        return true; // All cells are filled and no winner
    }
}