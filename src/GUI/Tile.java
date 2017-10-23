package GUI;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Sphere;
import javafx.util.Duration;
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
    public static int duration = 0;
    private static final int dur = 2;
    public static boolean burstbool = false;

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
                System.out.println("x: "+this.x+" y: "+this.y+" rows: "+this.tileGrid.getNumberOfRows()+" cols: "+this.tileGrid.getNumberOfColumns());
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
            int i = this.y;
            int j = this.x;
            Timeline timeline = new Timeline();
            if(i > 0) {
                Sphere orb = new Orb(x, y, center_x+50, center_y+50, game, tileGrid).getOrb(color);
                innerGroup.getChildren().addAll(orb);
                System.out.println("timeline should play");
                timeline.getKeyFrames().addAll(
                        new KeyFrame(
                        		new Duration(duration),
                                new KeyValue(orb.translateXProperty(), center_x+50),
                                new KeyValue(orb.translateYProperty(), center_y+50)
                        ),
                        new KeyFrame(
                                new Duration(duration+250),
                                new KeyValue(orb.translateXProperty(), center_x-50),
                                new KeyValue(orb.translateYProperty(), center_y+50)
                        )
                );
            }

            if(i < this.tileGrid.getNumberOfColumns()-1) {
                Sphere orb = new Orb(x, y, center_x+50, center_y+50, game, tileGrid).getOrb(color);
                innerGroup.getChildren().addAll(orb);
                System.out.println("timeline should play");
                timeline.getKeyFrames().addAll(
                        new KeyFrame(
                        		new Duration(duration),
                                new KeyValue(orb.translateXProperty(), center_x+50),
                                new KeyValue(orb.translateYProperty(), center_y+50)
                        ),
                        new KeyFrame(
                                new Duration(duration+250),
                                new KeyValue(orb.translateXProperty(), center_x+150),
                                new KeyValue(orb.translateYProperty(), center_y+50)
                        )
                );
            }

            if(j > 0) {
                Sphere orb = new Orb(x, y, center_x+50, center_y+50, game, tileGrid).getOrb(color);
                innerGroup.getChildren().addAll(orb);
                System.out.println("timeline should play");
                timeline.getKeyFrames().addAll(
                        new KeyFrame(
                        		new Duration(duration),
                                new KeyValue(orb.translateXProperty(), center_x+50),
                                new KeyValue(orb.translateYProperty(), center_y+50)
                        ),
                        new KeyFrame(
                                new Duration(duration+250),
                                new KeyValue(orb.translateXProperty(), center_x+50),
                                new KeyValue(orb.translateYProperty(), center_y-50)
                        )
                );
            }

            if(j < this.tileGrid.getNumberOfRows()-1) {
                Sphere orb = new Orb(x, y, center_x+50, center_y+50, game, tileGrid).getOrb(color);
                innerGroup.getChildren().addAll(orb);
                System.out.println("timeline should play");
                timeline.getKeyFrames().addAll(
                        new KeyFrame(
                        		new Duration(duration),
                                new KeyValue(orb.translateXProperty(), center_x+50),
                                new KeyValue(orb.translateYProperty(), center_y+50)
                        ),
                        new KeyFrame(
                                new Duration(duration+250),
                                new KeyValue(orb.translateXProperty(), center_x+50),
                                new KeyValue(orb.translateYProperty(), center_y+150)
                        )
                );
            }
            timeline.play();
            duration+=250;
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
            Sphere o1 = orb1.getOrb(color);
            Sphere o2 = orb2.getOrb(color);
            Arc e1 = new Arc(center_x + 50, center_y+50, 10, 10, 0, 360); 
            Arc e2 = new Arc(center_x + 50, center_y+50, 10, 10, 180, 360);
            PathTransition t1 = new PathTransition();
            t1.setPath(e1);
            t1.setNode(o1);
            t1.setInterpolator(Interpolator.LINEAR);
            t1.setDuration(Duration.seconds(dur));
            t1.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
            t1.setCycleCount(Timeline.INDEFINITE);
            t1.play();
            PathTransition t2 = new PathTransition();
            t2.setPath(e2);
            t2.setNode(o2);
            t2.setInterpolator(Interpolator.LINEAR);
            t2.setDuration(Duration.seconds(dur));
            t2.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
            t2.setCycleCount(Timeline.INDEFINITE);
            t2.play();
            innerGroup.getChildren().addAll(o1,o2);
        } else if (this.numberOfOrbs == 3) {
            Orb orb1 = new Orb(x, y,center_x+50, center_y+50-10, game, tileGrid);
            Orb orb2 = new Orb(x, y,center_x+50 - 10, center_y+50+10, game, tileGrid);
            Orb orb3 = new Orb(x, y,center_x+50+10, center_y+50+10, game, tileGrid);
            this.color = color;
            Sphere o1 = orb1.getOrb(color);
            Sphere o2 = orb2.getOrb(color);
            Sphere o3 = orb3.getOrb(color);
            Arc e1 = new Arc(center_x + 50, center_y+50, 10, 10, 90, 360); 
            Arc e2 = new Arc(center_x + 50, center_y+50, 10, 10, -30, 360);
            Arc e3 = new Arc(center_x + 50, center_y+50, 10, 10, -150, 360);
            PathTransition t1 = new PathTransition();
            t1.setPath(e1);
            t1.setNode(o1);
            t1.setInterpolator(Interpolator.LINEAR);
            t1.setDuration(Duration.seconds(dur));
            t1.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
            t1.setCycleCount(Timeline.INDEFINITE);
            t1.play();
            PathTransition t2 = new PathTransition();
            t2.setPath(e2);
            t2.setNode(o2);
            t2.setInterpolator(Interpolator.LINEAR);
            t2.setDuration(Duration.seconds(dur));
            t2.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
            t2.setCycleCount(Timeline.INDEFINITE);
            t2.play();
            PathTransition t3 = new PathTransition();
            t3.setPath(e3);
            t3.setNode(o3);
            t3.setInterpolator(Interpolator.LINEAR);
            t3.setDuration(Duration.seconds(dur));
            t3.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
            t3.setCycleCount(Timeline.INDEFINITE);
            t3.play();
            innerGroup.getChildren().addAll(o1,o2,o3);
        }
        this.group.getChildren().addAll(innerGroup);
        return false;
    }

    public Color getColor() {
        return color;
    }
}
