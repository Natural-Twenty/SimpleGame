package unsw.dungeon;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TutorialScreen {

    private Stage stage;
    private String title;
    private TutorialController controller;
    private Scene scene;


    public TutorialScreen(Stage stage) throws IOException {
        this.stage = stage;
        title = "Crawler Tutorial";

        controller = new TutorialController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TutorialView.fxml"));
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

    public TutorialController getController() {
        return controller;
    }
}
