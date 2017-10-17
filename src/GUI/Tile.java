package GUI;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import withGUI.Game;

public class Tile {
    private final int x;
    private final int y;
    private final int center_x;
    private final int center_y;
    private int numberOfOrbs;
    private Group group;
    private Color color;
    private Group innerGroup;
    private Game game;
    private int criticalMass;
    private TileGrid tileGrid;

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public int getNumberOfOrbs() {
        return numberOfOrbs;
    }

    public Tile(int x, int y, int center_x, int center_y, Game game, TileGrid tileGrid) {
        this.x = x;
        this.y = y;
        this.center_x = center_x;
        this.center_y = center_y;
        this.numberOfOrbs = 0;
        this.innerGroup = new Group();
        this.game = game;
        this.tileGrid = tileGrid;
        setCriticalMass();
    }

    public Rectangle getRectangle() {
        Rectangle rectangle = new Rectangle();
        rectangle.setHeight(100);
        rectangle.setWidth(100);
        rectangle.setFill(Color.BLACK);
        rectangle.setX(center_x);
        rectangle.setY(center_y);

        rectangle.setOnMouseEntered(e -> {
            if(this.color==null || this.color.equals(Board.currentColor))
                rectangle.setFill(Board.currentColor);
        });

        rectangle.setOnMouseExited( e -> {
            rectangle.setFill(Color.BLACK);
        });

        rectangle.setOnMouseClicked( e-> {
            if(this.color==null || this.color.equals(Board.currentColor)) {
                tileGrid.replenish();
                tileGrid.addOrb(x, y, Board.currentColor);
                game.takeTurn();
                rectangle.setFill(Color.BLACK);
            }
        });
        return rectangle;
    }

    public void setGroup(Group group) {
        this.group = group;
//        this.group.getChildren().addAll(this.innerGroup);
    }

    public void setCriticalMass() {
        int criticalMass = 4;
        if(x==0 || x==tileGrid.getNumberOfRows()-1)
            criticalMass--;
        if(y==0 || y==tileGrid.getNumberOfColumns()-1)
            criticalMass--;
        this.criticalMass = criticalMass;
    }

    public boolean addOrb(Color color) {
        this.numberOfOrbs++;
        this.innerGroup.getChildren().clear();
        innerGroup = new Group();
        if(numberOfOrbs==criticalMass) {
            this.numberOfOrbs=0;
            this.color = null;
            this.group.getChildren().addAll(innerGroup);
            return true;
        }
        if (this.numberOfOrbs == 1) {
            Orb orb = new Orb(x, y,center_x+50, center_y+50, game, tileGrid);
            this.color = color;
            innerGroup.getChildren().addAll(orb.getOrb(color));
        } else if (this.numberOfOrbs == 2) {
            Orb orb1 = new Orb(x, y,center_x+40, center_y+50, game, tileGrid);
            Orb orb2 = new Orb(x, y,center_x+60, center_y+50, game, tileGrid);
            this.color = color;
            innerGroup.getChildren().addAll(orb1.getOrb(color), orb2.getOrb(color));
        } else if (this.numberOfOrbs == 3) {
            Orb orb1 = new Orb(x, y,center_x+50, center_y+50-10, game, tileGrid);
            Orb orb2 = new Orb(x, y,center_x+50 - 10, center_y+50+10, game, tileGrid);
            Orb orb3 = new Orb(x, y,center_x+50+10, center_y+50+10, game, tileGrid);
            this.color = color;
            innerGroup.getChildren().addAll(orb1.getOrb(color), orb2.getOrb(color), orb3.getOrb(color));
        }
        this.group.getChildren().addAll(innerGroup);
        return false;
    }

    public Color getColor() {
        return color;
    }
}
