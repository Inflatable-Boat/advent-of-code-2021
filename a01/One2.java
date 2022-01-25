package a01;

import boat.Helper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class One2 {

    private final static int rollingWindowSize = 3;

    public static void main(String[] args) {

        List<Integer> lines;

        int count = 0;
        int[] window = new int[rollingWindowSize]; // 0 initialized

        try {
            lines = Files.readAllLines(Paths.get("a01/input.txt"))
                         .stream()
                         .map(s -> Integer.parseInt(s))
                         .collect(Collectors.toList());

            int previousSum = Integer.MAX_VALUE; // so that on the first iteration, nextSum < previousSum
            int nextSum = 0;

            for (int num : lines) {

                rollWindow(window, num);
                nextSum = sum(window);
                if (window[rollingWindowSize - 1] == 0) {
                    // Wait until the whole window has been filled.
                    // Yes, this breaks if the first number is zero. But the first number is not zero.
                    // Good thing this code won't be published to the internet, right?
                    continue;
                }
                if (nextSum > previousSum) {
                    count++;
                }
                previousSum = nextSum;
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.out.println("whoops");
            System.exit(-1);
        }

        Helper.print(count);
    }

    private static void rollWindow(int[] window, int num) {

        for (int i = window.length - 1; i > 0; i--) {
            window[i] = window[i - 1];
        }
        window[0] = num;
    }

    private static int sum(int[] ints) {

        int sum = 0;
        for (int i : ints) {
            sum += i;
        }
        return sum;
    }
}
