import java.util.Scanner;

public class tictactoe {
    
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
    
    public static boolean checkWin(char[][] board, char player) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
                return true;
            }
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) {
                return true;
            }
        }
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
                return true;
        }
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
                return true;
        }
        
        return false;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(":::::::::::::Welcome to Tic-Tac-Toe:::::::::::::");
        
        char[][] board = { { ' ', ' ', ' ' },
                           { ' ', ' ', ' ' },
                           { ' ', ' ', ' ' } };
        
        char player = 'X';
        int row, col, turn;
        
        for (turn = 0; turn < 9; turn++) {
            drawBoard(board);
            while (true) {
                System.out.print("Player " + player + ", Enter cell in row and column form : ");
                row = scanner.nextInt();
                col = scanner.nextInt();
                if (board[row][col] != ' ' || row > 2 || row < 0 || col < 0 || col > 2) {
                    System.out.println("Enter valid input !");
                } else {
                    break;
                }
            }
            board[row][col] = player;
            if (checkWin(board, player)) {
                System.out.println("\nPlayer " + player + " WINS");
                drawBoard(board);
                break;
            }
            player = (player == 'X') ? 'O' : 'X';
        }
        
        if (turn == 9 && !checkWin(board, 'X') && !checkWin(board, 'O')) {
            System.out.println("It's a draw!");
        }
        
        scanner.close();
    }
}