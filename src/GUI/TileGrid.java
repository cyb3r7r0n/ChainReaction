package GUI;


import javafx.scene.Group;
import javafx.scene.paint.Color;
import withGUI.Cell;
import withGUI.Game;
import withGUI.GameState;

public class TileGrid {

    public  Tile[][] tiles;
    private  int numberOfColumns, numberOfRows;
    public  boolean[][] reached;
    public  int sum;
    private Group group;
    private Game game;
    private static final int duration = 300;

    public TileGrid(int numberOfRows, int numberOfColumns, Game game) {
        this.numberOfColumns = numberOfColumns;
        this.numberOfRows = numberOfRows;
        this.group = new Group();
        this.game = game;
        this.tiles = new Tile[numberOfRows][numberOfColumns];
        for(int i=1; i<=this.numberOfColumns; i++) {
            for(int j=1; j<=this.numberOfRows; j++) {
                this.tiles[j-1][i-1] = new Tile(j-1, i-1, i*100, j*100, game, this);
                this.tiles[j-1][i-1].setGroup(group);
                this.group.getChildren().addAll(tiles[j-1][i-1].getRectangle());
            }
        }
    }

    public  int getNumberOfRows() {
        return numberOfRows;
    }

    public  int getNumberOfColumns() {
        return numberOfColumns;
    }

    public  Group getGrid() {
        return this.group;
    }

    public  boolean hasLost(Color color) {
        for (int i=0; i<numberOfRows; i++) {
            for (int j=0; j<numberOfColumns; j++) {
                if(tiles[i][j].getColor()!=null && tiles[i][j].getColor().equals(color))
                    return false;
            }
        }
        return true;
    }

    public  void replenish() {
        sum = 0;
        reached = new boolean[numberOfRows][numberOfColumns];
    }

    public  void addOrb(int i, int j, Color color) {
        if(i>=0 && j>=0 && i<numberOfRows && j<numberOfColumns) {
            if(sum!=numberOfColumns*numberOfRows) {
                boolean burst = tiles[i][j].addOrb(color);
                if (burst) {
                    addOrb(i + 1, j, color);
                    addOrb(i - 1, j, color);
                    addOrb(i, j + 1, color);
                    addOrb(i, j - 1, color);
                }
                if(!reached[i][j]) {
                    reached[i][j] = true;
                    sum+=1;
                }
            }
        }
    }

    public Cell[][] getGrid2() {
        Cell[][] grid = new Cell[numberOfRows][numberOfColumns];
        for (int i=0; i<numberOfRows; i++) {
            for(int j=0; j< numberOfColumns; j++) {
                Cell cell = new Cell(tiles[i][j].getNumberOfOrbs(), tiles[i][j].getX(), tiles[i][j].getY(), tiles[i][j].getColor());
                grid[i][j] = cell;
            }
        }
        return grid;
    }

    public void setTiles(GameState gameState, Game game) {
        this.numberOfColumns = gameState.getNumberOfColumns();
        this.numberOfRows = gameState.getNumberOfRows();
        this.group = new Group();
        this.game = game;
        this.tiles = new Tile[this.numberOfRows][this.numberOfColumns];
        for(int i=1; i<=this.numberOfColumns; i++) {
            for(int j=1; j<=this.numberOfRows; j++) {
                int x = j-1;
                int y = i-1;
                this.tiles[x][y] = new Tile(x, y, i*100, j*100, game, this);
                this.tiles[x][y].setGroup(group);
                this.group.getChildren().addAll(tiles[x][y].getRectangle());
                for (int k=0; k<gameState.getGrid()[x][y].getNumberOfOrbs() && gameState.getGrid()[x][y].getNumberOfOrbs()>0; k++) {
                    this.sum = 0;
                    this.reached = new boolean[numberOfRows][numberOfColumns];
                    addOrb(x, y, gameState.getGrid()[x][y].getColor());
                }
            }
        }
    }
}
