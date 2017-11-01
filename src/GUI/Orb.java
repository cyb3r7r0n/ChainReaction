package GUI;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import withGUI.Game;

public class Orb {
    private int center_x;
    private int center_y;
    private int x, y;
    private Color color;
    private Game game;
    private TileGrid tileGrid;

    public Orb(int x, int y, int center_x, int center_y, Game game, TileGrid tileGrid) {
        this.x = x;
        this.y = y;
        this.center_x = center_x;
        this.center_y = center_y;
        this.game = game;
        this.tileGrid = tileGrid;
        this.color = null;
    }

    public Sphere getOrb(Color color) {
        Sphere orb = new Sphere();
        orb.setTranslateX(center_x);
        orb.setTranslateY(center_y);
        orb.setRadius(20);
        PhongMaterial phongMaterial = new PhongMaterial();
        phongMaterial.setDiffuseColor(color);
        orb.setMaterial(phongMaterial);
        orb.setOnMouseClicked(e -> {
            if(this.color==null || this.color.equals(Board.currentColor)) {
                tileGrid.replenish();
                tileGrid.addOrb(x, y, Board.currentColor);
            }
        });
        this.color = color;



        return orb;
    }
}
