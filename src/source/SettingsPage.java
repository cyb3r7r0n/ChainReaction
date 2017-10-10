package source;

import exceptions.MaxPlayersExceeded;

import java.io.Serializable;

public class SettingsPage implements Colours, Serializable {
    private static String player1Colour;
    private static String player2Colour;
    private static String player3Colour;
    private static String player4Colour;
    private static String player5Colour;
    private static String player6Colour;
    private static String player7Colour;
    private static String player8Colour;
    private static final long serialVersionUID = 5L;

    public SettingsPage() {
        player1Colour = red;
        player2Colour = blue;
        player3Colour = yellow;
        player4Colour = green;
        player5Colour = pink;
        player6Colour = orange;
        player7Colour = white;
        player8Colour = teal;
    }

    public void showCurrentSetting() {
        System.out.println("Player 1 -> " + player1Colour);
        System.out.println("Player 2 -> " + player2Colour);
        System.out.println("Player 3 -> " + player3Colour);
        System.out.println("Player 4 -> " + player4Colour);
        System.out.println("Player 5 -> " + player5Colour);
        System.out.println("Player 6 -> " + player6Colour);
        System.out.println("Player 7 -> " + player7Colour);
        System.out.println("Player 8 -> " + player8Colour);
    }

    public void showPage() throws MaxPlayersExceeded {
        showCurrentSetting();
        System.out.println("Returning to MainPage");
        Main.loadMainPage();
    }

    public static String[] getColours() {
        String[] colours = new String[8];
        colours[0] = player1Colour;
        colours[1] = player2Colour;
        colours[2] = player3Colour;
        colours[3] = player4Colour;
        colours[4] = player5Colour;
        colours[5] = player6Colour;
        colours[6] = player7Colour;
        colours[7] = player8Colour;
        return colours;
    }
}
