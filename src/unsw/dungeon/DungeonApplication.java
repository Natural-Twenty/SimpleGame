package unsw.dungeon;

import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;

public class DungeonApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        // primaryStage.setTitle("Dungeon");

        // DungeonControllerLoader dungeonLoader = new DungeonControllerLoader("testDungeon.json");

        // DungeonController controller = dungeonLoader.loadController();

        // FXMLLoader loader = new FXMLLoader(getClass().getResource("DungeonView.fxml"));
        // loader.setController(controller);
        // Parent root = loader.load();
        // Scene scene = new Scene(root);
        // root.requestFocus();
        // primaryStage.setScene(scene);
        // primaryStage.show();

        MenuScreen menuScreen = new MenuScreen(primaryStage);
        //TutorialScreen tutorialScreen = new TutorialScreen(primaryStage);
        DungeonScreen dungeonScreen = new DungeonScreen(primaryStage);

        // Both controllers need to know about the other screen.
        // Dungeons controller sees its screen since I hoped that would allow us to use the same method of
        // resetting a dungeon from main menu or the retry button

        menuScreen.getController().setDungeonScreen(dungeonScreen);

        dungeonScreen.getController().setMenuScreen(menuScreen);
        dungeonScreen.getController().setDungeonScreen(dungeonScreen);

        menuScreen.start();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
