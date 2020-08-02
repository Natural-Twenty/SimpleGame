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

    public DungeonScreen(Stage stage) throws IOException {
        this.stage = stage;
        this.title = "Dungeon Level";

        dungeonLoader = new DungeonControllerLoader("testDungeon.json");
        controller = dungeonLoader.loadController();

        loader = new FXMLLoader(getClass().getResource("DungeonView.fxml"));
        loader.setController(controller);
        root = loader.load();
        scene = new Scene(root);

    }

    public void start() throws IOException {
        // dungeonLoader = new DungeonControllerLoader("testDungeon.json");
        // controller = dungeonLoader.loadController();

        // loader = new FXMLLoader(getClass().getResource("DungeonView.fxml"));
        // loader.setController(controller);
        // root = loader.load();
        // scene = new Scene(root);
        
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
        root.requestFocus();
        
    }

    public DungeonController getController() {
        return controller;
    }

}