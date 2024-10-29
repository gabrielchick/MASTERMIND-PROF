package tictactoe; //no minimax

import java.util.Random;
import java.util.Scanner;

public class TicTacToe {
    private char[][] board;
    private char currentPlayer;
    private Scanner scanner;
    private Random random;

    public TicTacToe() {
        board = new char[][] {
            {' ', ' ', ' '},
            {' ', ' ', ' '},
            {' ', ' ', ' '}
        };
        currentPlayer = 'X'; // Player starts as 'X'
        scanner = new Scanner(System.in);
        random = new Random();
    }

    public void playGame() {
        printBoard();
        while (!isGameFinished()) {
            if (currentPlayer == 'X') {
                playerMove();
            } else {
                computerMove();
            }
            printBoard();
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        }
        scanner.close();
    }

    private void playerMove() {
        int move;
        while (true) {
            System.out.println("Choose your move (1-9): ");
            move = scanner.nextInt();
            if (isValidMove(move)) {
                placeMove(move, 'X');
                break;
            } else {
                System.out.println("Invalid move. Try again.");
            }
        }
    }  

    private void computerMove() {
        int move;
        while (true) {
            move = random.nextInt(9) + 1;
            if (isValidMove(move)) {
                System.out.println("Computer chose " + move);
                placeMove(move, 'O');
                break;
            }
        }
    }

    private boolean isValidMove(int position) {
        int row = (position - 1) / 3;
        int col = (position - 1) % 3;
        return board[row][col] == ' ';
    }

    private void placeMove(int position, char symbol) {
        int row = (position - 1) / 3;
        int col = (position - 1) % 3;
        board[row][col] = symbol;
    }

    private boolean isGameFinished() {
        if (checkWinner('X')) {
            System.out.println("Player wins!");
            return true;
        } else if (checkWinner('O')) {
            System.out.println("Computer wins!");
            return true;
        } else if (isBoardFull()) {
            System.out.println("The game ended in a tie!");
            return true;
        }
        return false;
    }

    private boolean checkWinner(char symbol) {
        return (board[0][0] == symbol && board[0][1] == symbol && board[0][2] == symbol) ||
               (board[1][0] == symbol && board[1][1] == symbol && board[1][2] == symbol) ||
               (board[2][0] == symbol && board[2][1] == symbol && board[2][2] == symbol) ||
               (board[0][0] == symbol && board[1][0] == symbol && board[2][0] == symbol) ||
               (board[0][1] == symbol && board[1][1] == symbol && board[2][1] == symbol) ||
               (board[0][2] == symbol && board[1][2] == symbol && board[2][2] == symbol) ||
               (board[0][0] == symbol && board[1][1] == symbol && board[2][2] == symbol) ||
               (board[0][2] == symbol && board[1][1] == symbol && board[2][0] == symbol);
    }

    private boolean isBoardFull() {
        for (char[] row : board) {
            for (char cell : row) {
                if (cell == ' ') return false;
            }
        }
        return true;
    }

    private void printBoard() {
        for (int i = 0; i < board.length; i++) {
            System.out.println(board[i][0] + "|" + board[i][1] + "|" + board[i][2]);
            if (i < board.length - 1) System.out.println("-+-+-");
        }
    }

    public static void main(String[] args) {
        TicTacToe game = new TicTacToe();
        game.playGame();
    }
}
