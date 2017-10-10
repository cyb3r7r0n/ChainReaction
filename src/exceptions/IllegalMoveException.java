package exceptions;

import source.Cell;

public class IllegalMoveException extends Throwable {
    private Cell cell;
    private String colour;

    public IllegalMoveException(Cell cell, String colour) {
        this.colour = colour;
        this.cell = cell;
    }

    @Override
    public String toString() {
        return "Illegal move at "+cell+" for color "+colour;
    }
}
