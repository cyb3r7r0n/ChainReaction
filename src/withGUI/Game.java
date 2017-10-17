package withGUI;

import GUI.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;
import java.util.EmptyStackException;
import java.util.Stack;

public class Game {

    private int numberOfPlayers;
    private int numberOfRows;
    private int numberOfColumns;
    private int currentPlayer;
    private Player[] players;
    private int turns;
    transient private Stage stage;
    transient private TileGrid tileGrid;
    private Stack<GameState> states;
    private boolean isComplete;

    public Stack<GameState> getStates() {
        return states;
    }

    public void saveGame() {
        ObjectOutputStream oos = null;
        try {
            File file = new File("game");
            if(!file.exists()) {
                file.createNewFile();
            }
            oos = new ObjectOutputStream(new FileOutputStream("game"));
            oos.writeObject(new GameState(this));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(oos!=null)
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    public boolean isComplete() {
        return isComplete;
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public int getTurns() {
        return turns;
    }

    public Player[] getPlayers() {
        return players;
    }

    public TileGrid getTileGrid() {
        return tileGrid;
    }

    public void choseNextPlayer() {
        currentPlayer = (currentPlayer+1)%numberOfPlayers;
        if(players[currentPlayer].hasLost()) {
            choseNextPlayer();
        } else {
            Board.currentColor = players[currentPlayer].getColor();
            LineGrid.setColour(Board.currentColor);
        }
    }

    public void takeTurn() {
        turns++;
        if(turns > numberOfPlayers) {
            if (tileGrid.hasLost(players[currentPlayer].getColor())) {
                players[currentPlayer].lost();
            }
            int playerWon = playerWon();
            if (playerWon != -1) {
                this.isComplete = true;
                saveGame();
                AlertBox alertBox = new AlertBox();
                alertBox.display("Game over", players[playerWon].getColor());
                if (alertBox.startNewGame()) {
                    this.startNewGame();
                    this.startGame();
                } else {
                    this.stage.close();
                    Board.showMenu();
                }
            } else {
                this.isComplete = false;
            }
        }
        choseNextPlayer();
        this.states.push(new GameState(this));
        saveGame();
    }

    public int playerWon() {
        if(turns>numberOfPlayers) {
            int numberOfPlayersStillPlaying = 0;
            int latestPlayer = -1;
            for (int i = 0; i < numberOfPlayers; i++) {
                if (tileGrid.hasLost(players[i].getColor())) {
                    players[i].lost();
                } else {
                    numberOfPlayersStillPlaying++;
                    latestPlayer = i;
                }
            }
            if (numberOfPlayersStillPlaying == 1) {
                return latestPlayer;
            }
        }
        return -1;
    }

    public void startGame() {
        this.currentPlayer = 0;
        LineGrid.setColour(players[currentPlayer].getColor());
        Board.currentColor = players[currentPlayer].getColor();
    }

    public void startNewGame() {
        Game game = new Game(this.numberOfPlayers, this.numberOfRows, this.numberOfColumns, this.stage, false);
        game.startGame();
    }

    public void showPage(boolean createNewPage) {
        if(createNewPage) {
            this.stage = new Stage();
        }
        this.stage.setResizable(false);
        stage.setTitle("Game Page");
        this.tileGrid = new TileGrid(numberOfRows, numberOfColumns, this);

        Button button = new Button("UNDO");
        button.setMinWidth(100);
        button.setTranslateX((numberOfColumns)*100+50);
        button.setTranslateY(50);
        button.setOnAction(e -> undo());

        Group group = new Group();
        Scene scene = new Scene(group, (numberOfColumns+2)*100, (numberOfRows+2)*100);
        group.getChildren().addAll(tileGrid.getGrid(), LineGrid.getGrid(numberOfColumns, numberOfRows), button);
        scene.setFill(Color.GRAY);

        this.stage.setScene(scene);
        this.stage.show();
//        this.stage.setFullScreen(true);
//        this.stage.setMaxHeight(1000);
//        this.stage.setMaxWidth(1000);
    }

    public Game(int numberOfPlayers, int numberOfRows, int numberOfColumns, Stage stage, boolean createNewStage) {
        this.numberOfPlayers = numberOfPlayers;
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        this.players = new Player[numberOfPlayers];
        this.currentPlayer = 0;
        this.turns = 0;
        this.stage = stage;
        this.states = new Stack<>();

        Color[] colors = SettingsPage.getColours();
        for(int i=0; i<this.numberOfPlayers; i++) {
            players[i] = new Player(i, colors[i]);
        }
        this.isComplete = false;
        showPage(createNewStage);
        this.states.push(new GameState(this));
    }

    public void convertToGame(GameState gameState) {
        this.numberOfPlayers = gameState.getNumberOfPlayers();
        this.numberOfRows = gameState.getNumberOfRows();
        this.numberOfColumns = gameState.getNumberOfColumns();
        this.players = gameState.getPlayers();
        this.turns = gameState.getTurns();
        this.currentPlayer = gameState.getCurrentPlayer();
        this.tileGrid.setTiles(gameState, this);
        Board.currentColor = players[currentPlayer].getColor();
        this.states = gameState.getStates();
        this.isComplete = gameState.isGameComplete();

        Group group = new Group();
        Scene scene = new Scene(group, (numberOfColumns+2)*100, (numberOfRows+2)*100);

        Button button = new Button("UNDO");
        button.setMinWidth(100);
        button.setTranslateX((numberOfColumns)*100+50);
        button.setTranslateY(50);
        button.setOnAction(e -> undo());


        group.getChildren().addAll(this.tileGrid.getGrid(), LineGrid.getGrid(numberOfColumns, numberOfRows), button);
        LineGrid.setColour(Board.currentColor);

        scene.setFill(Color.GRAY);

        this.stage.setScene(scene);
        this.stage.show();
    }

    public void loadGame(GameState gameState) {
        this.convertToGame(gameState);
    }

    public void undo() {
        try {
            GameState previousState = states.pop();
            GameState currentState = states.pop();
            if(this.states.size()==1) {
                this.states = new Stack<>();
            }
            this.states.push(previousState);
            this.convertToGame(currentState);
            saveGame();
        } catch (EmptyStackException e) {
            this.states = new Stack<>();
            startNewGame();
        }
    }
}
