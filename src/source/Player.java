package source;

import java.io.Serializable;

public class Player implements Serializable{
    private final String colour;
    private static final long serialVersionUID = 4L;
    private boolean stillPlaying;


    public Player(String colour) {
        this.colour = colour;
        this.stillPlaying = true;
    }

    public String getColour() {
        return colour;
    }

    @Override
    public String toString() {
        return this.colour;
    }

    public boolean hasLost() {
        return !this.stillPlaying;
    }

    public void lost() {
        this.stillPlaying = false;
    }
}
