package a03;

import static boat.Helper.print;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Three {
    
    public static void main(String[] args) {

        List<String> lines;
        try {
            int gamma = 0, epsilon = 0;
            lines = Files.readAllLines(Paths.get("a03/input.txt"));
            final int NUM_LINES = lines.size();
            final int NUM_BITS = lines.get(0).length();
            int[] bitCount = new int[NUM_BITS];

            print("number of lines: " + NUM_LINES);
            print("number of bits per line: " + NUM_BITS);

            for (String line : lines) {
                for (int i = 0; i < NUM_BITS; i++) {
                    if (line.charAt(i) == '1') {
                        bitCount[i]++;
                    }
                }
            }
            for (int i = 0; i < bitCount.length; i++) {
                print("bitcount[" + i + "]" + bitCount[i]);
            }

            for (int i = 0; i < NUM_BITS; i++) {
                if (bitCount[i] > NUM_LINES / 2) { // if more than half of bit i was 1,
                    gamma += 1 << NUM_BITS - 1 - i; // put that bit to 1
                } else {
                    epsilon += 1 << NUM_BITS - 1 - i;
                    // since epsilon is the complement of gamma, at least for the right NUM_BITS bits,
                    // rather than "calculate it" like here, we can also just take the complement of gamma
                    // at the end of this loop, but only the complement of the last NUM_BITS bits, see epsilon2
                }
            }
            print("gamma    = " + gamma   + ", bits: " + getBits(gamma, NUM_BITS));
            print("epsilon  = " + epsilon + ", bits: " + getBits(epsilon, NUM_BITS));
            int epsilon2 = gamma ^ (-1 >>> (32 - NUM_BITS));
            // -1 in binary is all 1. bitshift right 32 - NUM_BITS (>>), but fill with zeroes (>>>),
            // so -1 >>> (32 - NUM_BITS) is just 1s for the right NUM_BITS bits. Then take the bitwise xor ^
            print("epsilon2 = " + epsilon2 + ", bits: " + getBits(epsilon2, NUM_BITS)); // should equal epsilon

            print("gamma * epsilon = " + gamma * epsilon);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.out.println("whoops");
            System.exit(-1);
        }
    }

    private static String getBits(int number, int padding) {
        return String.format("%" + padding + "s", Integer.toBinaryString(number)).replaceAll(" ", "0");
    }
}
