package unsw.dungeon;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MenuScreen {

    private Stage stage;
    private String title;
    private MenuController controller;
    private Scene scene;


    public MenuScreen(Stage stage) throws IOException {
        this.stage = stage;
        title = "Dungeon Menu";

        controller = new MenuController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenuView.fxml"));
        loader.setController(controller);

        // load into a Parent node called root
        Parent root = loader.load();
        scene = new Scene(root);
    }

    public void start() {
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    public MenuController getController() {
        return controller;
    }
}