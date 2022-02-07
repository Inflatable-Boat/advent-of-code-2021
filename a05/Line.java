package a05;

import java.util.ArrayList;
import java.util.List;

public class Line {

    Point start, end;

    Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    List<Point> getPoints() {

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
            System.out.println("non-axis-aligned line");
            // TODO: part 2 probably :)
        }

        return points;
    }
}