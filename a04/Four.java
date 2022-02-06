package a04;

import static boat.Helper.printTimeSince;
import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Four {

    private static final int GRID_SIZE = 5;

    public static void main(String[] args) {
        final long startTime = System.nanoTime();

        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get("a04/input.txt"));
            List<Integer> numbers = Arrays.stream(lines.get(0).split(",")) // first line contains called numbers
                    .map(Integer::parseInt)
                    .collect(toList());

            List<BingoBoard> bingoBoards = readBingoBoards(lines);

            BingoBoard bingo = null; // Reference to BingoBoard which has bingo

            // BingoBoards are read in and we ready to play, let's call the numbers!
            int i = 0;
            while (bingo == null) { // will throw IndexOutOfBoundException if no boards have bingo, not possible
                                    // however since all numbers will be called
                int number = numbers.get(i++);
                for (BingoBoard bingoBoard : bingoBoards) {
                    bingoBoard.mark(number);
                    if (bingoBoard.hasBingo()) { // Bingo!
                        bingo = bingoBoard;
                    }
                }
            }

            System.out.println("Score: " + calculateScore(bingo, numbers.get(i - 1)));
            System.out.println("winning board:\n" + bingo.toString());

        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.out.println("whoops");
            System.exit(-1);
        }

        printTimeSince(startTime);
    }

    private static int calculateScore(BingoBoard bingo, int numberJustCalled) {
        return bingo.sumOfUnmarkedNumbers() * numberJustCalled;
    }

    private static List<BingoBoard> readBingoBoards(List<String> lines) {

        List<BingoBoard> bingoBoards = new ArrayList<>();
        // the first two lines are called numbers and newline, so start at i = 2.
        // Advance by GRID_SIZE + 1 for the newline to get to the start of the next grid
        for (int i = 2; i < lines.size(); i += GRID_SIZE + 1) {
            bingoBoards.add(readBingoBoard(lines.subList(i, i + GRID_SIZE)));
        }
        return bingoBoards;
    }

    // assumes sublist.size() == GRID_SIZE, and that the Strings contain GRID_SIZE ints.
    private static BingoBoard readBingoBoard(List<String> subList) {

        assert subList.size() == GRID_SIZE;
        BingoBoard bingoBoard = new BingoBoard(GRID_SIZE);

        for (int i = 0; i < GRID_SIZE; i++) {
            String[] rowNums = subList.get(i).stripLeading().split(" +");
            // numbers on one row are separated by one or more spaces, however if the first number
            // is smaller than 10, it will split into an empty string first too, so let's strip that

            assert rowNums.length == GRID_SIZE;
            for (int j = 0; j < GRID_SIZE; j++) {
                bingoBoard.grid[i][j] = Integer.parseInt(rowNums[j]);
            }
        }
        return bingoBoard;
    }
}

/**
 * Container with convenience methods for bingo boards.
 * 
 * Mark each number when a new number is called with {@link #mark},
 * Check if it has bingo with {@link #hasBingo}.
 */
class BingoBoard {

    final int GRID_SIZE;
    int[][] grid;
    boolean[][] marked;

    BingoBoard(final int GRID_SIZE) {
        this.GRID_SIZE = GRID_SIZE;
        grid = new int[GRID_SIZE][GRID_SIZE];
        marked = new boolean[GRID_SIZE][GRID_SIZE];
    }

    boolean hasBingo() {

        boolean bingo = false;

        for (int i = 0; i < GRID_SIZE && !bingo; i++) {
            // are all numbers in one row marked?
            boolean rowSoFarMarked = true;
            // are all numbers in one column marked?
            boolean columnSoFarMarked = true;

            for (int j = 0; j < GRID_SIZE; j++) {
                if (!marked[i][j]) {
                    rowSoFarMarked = false;
                }
                if (!marked[j][i]) {
                    columnSoFarMarked = false;
                }
            }
            // if either a complete column or row is marked, we have a winner!!!
            bingo = rowSoFarMarked || columnSoFarMarked;
        }
        return bingo;
    }

    void mark(int number) {

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (number == grid[i][j]) {
                    marked[i][j] = true;
                    return; // numbers on a bingo board are unique
                }
            }
        }
    }

    int sumOfUnmarkedNumbers() {

        int sum = 0;

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (!marked[i][j]) {
                    sum += grid[i][j];
                }
            }
        }
        return sum;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                sb.append(grid[i][j]).append(' ');
                if (grid[i][j] < 10) {
                    sb.append(' ');
                }
            }
            sb.append('\n');
        }
        return sb.toString().stripTrailing();
    }
}