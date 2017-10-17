package GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import withGUI.Colours;

import java.io.*;
import java.util.ArrayList;

public class SettingsPage implements Colours, Serializable {
    private static String[] colours;
    private static final long serialVersionUID = 2L;

    public static void init() {
        if(colours==null) {
            colours = new String[8];
            colours[0] = RED;
            colours[1] = BLUE;
            colours[2] = GREEN;
            colours[3] = ORANGE;
            colours[4] = PINK;
            colours[5] = TEAL;
            colours[6] = WHITE;
            colours[7] = YELLOW;
        }
    }

    public static Color getCorrespondingColour(String colour) {
        if(RED.equals(colour)) {
            return Color.valueOf(REDCOLOR);
        } else if(BLUE.equals(colour)) {
            return Color.valueOf(BLUECOLOR);
        } else if(GREEN.equals(colour)) {
            return Color.valueOf(GREENCOLOR);
        } else if(ORANGE.equals(colour)) {
            return Color.valueOf(ORANGECOLOR);
        } else if(PINK.equals(colour)) {
            return Color.valueOf(PINKCOLOR);
        } else if(TEAL.equals(colour)) {
            return Color.valueOf(TEALCOLOR);
        } else if(WHITE.equals(colour)) {
            return Color.valueOf(WHITECOLOR);
        } else if(YELLOW.equals(colour)) {
            return Color.valueOf(YELLOWCOLOR);
        } else {
            return null;
        }
    }

    public static Color[] getColours() {
        Color[] COLOURS = new Color[colours.length];
        for(int i=0; i<colours.length; i++) {
            COLOURS[i] = getCorrespondingColour(colours[i]);
        }
        return COLOURS;
    }

    public static int selectColour(int j, String colour) {
        for (int i=0; i<colours.length; i++) {
            if(colours[i].equals(colour)) {
                colours[i] = colours[j];
                colours[j] = colour;
                return i;
            }
        }
        return -1;
    }

    public static void showPage(Stage stage) {
        if(colours==null)
            init();
        ArrayList<ChoiceBox<String>> colourChoice= new ArrayList<>();
        for(int i=0; i<colours.length; i++) {
            ChoiceBox<String> choiceBox =  new ChoiceBox<>();
            for(int j=0; j<colours.length; j++) {
                choiceBox.getItems().addAll(colours[j]);
            }
            choiceBox.setValue(colours[i]);

            choiceBox.setOnAction(new ActionHandler(choiceBox, i, stage));
            colourChoice.add(i,choiceBox);
        }

        VBox layout = new VBox(20);
        Font font = new Font(20);
        for (int i=0; i<colours.length; i++) {
            Sphere orb = new Sphere();
            orb.setRadius(20);
            PhongMaterial phongMaterial = new PhongMaterial();
            phongMaterial.setDiffuseColor(getCorrespondingColour(colours[i]));
            orb.setMaterial(phongMaterial);

            Label label = new Label("Player "+(i+1));
            label.setFont(font);
            HBox hBox = new HBox(20);
            hBox.setAlignment(Pos.CENTER);
            hBox.getChildren().addAll(orb, label, colourChoice.get(i));
            layout.getChildren().addAll(hBox);
        }

        Button button = new Button("Back");
        button.setOnAction( e -> {
            Board.showMenu();
        });
        layout.getChildren().addAll(button);
        layout.setAlignment(Pos.CENTER);
        layout.setPrefWidth(400);

        Scene scene = new Scene(layout, 400, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void saveSetting() {
        ObjectOutputStream oos = null;
        try {
            File file = new File("game");
            if(!file.exists()) {
                file.createNewFile();
            }
            oos = new ObjectOutputStream(new FileOutputStream("setting"));
            oos.writeObject(colours);
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

    public static void loadSetting() {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream("setting"));
            colours = (String[]) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
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
}

class ActionHandler implements EventHandler<ActionEvent> {

    private ChoiceBox<String> choiceBox;
    private int i;
    private Stage stage;

    public ActionHandler(ChoiceBox<String> choiceBox, int i, Stage stage) {
        this.choiceBox = choiceBox;
        this.i = i;
        this.stage = stage;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        SettingsPage.selectColour(this.i, this.choiceBox.getValue());
        SettingsPage.showPage(stage);
        SettingsPage.saveSetting();
    }
}