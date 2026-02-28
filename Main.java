import java.util.Scanner;
import java.util.InputMismatchException;

public class Main {

    public static void main(String[] args) {

        int[][] board = {
                {5, 0, 9, 0, 0, 0, 4, 0, 0},
                {7, 0, 8, 3, 0, 4, 9, 0, 0},
                {6, 0, 1, 0, 0, 0, 7, 3, 0},
                {4, 6, 2, 5, 0, 0, 0, 0, 0},
                {3, 8, 5, 7, 2, 0, 6, 4, 0},
                {1, 0, 7, 4, 0, 8, 2, 0, 0},
                {2, 0, 0, 1, 0, 0, 0, 0, 4},
                {0, 0, 3, 0, 4, 0, 0, 8, 7},
                {0, 7, 0, 0, 5, 3, 0, 0, 6}
        };
        System.out.println("Вітаємо у грі \"Судоку\"!");
        System.out.println("Ваша задача – заповнити сітку цифрами від 1 до 9 так,");
        System.out.println("щоб кожна цифра зустрічалася лише один раз");
        System.out.println("у кожному рядку, стовпці та малому квадраті.");
        System.out.println("Щоб перейти до гри, натисніть Enter.");


        Scanner sc = new Scanner(System.in);
        sc.nextLine();

        while (true) {

            printBoard(board);
            System.out.println("Введіть рядок, стовпець та число (наприклад: 6 2 9) або -1 для виходу:");

            try {

                String line = sc.nextLine();

                if (line.equals("-1")) {
                    break;
                }

                int[] check = parseInput(line);

                int row = check[0];
                int col = check[1];
                int num = check[2];

                checkOne(row, col, num);
                checkTwo(board, row, col);

                if (sudoku(board, row, col, num)) {
                    board[row][col] = num;
                    System.out.println("Правильно!");
                } else {
                    System.out.println("Неправильно!");
                }

            }
            catch (NumberFormatException e) {
                System.out.println("Вводити можна лише числа");
            }
            catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
            catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Не входить у поле");
            }
        }
    }

    static int[] parseInput(String line) {

        if (line == null || line.length() == 0) {
            throw new IllegalArgumentException("Напишіть число");
        }

        String[] parts = line.split(" ");

        if (parts.length != 3) {
            throw new IllegalArgumentException("Потрібно ввести 3 числа");
        }

        int row = Integer.parseInt(parts[0]);
        int col = Integer.parseInt(parts[1]);
        int num = Integer.parseInt(parts[2]);

        return new int[]{row, col, num};
    }

    static void checkOne(int row, int col, int num) {

        if (row < 0 || row > 8) {
            throw new IllegalArgumentException("Рядок має бути 0-8");
        }

        if (col < 0 || col > 8) {
            throw new IllegalArgumentException("Стовпець має бути 0-8");
        }

        if (num < 1 || num > 9) {
            throw new IllegalArgumentException("Число має бути 1-9");
        }
    }

    static void checkTwo(int[][] board, int row, int col) {

        if (board[row][col] != 0) {
            throw new IllegalArgumentException("Клітинка вже заповнена");
        }
    }

    static boolean sudoku(int[][] board, int row, int col, int num) {

        for (int i = 0; i < 9; i++)
            if (board[row][i] == num)
                return false;

        for (int i = 0; i < 9; i++)
            if (board[i][col] == num)
                return false;

        int startRow = row - row % 3;
        int startCol = col - col % 3;

        for (int i = startRow; i < startRow + 3; i++)
            for (int j = startCol; j < startCol + 3; j++)
                if (board[i][j] == num)
                    return false;

        return true;
    }

    static void printBoard(int[][] board) {

        System.out.println("-------------------------");

        for (int i = 0; i < 9; i++) {

            for (int j = 0; j < 9; j++) {

                if (board[i][j] == 0)
                    System.out.print(". ");
                else
                    System.out.print(board[i][j] + " ");

                if ((j + 1) % 3 == 0)
                    System.out.print("| ");
            }

            System.out.println();

            if ((i + 1) % 3 == 0)
                System.out.println("-------------------------");
        }
    }
}
