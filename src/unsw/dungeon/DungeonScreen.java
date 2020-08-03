package unsw.dungeon;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DungeonScreen {

    private Stage stage;
    private String title;
    DungeonControllerLoader dungeonLoader;
    private DungeonController controller;
    private Scene scene;
    private FXMLLoader loader;
    Parent root;

    /**
     * Constructs a new dungeonScreen by parsing the name of the wanted json file from a string
     * and then creating the necessary objects to load and control the dungeon
     * @param stage
     * @param level
     * @throws IOException
     */
    public DungeonScreen(Stage stage, String level) throws IOException {
        this.stage = stage;
        this.title = "Level: "+level;

        dungeonLoader = new DungeonControllerLoader(level+".json");
        controller = dungeonLoader.loadController();
        controller.setLevelName(level);

        loader = new FXMLLoader(getClass().getResource("DungeonView.fxml"));
        loader.setController(controller);
        root = loader.load();
        scene = new Scene(root);

    }

    public void start() throws IOException {
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
        root.requestFocus();
        
        //this should stop players from resizing windows
        //if window is wrong size retry button fixes most issues
        stage.setResizable(false);
        
    }

    public DungeonController getController() {
        return controller;
    }

}