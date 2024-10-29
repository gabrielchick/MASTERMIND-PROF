package tictactoe; //with minimax and debug codes
import java.util.Scanner;

public class TicTacToeWithMinimax {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char[][] board = {
            {' ', ' ', ' '},
            {' ', ' ', ' '},
            {' ', ' ', ' '}
        };

        char currentPlayer = 'X'; // Player is 'X'
        boolean gameWon = false;

        while (!gameWon) {
            printBoard(board);
            if (currentPlayer == 'X') {
                playerTurn(board, scanner);
            } else {
                computerTurn(board);
            }
            gameWon = checkWin(board, currentPlayer);
            if (gameWon) {
                printBoard(board);
                System.out.println("Player " + currentPlayer + " wins!");
                break;
            }
            if (isBoardFull(board)) {
                printBoard(board);
                System.out.println("The game ended in a tie!");
                break;
            }
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X'; // Switch players
        }

        scanner.close();
    }

    private static void printBoard(char[][] board) {
        System.out.println("Current Board:");
        for (int i = 0; i < 3; i++) {
            System.out.print(board[i][0] + " | " + board[i][1] + " | " + board[i][2]);
            System.out.println();
            if (i < 2) {
                System.out.println("---------");
            }
        }
    }

    private static void playerTurn(char[][] board, Scanner scanner) {
        int move;
        while (true) {
            System.out.print("Enter your move (1-9): ");
            move = scanner.nextInt() - 1; // Convert to 0-based index
            if (move >= 0 && move < 9 && board[move / 3][move % 3] == ' ') {
                board[move / 3][move % 3] = 'X'; // Place 'X' on the board
                System.out.println("Player placed X at position: " + (move + 1));
                break;
            } else {
                System.out.println("Invalid move. Try again.");
            }
        }
    }

    private static void computerTurn(char[][] board) {
        System.out.println("Computer's turn (O):");
        int bestMove = findBestMove(board);
        board[bestMove / 3][bestMove % 3] = 'O'; // Place 'O' on the board
        System.out.println("Computer chose position: " + (bestMove + 1));
    }

    private static int findBestMove(char[][] board) {
        int bestValue = -1000; // Initialize to a very low value
        int bestMove = -1;

        // Evaluate all possible moves
        for (int i = 0; i < 9; i++) {
            if (board[i / 3][i % 3] == ' ') { // Check if the position is empty
                board[i / 3][i % 3] = 'O'; // Make the move
                int moveValue = minimax(board, 0, false); // Minimax evaluation
                board[i / 3][i % 3] = ' '; // Undo the move
                System.out.println("Evaluating move " + (i + 1) + ": Minimax value = " + moveValue);
                if (moveValue > bestValue) {
                    bestMove = i; // Update best move
                    bestValue = moveValue;
                    System.out.println("Best move updated to: " + (bestMove + 1));
                }
            }
        }
        return bestMove;
    }

    private static int minimax(char[][] board, int depth, boolean isMax) {
        // Check for terminal states
        if (checkWin(board, 'O')) return 10 - depth; // AI wins
        if (checkWin(board, 'X')) return depth - 10; // Player wins
        if (isBoardFull(board)) return 0; // Tie

        if (isMax) {
            int bestValue = -1000;
            for (int i = 0; i < 9; i++) {
                if (board[i / 3][i % 3] == ' ') {
                    board[i / 3][i % 3] = 'O'; // AI's move
                    bestValue = Math.max(bestValue, minimax(board, depth + 1, false));
                    board[i / 3][i % 3] = ' '; // Undo the move
                }
            }
            return bestValue;
        } else {
            int bestValue = 1000;
            for (int i = 0; i < 9; i++) {
                if (board[i / 3][i % 3] == ' ') {
                    board[i / 3][i % 3] = 'X'; // Player's move
                    bestValue = Math.min(bestValue, minimax(board, depth + 1, true));
                    board[i / 3][i % 3] = ' '; // Undo the move
                }
            }
            return bestValue;
        }
    }

    private static boolean checkWin(char[][] board, char symbol) {
        // Check rows, columns, and diagonals for a win
        for (int i = 0; i < 3; i++) {
            if ((board[i][0] == symbol && board[i][1] == symbol && board[i][2] == symbol) || // Check rows
                (board[0][i] == symbol && board[1][i] == symbol && board[2][i] == symbol)) { // Check columns
                System.out.println("Player " + symbol + " has won!");
                return true;
            }
        }
        // Check diagonals
        if ((board[0][0] == symbol && board[1][1] == symbol && board[2][2] == symbol) ||
            (board[0][2] == symbol && board[1][1] == symbol && board[2][0] == symbol)) {
            System.out.println("Player " + symbol + " has won!");
            return true;
        }
        return false;
    }

    private static boolean isBoardFull(char[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false; // Found an empty space
                }
            }
        }
        return true; // No empty spaces found
    }
}
