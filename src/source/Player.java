package source;

import java.io.Serializable;

public class Player implements Serializable{
    private final String colour;
    private static final long serialVersionUID = 4L;


    public Player(String colour) {
        this.colour = colour;
    }

    public String getColour() {
        return colour;
    }

    @Override
    public String toString() {
        return this.colour;
    }
}
