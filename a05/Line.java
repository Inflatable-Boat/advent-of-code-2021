package a05;

import java.util.ArrayList;
import java.util.List;

public class Line {

    Point start, end;

    Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    List<Point> getPoints(final boolean countDiagonalLines) {

        List<Point> points = new ArrayList<>();

        if (start.y == end.y) {
            // horizontal line
            int x = Math.min(start.x, end.x);
            int x2 = Math.max(start.x, end.x);

            while (x <= x2) {
                points.add(new Point(x++, start.y));
            }
        } else if (start.x == end.x) {
            // vertical line
            int y = Math.min(start.y, end.y);
            int y2 = Math.max(start.y, end.y);

            while (y <= y2) {
                points.add(new Point(start.x, y++));
            }
        } else {
            // assuming diagonal lines as the only other option
            if (countDiagonalLines) {
                addPointsInDiagonalLine(points);
            } else {
                System.out.println("ignoring diagonal line");
            }
        }

        return points;
    }

    private void addPointsInDiagonalLine(List<Point> points) {
        // start from the point with smallest x, or "most to the left"
        final boolean isStartMoreLeft = start.x < end.x;

        final int x1 = isStartMoreLeft ? start.x : end.x;
        final int y1 = isStartMoreLeft ? start.y : end.y;
        final int x2 = isStartMoreLeft ? end.x : start.x;
        final int y2 = isStartMoreLeft ? end.y : start.y;

        // does the line go up? Invest now!
        final int Δy = y2 > y1 ? 1 : -1;

        int y = y1;
        for (int x = x1; x <= x2; x++, y += Δy) {
            points.add(new Point(x, y));
        }

        if (y != y2 + Δy) { // + Δy because of the final overshoot in the for loop above
            throw new IllegalStateException("only diagonal lines expected");
        }
    }
}