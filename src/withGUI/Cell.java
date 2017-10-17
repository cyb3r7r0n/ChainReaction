package withGUI;

import javafx.scene.paint.Color;

import java.io.Serializable;

public class Cell implements Serializable {
    private int numberOfOrbs;
    private int x;
    private int y;
    private String color;
    private static final long serialVersionUID = 5L;

    public Cell(int numberOfOrbs, int x, int y, Color color) {
        this.numberOfOrbs = numberOfOrbs;
        this.x = x;
        this.y = y;
        if(color!=null)
            this.color = color.toString();
        else
            this.color = null;
    }

    public int getNumberOfOrbs() {
        return numberOfOrbs;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setColor(Color color) {
        this.color = color.toString();
    }

    public Color getColor() {
        if(this.color!=null) {
            Color color = Color.valueOf(this.color);
            return color;
        }
        return null;
    }
}
