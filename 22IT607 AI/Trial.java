import java.util.Scanner;
public class Trial {

    public static void drawBoard(char[][] board) {
        System.out.println("-------------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " | ");
            }
            System.out.println("\n-------------");
        }
    }

    public static boolean checkWin(char[][] board, int[][] magic, char player) {
        int sum = 0;

        // Check rows
        for (int i = 0; i < 3; i++) {
            sum = 0;
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == player) {
                    sum += magic[i][j];
                }
            }
            if (sum == 15) {
                return true;
            }
        }

        // Check columns
        for (int j = 0; j < 3; j++) {
            sum = 0;
            for (int i = 0; i < 3; i++) {
                if (board[i][j] == player) {
                    sum += magic[i][j];
                }
            }
            if (sum == 15) {
                return true;
            }
        }

        // Check cross
        sum = 0;
        for (int i = 0; i < 3; i++) {
            if (board[i][i] == player) {
                sum += magic[i][i];
            }
        }
        if (sum == 15) {
            return true;
        }

        sum = 0;
        for (int i = 0; i < 3; i++) {
            if (board[i][2 - i] == player) {
                sum += magic[i][2 - i];
            }
        }
        if (sum == 15) {
            return true;
        }

        return false;
    }

    public static int minimax(char[][] board, int[][] magic, char player, int depth, boolean isMaximizing) {
        if (checkWin(board, magic, 'O')) {
            return 1;
        } else if (checkWin(board, magic, 'X')) {
            return -1;
        } else if (isBoardFull(board)) {
            return 0;
        }

        if (isMaximizing) {
            int bestScore = -1000;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = 'O';
                        int score = minimax(board, magic, player, depth + 1, false);
                        board[i][j] = ' ';
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = 1000;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = 'X';
                        int score = minimax(board, magic, player, depth + 1, true);
                        board[i][j] = ' ';
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
            return bestScore;
        }
    }

    public static boolean isBoardFull(char[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(":::::::::::::Welcome to Tic-Tac-Toe:::::::::::::");

        char[][] board = { {' ',' ',' ' },
                           {' ',' ',' ' },
                           {' ',' ',' '} };

        int[][] magic = { { 8, 1, 6 },
                          { 3, 5, 7 },
                          { 4, 9, 2 } };

        char player = 'X';
        int turn;

        for (turn = 0; turn < 9; turn++) {
            drawBoard(board);
            if (player == 'X') {
                int row, col;
                while (true) {
                    System.out.print("Player X, Enter cell in row and column form (0-2): ");
                    row = scanner.nextInt();
                    col = scanner.nextInt();
                    if (board[row][col]!= ' ' || row > 2 || row < 0 || col < 0 || col > 2) {
                        System.out.println("Enter valid input!");
                    } else {
                        break;
                    }
                }
                board[row][col] = player;
            } else {
                int bestScore = -1000;
                int bestRow = -1;
                int bestCol = -1;
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (board[i][j] == ' ') {
                            board[i][j] = 'O';
                            int score = minimax(board, magic, player, 0, false);
                            board[i][j] = ' ';
                            if (score > bestScore) {
                                bestScore = score;
                                bestRow = i;
                                bestCol = j;
                            }
                        }
                    }
                }
                board[bestRow][bestCol] = player;
            }

            if (checkWin(board, magic, player)) {
                System.out.println("\nPlayer " + player + " WINS");
                drawBoard(board);
                break;
            }
            player = (player == 'X')? 'O' : 'X';
        }

        if (turn == 9 &&!checkWin(board, magic, 'X') &&!checkWin(board, magic, 'O')) {
            System.out.println("It's a draw!");
        }

        scanner.close();
    }
}