package source;

import javafx.scene.paint.Color;

import java.io.Serializable;

public class Player implements Serializable{
    private final Color colour;
    private static final long serialVersionUID = 4L;
    private boolean stillPlaying;


    public Player(Color colour) {
        this.colour = colour;
        this.stillPlaying = true;
    }

    public Color getColour() {
        return colour;
    }

    @Override
    public String toString() {
        return this.colour.toString();
    }

    public boolean hasLost() {
        return !this.stillPlaying;
    }

    public void lost() {
        this.stillPlaying = false;
    }
}
