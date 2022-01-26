package a02;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import boat.Helper;

public class Two2 {

    public static void main(String[] args) {

        List<String> lines;
        State state = new State();
        try {
            lines = Files.readAllLines(Paths.get("a02/input.txt"));

            for (String line : lines) {
                parseLine(line, state);
            }

            Helper.print("horizontal pos: " + state.x);
            Helper.print("depth: " + state.y);
            Helper.print("horizontal pos * depth: " + state.x * state.y);

        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.out.println("whoops");
            System.exit(-1);
        }
    }

    private static void parseLine(String line, State state) {

        String[] directionAndNumber = line.split(" ");
        Direction direction = Direction.valueOf(directionAndNumber[0]);
        int number = Integer.parseInt(directionAndNumber[1]);

        switch (direction) {
            case forward:
                state.x += number;
                state.y += number * state.aim;
                break;
            case up:
                state.aim -= number;
                break;
            case down:
                state.aim += number;
                break;
            default:
                break;
        }
    }

    private enum Direction {
        forward,
        up,
        down;
    }

    private static class State { 
        int x;
        int y;
        int aim;
    }
}
