package boat;

public class Helper {

    public static void print(Object s) {
        System.out.println(s);
    }

    public static void print(int s) {
        System.out.println(s);
    }

    public static void print(double s) {
        System.out.println(s);
    }

    public static void printTimeSince(final long startTime) {
        System.out.println("took " + (System.nanoTime() - startTime) / 10e6 + "ms");
    }
}