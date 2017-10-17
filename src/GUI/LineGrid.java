package GUI;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class LineGrid {
    private static Line[] gridLines;

    public static void setColour(Color color) {
        if(gridLines==null)
            return;
        for(int i=0; i<gridLines.length; i++) {
            gridLines[i].setStroke(color);
        }
    }

    public static Group getGrid(int width, int height) {
        gridLines = new Line[(width+1)+(height+1)];
        Group group = new Group();

        for(int i=1; i<=width+1; i++) {
            Line line = new Line();
            line.setStartX(i*100);
            line.setStartY(100);
            line.setEndX(i*100);
            line.setEndY((height+1)*100);
            gridLines[i-1] = line;
            group.getChildren().add(line);
        }

        for(int i=1; i<=height+1; i++) {
            Line line = new Line();
            line.setStartY(i*100);
            line.setStartX(100);
            line.setEndY(i*100);
            line.setEndX((width+1)*100);
            gridLines[width+i] = line;
            group.getChildren().add(line);
        }

        return group;
    }
}
