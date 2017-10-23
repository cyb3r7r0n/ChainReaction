package GUI;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import withGUI.Game;
import withGUI.GameState;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;


public class Board extends Application {
    private static GameState lastGame;
    private static final int height = 500, width=400;
    private static double scale;
    private static Stage stage;
    private static int MAXROWS, MAXCOLUMN, MAXPLAYERS;

    private static int numberPlayers, numberRows, numberColumns;

    public static int getScale() {
        return (int) scale;
    }

    public static Color currentColor;

    public static void main(String[] args) {
        launch(args);
    }

    private static boolean lastGameComplete() {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream("game"));
            lastGame = (GameState) ois.readObject();
            return lastGame.isGameComplete();
        } catch (IOException | ClassNotFoundException e) {
            return false;
        } finally {
            if(ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static VBox menuGroup(Stage primaryStage) {
        Label numPlayers = new Label("Number Of Players");
        Font font = new Font(20);
        numPlayers.setFont(font);

        Label numRows = new Label("Number Of Rows");
        numRows.setFont(font);

        Label numColumns = new Label("Number Of Columns");
        numColumns.setFont(font);

        ChoiceBox<Integer> players = new ChoiceBox<>();
        for(int i=2; i<=MAXPLAYERS; i++) {
            players.getItems().add(i);
        }
        players.setValue(numberPlayers);

        ChoiceBox<Integer> rows = new ChoiceBox<>();
        for(int i=2; i<=MAXROWS; i++) {
            rows.getItems().add(i);
        }
        rows.setValue(numberRows);

        ChoiceBox<Integer> columns = new ChoiceBox<>();
        for(int i=2; i<=MAXCOLUMN; i++) {
            columns.getItems().add(i);
        }
        columns.setValue(numberColumns);

        HBox selectNumPlayers = new HBox(20);
        selectNumPlayers.setAlignment(Pos.CENTER);
        selectNumPlayers.setPrefWidth(width);
        selectNumPlayers.getChildren().addAll(numPlayers, players);

        HBox selectNumRows = new HBox(20);
        selectNumRows.setPrefWidth(width);
        selectNumRows.setAlignment(Pos.CENTER);
        selectNumRows.getChildren().addAll(numRows, rows);

        HBox selectNumColumns  = new HBox(20);
        selectNumColumns.setAlignment(Pos.CENTER);
        selectNumColumns.setPrefWidth(width);
        selectNumColumns.getChildren().addAll(numColumns, columns);

        VBox menu = new VBox(20);
        menu.getChildren().addAll(selectNumPlayers, selectNumRows, selectNumColumns);


        Button newGame = new Button("New Game");
        newGame.setOnAction( e -> {

            numberPlayers = players.getValue();
            numberRows = rows.getValue();
            numberColumns = columns.getValue();
            if(numberRows<=10 && numberColumns<=20) {
                scale = 1.0;
            } else {
                scale = 1 / Math.max((double)(numberRows/10), (double)(numberColumns/20));
            }
            Game game = new Game(numberPlayers, numberRows, numberColumns, primaryStage, true);
            System.out.println("New Game Clicked");
            primaryStage.close();
            game.startGame();
        });

        Button settings = new Button("Settings");
        settings.setOnAction( e -> {
            SettingsPage.showPage(stage);
        });

        Button previousGame = new Button("Last Game");
        previousGame.setOnAction( e -> {
            System.out.println("Previous Game clicked");
            primaryStage.close();
            numberPlayers = lastGame.getNumberOfPlayers();
            numberRows = lastGame.getNumberOfRows();
            numberColumns = lastGame.getNumberOfColumns();
            Game game = new Game(numberPlayers, numberRows, numberColumns, primaryStage, true);
            game.loadGame(lastGame);
        });

        menu.getChildren().addAll(newGame, settings);

        if(!lastGameComplete()) {
            menu.getChildren().addAll(previousGame);
        }

        menu.setAlignment(Pos.CENTER);
        menu.setPrefWidth(width);
        return menu;
    }

    public static void showMenu() {
        VBox layout = menuGroup(stage);

        Scene scene = new Scene(layout, width, height);

        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void start(Stage primaryStage) {
        numberPlayers = 2;
        numberRows = 9;
        numberColumns = 6;
        MAXROWS = 9;
        MAXCOLUMN = 17;
        MAXPLAYERS = 8;
        stage = primaryStage;
        SettingsPage.loadSetting();
        showMenu();
    }
}
