package unsw.dungeon;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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

    private Stage stage;

    private DungeonScreen dungeonScreen;
    private MenuScreen menuScreen;

    ObservableList<String> levelList = FXCollections.observableArrayList();

    public MenuController(MenuScreen screen, Stage stage) {
        this.menuScreen = screen;
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        loadLevelNames();
    }

    /**
     * When a level is selected from the list, a new dungeonScreen is constructed using the string
     * and menuscreens stage
     * @param event
     * @throws IOException
     */
    @FXML
    public void handleLevelSelect(ActionEvent event) throws IOException {
        String level = levelSelectChoiceBox.getValue();

        if (level == null) {
            return;
        }

        dungeonScreen = new DungeonScreen(stage, level);
        dungeonScreen.getController().setMenuScreen(menuScreen);
        dungeonScreen.start();

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