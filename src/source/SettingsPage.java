package source;

import exceptions.MaxPlayersExceeded;
import javafx.scene.paint.Color;

import java.io.*;

public class SettingsPage implements Colours, Serializable {
    private Color[] colours;
    private static final long serialVersionUID = 5L;

    public SettingsPage() {
        colours = new Color[8];
        colours[0] = Color.RED;
        colours[1] = Color.BLUE;
        colours[2] = Color.YELLOW;
        colours[3] = Color.GREEN;
        colours[4] = Color.PINK;
        colours[5] = Color.ORANGE;
        colours[6] = Color.WHITE;
        colours[7] = Color.TEAL;
        saveSettings();
    }

    public void setColours(Color[] c) {
        colours = c;
    }

    private void saveSettings() {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream("settings"));
            oos.writeObject(this);
        } catch (FileNotFoundException e) {
            System.out.println("Settings can't be saved");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    System.out.println("Settings can't be saved");
                }
            }
        }
    }

    public void showCurrentSetting() {
        System.out.println("Player 1 -> " + colours[0]);
        System.out.println("Player 2 -> " + colours[1]);
        System.out.println("Player 3 -> " + colours[2]);
        System.out.println("Player 4 -> " + colours[3]);
        System.out.println("Player 5 -> " + colours[4]);
        System.out.println("Player 6 -> " + colours[5]);
        System.out.println("Player 7 -> " + colours[6]);
        System.out.println("Player 8 -> " + colours[7]);
    }

    public void showPage() throws MaxPlayersExceeded {
        showCurrentSetting();
        boolean exit = false;
        while(!exit) {
            System.out.println("exit?(y/n)");
            String EXIT = Main.input.next();
            if(EXIT.toLowerCase().equals("y")) {
                exit = true;
                continue;
            }
            System.out.println("Choose the player");
            int playerNumber = Main.input.nextInt();
            if(playerNumber<=8 && playerNumber>0) {
                System.out.println("Select the colour");
                Color colour = red;
                if(colour.equals(red) || colour.equals(blue) || colour.equals(yellow) || colour.equals(green) || colour.equals(orange) || colour.equals(pink) || colour.equals(white) || colour.equals(teal)) {
                    selectColor(colour, playerNumber-1);
                    saveSettings();
                    showCurrentSetting();
                } else {
                    System.out.println(colour + " is not a colour. try again");
                }
            } else {
                System.out.println("select a valid player, try again");
            }
        }
        System.out.println("Returning to MainPage");
        Main.loadMainPage();
    }

    public Color[] getColours() {
        return colours;
    }

    public void selectColor(Color colour, int j) {
        for (int i=0; i<8; i++) {
            if(colours[i].equals(colour)) {
                colours[i] = colours[j];
                colours[j] = colour;
            }
        }
    }
}
