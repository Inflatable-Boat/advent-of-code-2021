package a02;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import boat.Helper;

public class Two {

    public static void main(String[] args) {

        List<String> lines;
        Position position = new Position();
        try {
            lines = Files.readAllLines(Paths.get("a02/input.txt"));

            for (String line : lines) {
                parseLine(line, position);
            }

            Helper.print("horizontal pos: " + position.x);
            Helper.print("depth: " + position.y);
            Helper.print("horizontal pos * depth: " + position.x * position.y);

        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.out.println("whoops");
            System.exit(-1);
        }
    }

    private static void parseLine(String line, Position position) {

        String[] directionAndNumber = line.split(" ");
        Direction direction = Direction.valueOf(directionAndNumber[0]);
        int number = Integer.parseInt(directionAndNumber[1]);

        switch (direction) {
            case forward:
                position.x += number;
                break;
            case up:
                position.y -= number; // up decreases depth
                break;
            case down:
                position.y += number; // down increases depth
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

    private static class Position { 
        int x;
        int y;
    }
}
