package withGUI;

import java.io.Serializable;
import java.util.Stack;

public class GameState implements Serializable {
    private int numberOfPlayers;
    private int numberOfRows;
    private int numberOfColumns;
    private int currentPlayer;
    private Player[] players;
    private int turns;
    private Cell[][] grid;
    private boolean gameComplete;
    private static final long serialVersionUID = 3L;
    private Stack<GameState> states;


    public GameState(Game game) {
        this.numberOfPlayers = game.getNumberOfPlayers();
        this.numberOfRows = game.getNumberOfRows();
        this.numberOfColumns = game.getNumberOfColumns();
        this.currentPlayer = game.getCurrentPlayer();
        this.players = new Player[this.numberOfPlayers];
        for(int i=0; i<numberOfPlayers; i++) {
            this.players[i] = new Player(game.getPlayers()[i]);
        }
        this.turns = game.getTurns();
        this.grid = game.getTileGrid().getGrid2();
        this.gameComplete = game.isComplete();
        this.states = game.getStates();
    }

    public Stack<GameState> getStates() {
        return states;
    }

    public boolean isGameComplete() {
        return gameComplete;
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public Player[] getPlayers() {
        return players;
    }

    public int getTurns() {
        return turns;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

}
