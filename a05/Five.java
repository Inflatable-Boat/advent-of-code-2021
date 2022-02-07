package a05;

import static boat.Helper.printTimeSince;
import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Five {

    final static int N = 1000;
    static int[][] grid = new int[N][N]; // zero-initialized

    public static void main(String[] args) {
        final long startTime = System.nanoTime();

        List<String> strings = new ArrayList<>();
        try {
            strings = Files.readAllLines(Paths.get("a05/input.txt"));
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.out.println("whoops");
            System.exit(-1);
        }

        for (String string : strings) {
            for (Point point : parseLine(string).getPoints(true)) { // count diagonal lines true/false
                grid[point.x][point.y]++;
            }
        }

        System.out.println("Number of points with at least danger level 2: " +
                findNumberOfPointsWithAtLeast(2));

        printTimeSince(startTime);
    }

    private static int findNumberOfPointsWithAtLeast(int dangerLevel) {

        int total = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (grid[i][j] >= dangerLevel) {
                    total++;
                }
            }
        }
        return total;
    }

    private static Line parseLine(String string) {
        List<Integer> coordinates = Arrays.stream(string.split("(,| -> )"))
                .map(Integer::parseInt)
                .collect(toList());

        return new Line(
                new Point(coordinates.get(0), coordinates.get(1)),
                new Point(coordinates.get(2), coordinates.get(3)));
    }
}
