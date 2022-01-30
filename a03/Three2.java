package a03;

import static boat.Helper.print;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Three2 {
    
    public static void main(String[] args) {

        List<String> lines;
        try {
            int o2 = -1, co2 = -1;
            lines = Files.readAllLines(Paths.get("a03/input.txt"));
            final int NUM_LINES = lines.size();
            final int NUM_BITS = lines.get(0).length();

            print("number of lines: " + NUM_LINES);
            print("number of bits per line: " + NUM_BITS);

            List<String> oxygenCandidates = new ArrayList<>(NUM_LINES);
            oxygenCandidates.addAll(lines);
            List<String> co2Candidates = new ArrayList<>(NUM_LINES);
            co2Candidates.addAll(lines);

            findRating(oxygenCandidates, false);
            findRating(co2Candidates, true);

            o2 = Integer.parseInt(oxygenCandidates.get(0), 2);
            co2 = Integer.parseInt(co2Candidates.get(0), 2);

            print("o2  = " + o2  + ", bits: " + getBits(o2, NUM_BITS));
            print("co2 = " + co2 + ", bits: " + getBits(co2, NUM_BITS));

            print("o2 * co2 = " + o2 * co2);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.out.println("whoops");
            System.exit(-1);
        }
    }

    private static void findRating(List<String> candidates, boolean doRemoveMostCommon) {
        int bitCriterionIndex = 0;
        while (candidates.size() > 1) {
            List<String> ones = new ArrayList<>(candidates.size());
            List<String> zeroes = new ArrayList<>(candidates.size());

            for (String s : candidates) {
                if (s.charAt(bitCriterionIndex) == '1') {
                    ones.add(s);
                } else {
                    zeroes.add(s);
                }
            }

            if (ones.size() * 2 > candidates.size()) {
                // ones are the most common value
                candidates.removeAll(doRemoveMostCommon ? ones : zeroes);

            } else if (ones.size() * 2 < candidates.size()) {
                // zeroes are the most common value
                candidates.removeAll(doRemoveMostCommon ? zeroes : ones);
            } else {
                // equal amount of ones and zeroes
                // if oxygen (!doRemoveMostCommon), keep 1, so remove zeroes
                // if co2 (== doRemoveMostCommon), keep 0, so remove ones.
                candidates.removeAll(doRemoveMostCommon ? ones : zeroes);
            }
            bitCriterionIndex++;
        }
    }

        private static String getBits(int number, int padding) {
        return String.format("%" + padding + "s", Integer.toBinaryString(number)).replaceAll(" ", "0");
    }
}
