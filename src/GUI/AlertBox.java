package GUI;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {

    private boolean startNewGame;

    public AlertBox() {
        startNewGame = false;
    }

    public boolean startNewGame() {
        return startNewGame;
    }

    public void display(String title, Color color) {
        Stage window = new Stage();
        window.centerOnScreen();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setWidth(500);

        Label label = new Label(" HAS WON");
        label.setFont(new Font(20));
        Button start_new_game = new Button("Start new game");
        Button closeButton = new Button("Return to Main menu");

        closeButton.setOnAction(e -> window.close());
        start_new_game.setOnAction(e -> {
            startNewGame = true;
            window.close();
        });

        Sphere orb = new Sphere();
        orb.setRadius(20);
        PhongMaterial phongMaterial = new PhongMaterial();
        phongMaterial.setDiffuseColor(color);
        orb.setMaterial(phongMaterial);


        VBox layout = new VBox(20);
        HBox buttons = new HBox(20);
        HBox result = new HBox(20);

        result.getChildren().addAll(orb, label);
        buttons.getChildren().addAll(start_new_game, closeButton);

        layout.getChildren().addAll(result, buttons);
        layout.setAlignment(Pos.CENTER);
        buttons.setAlignment(Pos.CENTER);
        result.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
        window.setResizable(false);
    }
}
