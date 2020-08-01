package unsw.dungeon;

import javafx.scene.layout.Pane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;



public class MenuController {

    
    @FXML
    private Button levelSelectButton;

    @FXML
    private Pane menuPane;

    private DungeonScreen dungeonScreen;

    @FXML
    void handleLevelSelect(ActionEvent event) {
        dungeonScreen.start();
    }

    public void setDungeonScreen(DungeonScreen dungeonScreen) {
        this.dungeonScreen = dungeonScreen;
    }

}