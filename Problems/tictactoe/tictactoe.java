package Problems.tictactoe;
// Enums

import java.util.Scanner;

enum Symbol {
    X, O, EMPTY
}

enum GameState {
    IN_PROGRESS,
    WON,
    DRAW
}

// Core Classes

class Player {
    String name;
    Symbol symbol;

    Player(String name, Symbol symbol) {
        this.name = name;
        this.symbol = symbol;
    }
}

class Cell {
    int row, col;
    Symbol value;

    Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.value = Symbol.EMPTY;
    }
}

class Board {
    private Cell[][] grid;
    private static final int SIZE = 3;

    Board() {
        grid = new Cell[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                grid[i][j] = new Cell(i, j);
            }
        }
    }

    boolean isCellEmpty(int row, int col) {
        return grid[row][col].value == Symbol.EMPTY;
    }

    void placeMark(int row, int col, Symbol symbol) {
        grid[row][col].value = symbol;
    }

    boolean isFull() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (grid[i][j].value == Symbol.EMPTY) return false;
            }
        }
        return true;
    }

    boolean checkWin(int row, int col, Symbol symbol) {
        // Check row
        boolean win = true;
        for (int j = 0; j < SIZE; j++) {
            if (grid[row][j].value != symbol) win = false;
        }
        if (win) return true;

        // Check column
        win = true;
        for (int i = 0; i < SIZE; i++) {
            if (grid[i][col].value != symbol) win = false;
        }
        if (win) return true;

        // Diagonal (top-left to bottom-right)
        if (row == col) {
            win = true;
            for (int i = 0; i < SIZE; i++) {
                if (grid[i][i].value != symbol) win = false;
            }
            if (win) return true;
        }

        // Diagonal (top-right to bottom-left)
        if (row + col == SIZE - 1) {
            win = true;
            for (int i = 0; i < SIZE; i++) {
                if (grid[i][SIZE - 1 - i].value != symbol) win = false;
            }
            if (win) return true;
        }

        return false;
    }

    void printBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                switch (grid[i][j].value) {
                    case X: System.out.print(" X "); break;
                    case O: System.out.print(" O "); break;
                    default: System.out.print(" . ");
                }
            }
            System.out.println();
        }
    }
}

class Game {
    private Board board;
    private Player playerX, playerO;
    private Player currentPlayer;
    private GameState state;
    private Player winner;

    Game(Player x, Player o) {
        this.board = new Board();
        this.playerX = x;
        this.playerO = o;
        this.currentPlayer = x;
        this.state = GameState.IN_PROGRESS;
    }

    boolean makeMove(int row, int col) {
        if (state != GameState.IN_PROGRESS) return false;
        if (!board.isCellEmpty(row, col)) return false;

        board.placeMark(row, col, currentPlayer.symbol);

        if (board.checkWin(row, col, currentPlayer.symbol)) {
            state = GameState.WON;
            winner = currentPlayer;
        } else if (board.isFull()) {
            state = GameState.DRAW;
        } else {
            switchTurn();
        }

        return true;
    }

    void switchTurn() {
        currentPlayer = (currentPlayer == playerX) ? playerO : playerX;
    }

    boolean isGameOver() {
        return state != GameState.IN_PROGRESS;
    }

    GameState getGameState() {
        return state;
    }

    Player getCurrentPlayer() {
        return currentPlayer;
    }

    Player getWinner() {
        return winner;
    }

    void display() {
        board.printBoard();
    }
}

// Main (for CLI testing)
class Main {
    public static void main(String[] args) {
        Player x = new Player("Alice", Symbol.X);
        Player o = new Player("Bob", Symbol.O);
        Game game = new Game(x, o);

        Scanner scanner = new Scanner(System.in);
        while (!game.isGameOver()) {
            game.display();
            Player current = game.getCurrentPlayer();
            System.out.println(current.name + "'s move (row col): ");
            int row = scanner.nextInt();
            int col = scanner.nextInt();
            if (row >= 3 || col >= 3 || !game.makeMove(row, col)) {
                System.out.println("Invalid move. Try again.");
            }
        }
        game.display();
        if (game.getGameState() == GameState.WON) {
            System.out.println("Winner: " + game.getWinner().name);
        } else {
            System.out.println("It's a draw!");
        }
    }
}
