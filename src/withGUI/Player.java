package withGUI;

import javafx.scene.paint.Color;

import java.io.Serializable;

public class Player implements Serializable{

    private String color;
    private final int playerNumber;
    private boolean stillPlaying;
    private static final long serialVersionUID = 4L;

    public Player(Player player) {
        this.playerNumber = player.playerNumber;
        this.color = player.color;
        this.stillPlaying = player.stillPlaying;
    }

    public Player(int playerNumber, Color color) {
        this.playerNumber = playerNumber;
        if(color!=null)
            this.color = color.toString();
        else
            this.color = null;
        this.stillPlaying = true;
    }

    public boolean hasLost() {
        return !stillPlaying;
    }

    public void lost() {
        this.stillPlaying = false;
    }

    public Color getColor() {
        if (this.color!=null)
            return Color.valueOf(color);
        return null;
    }
}
