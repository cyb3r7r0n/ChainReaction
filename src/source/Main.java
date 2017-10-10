package source;

import exceptions.MaxPlayersExceeded;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Scanner;

public class Main {
    private static int numberOfPlayers = 0;
    private static Game lastGame;
    private static SettingsPage settingsPage;
    private static boolean resumeButton;
    private static int numberOfRows = 0;
    private static int numberOfColumns = 0;

    public static Scanner input;

    private static void print(String s) {
        System.out.println(s);
    }

    public static void loadMainPage() throws MaxPlayersExceeded {
        if(numberOfPlayers==0) {
            print("Select the number of players");
            numberOfPlayers = input.nextInt();
            MaxPlayersExceeded.check(numberOfPlayers, 8);
        }
        if(numberOfColumns==0 && numberOfRows==0) {
            print("Enter the grid size");
            System.out.print("Number of Rows: ");
            numberOfRows = input.nextInt();
            System.out.print("Number of Column: ");
            numberOfColumns = input.nextInt();
        }
       int menui = 1;
       print(menui+". Start new Game?");
       menui++;
       if(resumeButton) {
           print(menui+". Resume last Game?");
           menui++;
       }
       print(menui+". Open Settings?");

       int choice = input.nextInt();
       if(choice <= menui) {
           if(choice == 1) {
               Grid grid = new Grid(numberOfRows, numberOfColumns);
               Game game = new Game(grid, numberOfPlayers, settingsPage);
               game.startGame();
           }
           else if(choice==2 && resumeButton) {
               lastGame.resumeGame();
           }
           else {
               settingsPage.showPage();
           }
       }

    }

    private static boolean lastGameInComplete() throws IOException {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream("game"));
            lastGame = (Game) ois.readObject();
            return lastGame.isGameInComplete();
        } catch (IOException | ClassNotFoundException e) {
            return false;
        } finally {
            if(ois != null) {
                ois.close();
            }
        }
    }

    private static void loadPreviousSettings() throws IOException {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream("settings"));
            settingsPage = (SettingsPage) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            settingsPage = new SettingsPage();
        } finally {
            if(ois != null) {
                ois.close();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        numberOfPlayers = 0;
        numberOfRows = 0;
        numberOfColumns = 0;
        input = new Scanner(System.in);
        try {
            resumeButton = lastGameInComplete();
        } catch (IOException e) {
            System.out.print("resume button can't be set");
        }
        loadPreviousSettings();
        try {
            loadMainPage();
        } catch (MaxPlayersExceeded maxPlayersExceeded) {
            print(maxPlayersExceeded.getMessage());
        }
    }
}
