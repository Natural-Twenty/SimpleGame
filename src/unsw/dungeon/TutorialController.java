package unsw.dungeon;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class TutorialController {
    @FXML
    private Button exitButton;

    @FXML
    private Pane tutorialPane;

    @FXML
    private TextArea textArea;

    @FXML
    private GridPane gridpane1;

    @FXML
    private GridPane gridpane2;

    private MenuScreen menuScreen;

    @FXML
    public void handleExitBtn(ActionEvent event) throws IOException {
        menuScreen.start();
    }

    public void setMenuScreen(MenuScreen screen) {
        this.menuScreen = screen;
    }

}