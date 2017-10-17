package exceptions;

import javafx.scene.paint.Color;
import source.Cell;

public class IllegalMoveException extends Throwable {
    private Cell cell;
    private Color colour;

    public IllegalMoveException(Cell cell, Color colour) {
        this.colour = colour;
        this.cell = cell;
    }

    @Override
    public String toString() {
        return "Illegal move at "+cell+" for color "+colour;
    }
}
