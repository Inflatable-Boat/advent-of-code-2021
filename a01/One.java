package a01;

import boat.Helper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class One {

    public static void main(String[] args) {

        List<String> lines;
        int count = 0, prev = -1, next = 0;
        try {
            lines = Files.readAllLines(Paths.get("a01/input.txt"));

            for (String line : lines) {
                if (prev == -1) { // yeah yeah shoot me
                    prev = Integer.parseInt(line);
                    continue;
                }
                next = Integer.parseInt(line);
                if (prev < next) {
                    count++;
                }
                prev = next;
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.out.println("whoops");
            System.exit(-1);
        }

        Helper.print(count);
    }
}
