package source;

import exceptions.IllegalMoveException;

import java.io.Serializable;

public class Grid implements Serializable, Colours, Cloneable {
    private final int numberOfRows;
    private final int numberOfColumns;
    private Cell[][] grid;
    private static final long serialVersionUID = 2L;
    public static boolean[][] reached;
    public static int sum;


    public Grid(int numberOfRows, int numberOfColumns) {
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        this.grid = new Cell[numberOfRows][numberOfColumns];
        for(int i=0; i<numberOfRows; i++)
            for (int j=0; j<numberOfColumns; j++) {
                grid[i][j] = new Cell(i, j, this);
            }
    }

    public Grid(Grid g) {
        this.numberOfColumns = g.numberOfColumns;
        this.numberOfRows = g.numberOfRows;
        this.grid = new Cell[numberOfRows][numberOfColumns];
        for(int i=0; i<numberOfRows; i++)
            for (int j=0; j<numberOfColumns; j++) {
                this.grid[i][j] = new Cell(g.grid[i][j]);
            }
    }

    public void showGrid() {
        System.out.println();
        for(int i=0; i<numberOfRows; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                System.out.print(grid[i][j] + "\t\t");
            }
            System.out.println();
        }
        System.out.println();
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public void addOrb(int i, int j, String colour) {
        if (i >= 0 && i < this.numberOfRows && j >= 0 && j < this.numberOfColumns) {
            if(sum!=numberOfColumns*numberOfRows) {
                boolean burst = this.grid[i][j].addOrb(colour);
                if (burst) {
                    if (i + 1 < this.numberOfRows)
                        addOrb(i + 1, j, colour);
                    if (i - 1 >= 0)
                        addOrb(i - 1, j, colour);
                    if (j + 1 < this.numberOfColumns)
                        addOrb(i, j + 1, colour);
                    if (j - 1 >= 0)
                        addOrb(i, j - 1, colour);
                }
                if(!reached[i][j]) {
                    reached[i][j] = true;
                    sum+=1;
                }
            }
        }
    }

    public void isLegal(int i, int j, String colour) throws IllegalMoveException {
        if(!colour.equals(grid[i][j].getColour()) && grid[i][j].getColour()!=null) {
            throw new IllegalMoveException(grid[i][j], colour);
        }
    }

    public boolean gameComplete(String colour, int turns) {
        if(turns<=2)
            return false;
        for(int i=0; i<numberOfRows; i++) {
            for (int j=0; j<numberOfColumns; j++) {
                if(!colour.equals(grid[i][j].getColour())) {
                    if(grid[i][j].getColour()!=null)
                        return false;
                }
            }
        }
        return true;
    }

    public boolean hasLost(Player player) {
        for (int i=0; i<numberOfRows; i++) {
            for (int j=0; j<numberOfRows; j++) {
                if(grid[i][j].getColour()!=null && grid[i][j].getColour().equals(player.getColour()))
                    return false;
            }
        }
        return true;
    }

}
