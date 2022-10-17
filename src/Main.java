import java.util.Random;
import java.util.Scanner;

public class Main {

    static char[][] map;
    static final char DOT_HUMAN = 'X';
    static final char DOT_AI = 'O';
    static final char DOT_EMPTY = '�';
    static final Scanner scanner = new Scanner(System.in);
    static final Random random = new Random();
    static int fieldSizeX;
    static int fieldSizeY;

    /**
     * ������������� �������
     */
    private static void initialize() {
        System.out.print("������� ����������� ���� (�� ����� ��� 4*4) ����� ������: ");
        int i = 1;
        do {
            fieldSizeX = scanner.nextInt();
            fieldSizeY = scanner.nextInt();
            i++;
            if (fieldSizeY < 4 || fieldSizeX < 4) {
                System.out.print("� �� ������, ������ ������ ���� �� �����, ��� 4*4. ������� �" + i + ": ");
            }
        }
        while (fieldSizeY < 4 || fieldSizeX < 4);
        map = new char[fieldSizeX][fieldSizeY];
        for (int x = 0; x < fieldSizeX; x++) {
            for (int y = 0; y < fieldSizeY; y++) {
                map[x][y] = DOT_EMPTY;
            }
        }
    }

    /**
     * ��������� ����
     */
    static void printField() {
        System.out.print("+");
        for (int i = 0; i < fieldSizeX * 2 + 1; i++) {
            System.out.print(i % 2 == 0 ? "-" : i / 2 + 1);
        }
        System.out.println();

        for (int y = 0; y < fieldSizeY; y++) {
            System.out.print(y + 1 + "|");
            for (int x = 0; x < fieldSizeX; x++) {
                System.out.print(map[x][y] + "|");
            }
            System.out.println();
        }

        for (int i = 0; i <= fieldSizeX * 2 + 1; i++) {
            System.out.print("-");
        }
        System.out.println();
    }

    /**
     * ��������� ���� ������ (��������)
     */
    static void humanTurn() {
        int x, y;
        do {
            System.out.print("������� ���������� ���� X (�� 1 �� " + fieldSizeX + "\n"
                    + ") � Y (�� 1 �� " + fieldSizeY + ") ����� ������ >>> ");
            x = scanner.nextInt() - 1;
            y = scanner.nextInt() - 1;
            if (!isCellEmpty(x, y)) System.out.println("\n" + "���� ������! �������� ��������� ����!");
        } while (isCellValid(x, y) && !isCellEmpty(x, y));
        map[x][y] = DOT_HUMAN;
    }

    /**
     * ��������� ���� ����������
     */
    static void aiTurn() {
        int x, y;
        // ���� while ��������� ������������ ��������
        char state = ' ';
        int i, j;
        for (j = 0; j < fieldSizeY; j++) {
            for (i = 0; i < fieldSizeX; i++) {
                state = map[i][j];
                map[i][j] = DOT_HUMAN;
                if (checkWin(DOT_HUMAN)) {
                    map[i][j] = DOT_AI;
                    break;
                }
                map[i][j] = state;
            }
        }
        do {
            x = random.nextInt(fieldSizeX);
            y = random.nextInt(fieldSizeY);
        } while (!isCellEmpty(x, y));
        map[x][y] = DOT_AI;
    }

    /**
     * �������� ������������ ����� (���������� ���� �� ������ ��������� ���������� �������,
     * ������������ ������� ����)
     *
     * @param x
     * @param y
     * @return
     */
    private static boolean isCellValid(int x, int y) {
        return x >= 0 && x < fieldSizeX && y > 0 && y < fieldSizeY;
    }

    /**
     * ��������, �������� �� ������ ������ (DOT_EMPTY)
     *
     * @param x
     * @param y
     * @return
     */
    static boolean isCellEmpty(int x, int y) {
        return map[x][y] == DOT_EMPTY;
    }

    /**
     * �������� ������ ������ (�������/���������)
     *
     * @param c
     * @return
     */
    static boolean checkWin(char c) {

        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                if (checkWinHor(x, y, c)) return true;
                if (checkWinVer(x, y, c)) return true;
                if (checkWinDiaXY(x, y, c)) return true;
                if (checkWinDiaYX(x, y, c)) return true;
            }
        }

//        if (map[0][0] == c && map[0][1] == c && map[0][2] == c) return true;
//        if (map[1][0] == c && map[1][1] == c && map[1][2] == c) return true;
//        if (map[2][0] == c && map[2][1] == c && map[2][2] == c) return true;
//
//        if (map[0][0] == c && map[1][0] == c && map[2][0] == c) return true;
//        if (map[0][1] == c && map[1][1] == c && map[2][1] == c) return true;
//        if (map[0][2] == c && map[1][2] == c && map[2][2] == c) return true;
//
//        if (map[0][0] == c && map[1][1] == c && map[2][2] == c) return true;
//        if (map[0][2] == c && map[1][1] == c && map[2][0] == c) return true;

        return false;
    }

    /**
     * �������� �� ����� (��� ���� ��������� ������� ������ ��� ����������)
     *
     * @return
     */
    static boolean checkDraw() {
        for (int x = 0; x < fieldSizeX; x++) {
            for (int y = 0; y < fieldSizeY; y++) {
                if (isCellEmpty(x, y)) return false;
            }
        }
        return true;
    }

    /**
     * ����� �������� ��������� ����
     *
     * @param dot - ������� �����
     * @param s   - �������� ������
     * @return
     */
    static boolean gameCheck(char dot, String s) {
        if (checkWin(dot)) {
            System.out.println(s);
            return true;
        }
        if (checkDraw()) {
            System.out.println("�����!");
            return true;
        }
        return false;
    }

    /**
     * �������� ������ �� �����������
     */
    static boolean checkWinHor(int x, int y, char dot) {
        int counterX = 0; // ������� �� �����������

        // �������� �� �����������
        while (map[x][y] == dot) {
            x++;
            counterX++;
            if (counterX == 4) return true;
            if (x == fieldSizeX) return false;
        }
        return false;
    }

    /**
     * �������� ������ �� ���������
     */
    static boolean checkWinVer(int x, int y, char dot) {
        int counterY = 0; // ������� �� ���������
        // �������� �� ���������
        while (map[x][y] == dot) {
            y++;
            counterY++;
            if (counterY == 4) return true;
            if (y >= fieldSizeY) return false;
        }
        return false;
    }

    /**
     * �������� ������ �� ��������� ������-����
     */
    static boolean checkWinDiaXY(int x, int y, char dot) {
        int counterXY = 0; // ������� �� ��������� ������-����
        // �������� �� ��������� ������-����
        while (map[x][y] == dot) {
            x++;
            y++;
            counterXY++;
            if (counterXY == 4) return true;
            if (x == fieldSizeX || y == fieldSizeY) return false;
        }
        return false;
    }

    /**
     * �������� ������ �� ��������� ������-�����
     */
    static boolean checkWinDiaYX(int x, int y, char dot) {
        int counterYX = 0; // ������� �� ��������� ������-�����
        // �������� �� ��������� ������-�����
        while (map[x][y] == dot) {
            x++;
            y--;
            counterYX++;
            if (counterYX == 4) return true;
            if (y < 0) return false;
            if (x == fieldSizeX || y == fieldSizeY) return false;
        }
        return false;
    }

    public static void main(String[] args) {
        while (true) {
            initialize();
            printField();
            while (true) {
                humanTurn();
                printField();
                if (gameCheck(DOT_HUMAN, "�� ��������!")) break;
                aiTurn();
                printField();
                if (gameCheck(DOT_AI, "������� ���������!")) break;
            }
            System.out.print("������� ������� ��� ���? (Y - ��)");
            if (!scanner.next().equalsIgnoreCase("Y")) break;
        }
    }
}