package a04;

import static boat.Helper.printTimeSince;
import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Four2 {

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

            List<BingoBoard> bingoes = new ArrayList<>(); // Reference to BingoBoards which have bingo

            // BingoBoards are read in and we ready to play, let's call the numbers!
            int i = 0;
            while (i < numbers.size() && !bingoBoards.isEmpty()) { 
                int number = numbers.get(i++);
                bingoes.clear();
                for (BingoBoard bingoBoard : bingoBoards) {
                    bingoBoard.mark(number);
                    if (bingoBoard.hasBingo()) { // Bingo!
                        bingoes.add(bingoBoard);
                    }
                }
                bingoBoards.removeAll(bingoes); // don't have to loop over these next time
            }

            BingoBoard lastBingo = bingoes.get(bingoes.size() - 1);

            System.out.println("Score: " + calculateScore(lastBingo, numbers.get(i - 1)));

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