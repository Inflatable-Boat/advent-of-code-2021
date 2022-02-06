package a04;

/**
 * Container with convenience methods for bingo boards.
 * 
 * Mark each number when a new number is called with {@link #mark},
 * Check if it has bingo with {@link #hasBingo}.
 */
public class BingoBoard {

    final private int GRID_SIZE;
    int[][] grid;
    boolean[][] marked;

    BingoBoard(final int GRID_SIZE) {
        this.GRID_SIZE = GRID_SIZE;
        grid = new int[GRID_SIZE][GRID_SIZE];
        marked = new boolean[GRID_SIZE][GRID_SIZE];
    }

    boolean hasBingo() {

        boolean bingo = false;

        for (int i = 0; i < GRID_SIZE && !bingo; i++) {
            // are all numbers in one row marked?
            boolean rowSoFarMarked = true;
            // are all numbers in one column marked?
            boolean columnSoFarMarked = true;

            for (int j = 0; j < GRID_SIZE; j++) {
                if (!marked[i][j]) {
                    rowSoFarMarked = false;
                }
                if (!marked[j][i]) {
                    columnSoFarMarked = false;
                }
            }
            // if either a complete column or row is marked, we have a winner!!!
            bingo = rowSoFarMarked || columnSoFarMarked;
        }
        return bingo;
    }

    void mark(int number) {

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (number == grid[i][j]) {
                    marked[i][j] = true;
                    return; // numbers on a bingo board are unique
                }
            }
        }
    }

    int sumOfUnmarkedNumbers() {

        int sum = 0;

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (!marked[i][j]) {
                    sum += grid[i][j];
                }
            }
        }
        return sum;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                sb.append(grid[i][j]).append(' ');
                if (grid[i][j] < 10) {
                    sb.append(' ');
                }
            }
            sb.append('\n');
        }
        return sb.toString().stripTrailing();
    }
}