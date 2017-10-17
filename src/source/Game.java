package source;

import exceptions.IllegalMoveException;
import javafx.scene.paint.Color;

import java.io.*;
import java.util.Stack;

public class Game implements Serializable{
    private boolean gameInComplete;
    private final int numberOfPlayers;
    private Grid grid;
    private Player[] players;
    private int numberOfTurns;
    private int nextPlayer;
    private static final long serialVersionUID = 1L;
    private Stack<Game> states;
    private Player currentPlayer;
    private boolean lastMoveWasAUndo;

    public Game(Grid grid, int numberOfPlayers, SettingsPage settingsPage) {
        this.grid = grid;
        this.numberOfPlayers = numberOfPlayers;
        this.players = new Player[numberOfPlayers];
        Color[] colours = settingsPage.getColours();
        for(int i=0; i<numberOfPlayers; i++) {
            this.players[i] = new Player(colours[i]);
        }
        this.numberOfTurns = 0;
        this.gameInComplete = true;
        this.states = new Stack<>();
        this.lastMoveWasAUndo = false;
    }

    public Game( Game game) {
        this.grid = new Grid(game.grid);
        this.gameInComplete = game.gameInComplete;
        this.numberOfPlayers = game.numberOfPlayers;
        this.players = game.players;
        this.numberOfTurns = game.numberOfTurns;
        this.nextPlayer = game.nextPlayer;
        this.states = game.states;
        this.currentPlayer = game.currentPlayer;
    }

    public boolean isGameInComplete() {
        return gameInComplete;
    }

    private void changeState( Game game ) {
        this.grid = game.grid;
        this.gameInComplete = game.gameInComplete;
        this.players = game.players;
        this.numberOfTurns = game.numberOfTurns;
        this.nextPlayer = game.nextPlayer;
        this.states = game.states;
        this.currentPlayer = game.currentPlayer;
    }

    public void startGame() {
        this.states.push(new Game(this));
        while (playerWon()==null) {
            for(int i=0; i<numberOfPlayers; i++) {
//                System.out.println(players[i]);
                if(players[i].hasLost() && numberOfTurns>numberOfPlayers) {
                    saveGame();
                    continue;
                }
                if(numberOfTurns>numberOfPlayers && grid.hasLost(players[i])) {
                    players[i].lost();
                    saveGame();
                    if(players[i].hasLost()) {
                        saveGame();
                        continue;
                    }
                }
                if(this.lastMoveWasAUndo) {
                    i = (numberOfPlayers+i-2)%numberOfPlayers;
                    this.currentPlayer = players[i];
                    takeTurn();
                }
                else {
                    this.currentPlayer = players[i];
                    takeTurn();
                }
                saveGame();
                if(playerWon()!=null)
                    break;
                this.nextPlayer = (i+1)%numberOfPlayers;
            }
        }
        this.gameInComplete = false;
        saveGame();
        grid.showGrid();
        System.out.println(playerWon()+" won the game");
    }

    private void undoMove() {
        Game temp = this.states.pop();
        Game previousState = this.states.pop();
        this.states.push(temp);
        changeState(previousState);
        saveGame();
    }

    private void takeTurn() {
        boolean moved = false;

        while (!moved) {
            grid.showGrid();
            boolean undo = false;
            System.out.println("Player " + currentPlayer.getColour() + "'s turn");
            if(numberOfTurns>0 && !this.lastMoveWasAUndo) {
                System.out.print("undo(y/n): ");
                String choice = Main.input.next();
                if (choice.equals("y")) {
                    this.lastMoveWasAUndo = true;
                    undo = true;
                    undoMove();
                } else {
                    this.lastMoveWasAUndo = false;
                    undo = false;
                }
                moved = true;
            }
            if(!undo) {
                saveGame();
                this.lastMoveWasAUndo = false;
                System.out.print("Enter x: ");
                int x = Main.input.nextInt();
                System.out.print("Enter y: ");
                int y = Main.input.nextInt();
                try {
                    grid.isLegal(x, y, currentPlayer.getColour());
                    System.out.println("check");
                    Grid.reached = new boolean[grid.getNumberOfRows()][grid.getNumberOfColumns()];
                    Grid.sum = 0;
                    grid.addOrb(x, y, currentPlayer.getColour());
                    moved = true;
                    numberOfTurns++;
                } catch (IllegalMoveException e) {
                    moved = false;
                    System.out.println(e);
                }
            }
        }
        this.states.push(new Game(this));
        saveGame();
    }

    public void saveGame() {
        ObjectOutputStream oos = null;
        try {
            File file = new File("game");
            if(!file.exists()) {
                file.createNewFile();
            }
            oos = new ObjectOutputStream(new FileOutputStream("game"));
            oos.writeObject(this);
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

    private Player playerWon() {
        for(int i=0; i<numberOfPlayers; i++) {
            if(grid.gameComplete(players[i].getColour(), numberOfTurns)) {
                this.gameInComplete = false;
                return players[i];
            }
        }
        this.gameInComplete = true;
        return null;
    }

    public void resumeGame() {
        boolean help = true;
        this.lastMoveWasAUndo = false;
        while (playerWon()==null) {
            for(int i=this.nextPlayer; i<numberOfPlayers && help; i++) {
                if(players[i].hasLost() && numberOfTurns>numberOfPlayers) {
                    saveGame();
                    continue;
                }
                if(numberOfTurns>numberOfPlayers && grid.hasLost(players[i])) {
                    players[i].lost();
                    saveGame();
                    if(players[i].hasLost()) {
                        saveGame();
                        continue;
                    }
                }
                if(this.lastMoveWasAUndo) {
                    i = (numberOfPlayers+i-2)%numberOfPlayers;
                    this.currentPlayer = players[i];
                    takeTurn();
                }
                else {
                    this.currentPlayer = players[i];
                    takeTurn();
                }
                if(playerWon()!=null)
                    break;
                this.nextPlayer = (i+1)%numberOfPlayers;
                if(i==numberOfPlayers-1)
                    help = false;
            }
            for(int i=0; i<numberOfPlayers && !help; i++) {
                if(players[i].hasLost() && numberOfTurns>numberOfPlayers) {
                    saveGame();
                    continue;
                }
                if(numberOfTurns>numberOfPlayers && grid.hasLost(players[i])) {
                    players[i].lost();
                    saveGame();
                    if(players[i].hasLost()) {
                        saveGame();
                        continue;
                    }
                }
                if(this.lastMoveWasAUndo) {
                    i = (numberOfPlayers+i-2)%numberOfPlayers;
                    this.currentPlayer = players[i];
                    takeTurn();
                }
                else {
                    this.currentPlayer = players[i];
                    takeTurn();
                }

                if(playerWon()!=null)
                    break;
                this.nextPlayer = (i+1)%numberOfPlayers;
            }
        }
        this.gameInComplete = false;
        saveGame();
        System.out.println(playerWon()+" won the game");
    }


}
