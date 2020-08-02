package unsw.dungeon;

import javafx.scene.layout.Pane;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;



public class MenuController {

    
    @FXML
    private Button levelSelectButton;

    @FXML
    private ChoiceBox<String> levelSelectChoiceBox;

    @FXML
    private Pane menuPane;

    private DungeonScreen dungeonScreen;

    ObservableList<String> levelList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        loadLevelNames();

    }
    @FXML
    public void handleLevelSelect(ActionEvent event) throws IOException {
        String level = levelSelectChoiceBox.getValue();

        if (level == null) {
            return;
        }

        dungeonScreen.start();
    }

    public void setDungeonScreen(DungeonScreen dungeonScreen) {
        this.dungeonScreen = dungeonScreen;
    }

    public void loadLevelNames() {
        String level1 = "maze";
        String level2 = "boulders";
        String level3 = "advanced";
        String level4 = "testDungeon";

        levelList.addAll(level1, level2, level3, level4);
        levelSelectChoiceBox.getItems().addAll(levelList);
    }
}