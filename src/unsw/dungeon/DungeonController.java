package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
// import javafx.scene.layout.StackPane;
// import javafx.collections.ObservableList;
// import javafx.scene.Node;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;

import java.io.File;
import java.io.IOException;

/**
 * A JavaFX controller for the dungeon.
 * @author Robert Clifton-Everest
 *
 */
public class DungeonController {
    @FXML
    private GridPane foreground;

    @FXML
    private GridPane background;

    @FXML
    private Text completionMessage;

    @FXML
    private Button menuButton;

    @FXML
    private Button retryButton;

    private List<ImageView> stationaryEntities;
    private List<ImageView> movingEntities;

    private MenuScreen menuScreen;
    
    private String level;

    private Player player;

    private Dungeon dungeon;

    public DungeonController(Dungeon dungeon, List<ImageView> stationaryEntities, List<ImageView> movingEntities) {
        this.dungeon = dungeon;
        this.player = dungeon.getPlayer();
        this.stationaryEntities = new ArrayList<>(stationaryEntities);
        this.movingEntities = new ArrayList<>(movingEntities);
    }

    @FXML
    public void initialize() {
        Image ground = new Image((new File("images/dirt_0_new.png")).toURI().toString());

        // Add the ground to background first so it is below all other entities
        for (int x = 0; x < dungeon.getWidth(); x++) {
            for (int y = 0; y < dungeon.getHeight(); y++) {
                background.add(new ImageView(ground), x, y);
            }
        }

        for (ImageView entity : stationaryEntities) {
            background.getChildren().add(entity);
        }

        //initialise foreground with blank images
        for (int x = 0; x < dungeon.getWidth(); x++) {
            for (int y = 0; y < dungeon.getHeight(); y++) {
                ImageView blank = new ImageView(ground);
                blank.setOpacity(0);
                foreground.add(blank, x, y);
            }
        }

        for (ImageView entity : movingEntities) {
            foreground.getChildren().add(entity);
        }

        setDungeonListener();

    }

    private void setDungeonListener() {
        dungeon.levelComplete().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                if (newValue.intValue() == 0) {
                    completionMessage.setText("");
                    

                } else if (newValue.intValue() == 1) {
                    completionMessage.setText("LEVEL COMPLETE");
                    

                } else if (newValue.intValue() == 2) {
                    completionMessage.setText("LEVEL FAILED");
                    
                }
            }
        });
    }

    @FXML
    public void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case UP:
                player.moveUp();
                break;
            case DOWN:
                player.moveDown();
                break;
            case LEFT:
                player.moveLeft();
                break;
            case RIGHT:
                player.moveRight();
                break;
            default:
                break;
            }
    }

    @FXML
    public void handleMenuButton(Event event) {
        menuScreen.start();
    }

    @FXML
    public void handleRetryButton(Event event) throws IOException {
        menuScreen.getController().startDungeon(level);
    }


    public void setMenuScreen(MenuScreen menuScreen) {
        this.menuScreen = menuScreen;
    }

    public void setLevelName(String level) {
        this.level = level;
    }

}

