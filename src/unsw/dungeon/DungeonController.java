package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
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
public class DungeonController implements Observer{
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

    @FXML
    private Text potionCounter;

    @FXML
    private Text swordCounter;

    @FXML
    private Text pickaxeCounter;

    @FXML
    private Text keyCounter;

    @FXML
    private Text bombCounter;

    @FXML
    private VBox goalVbox;

    @FXML
    private GridPane goalGrid;

    private List<ImageView> stationaryEntities;
    private List<ImageView> movingEntities;

    private MenuScreen menuScreen;
    
    private String level;

    private Player player;

    private Dungeon dungeon;

    public DungeonController(Dungeon dungeon, List<ImageView> stationaryEntities, List<ImageView> movingEntities) {
        this.dungeon = dungeon;
        this.player = dungeon.getPlayer();
        player.attach(this);
        this.stationaryEntities = new ArrayList<>(stationaryEntities);
        this.movingEntities = new ArrayList<>(movingEntities);
    }

    @FXML
    public void initialize() {
        Image ground = new Image((new File("src/images/dirt_0_new.png")).toURI().toString());

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

        //Scenebuilder broke so had to start doing javafx by code
        TextArea txt = new TextArea(dungeon.getGoalString());
        txt.setWrapText(true);
        txt.setEditable(false);
        goalVbox.getChildren().add(txt);

        Image exitImage = new Image((new File("src/images/exit.png")).toURI().toString());
        Image treasureImage = new Image((new File("src/images/gold_pile.png")).toURI().toString());
        Image hunterImage = new Image((new File("src/images/deep_elf_master_archer.png")).toURI().toString());
        Image floorPlateImage = new Image((new File("src/images/pressure_plate.png")).toURI().toString());

        ImageView exitImageView = new ImageView(exitImage);
        ImageView treasureImageView = new ImageView(treasureImage);
        ImageView hunterImageView = new ImageView(hunterImage);
        ImageView floorPlateImageView = new ImageView(floorPlateImage);

        goalGrid.add(exitImageView, 1, 0);
        goalGrid.add(treasureImageView, 2, 0);
        goalGrid.add(hunterImageView, 3, 0);
        goalGrid.add(floorPlateImageView, 4, 0);
        
        //Add code that creates correct sized grid pane
        //Adds image views for the correct goals
        //Sets up correct trackers for all goals

        //This could get a bit buggy if we have duplicate goals (i.e two eliminate goals? nvm maybe not)
        //1. Get a list of all leaf goal objects
        List<Goal> dungeonGoals = dungeon.getGoalObj();
        
        //2. We would also probs attach listeners/observers here
        for (Goal g: dungeonGoals) {
            if (g instanceof Exit) {

            } else if (g instanceof Treasure) {

            } else if (g instanceof Hunter) {
                
            } else if (g instanceof FloorSwitch) {
                
            }
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
            case B:
                if (player.getBomb() != null) {
                    player.plantBomb();
                }
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

    public void updatePotionBar() {

    }

    @Override
    public void update(Object o) {
        //We want to check if there are any changes to the players
        //inventory, there may be a neater way to do this
        if (o instanceof Player) {
            Player player = (Player) o;

            Potion potion = player.getPotion();
            if (potion == null) {
                potionCounter.setText("0");
            } else {
                potionCounter.setText(Integer.toString(potion.getDurability() - 1));
            }

            Sword sword = player.getSword();
            if (sword == null) {
                swordCounter.setText("0");
            } else {
                swordCounter.setText(Integer.toString(sword.getDurability()));
            }

            Pickaxe pickaxe = player.getPickaxe();
            if (pickaxe == null) {
                pickaxeCounter.setText("0");
            } else {
                pickaxeCounter.setText(Integer.toString(pickaxe.getDurability()));
            }

            Key key = player.getKey();
            if (key == null) {
                keyCounter.setText("0");
            } else {
                keyCounter.setText("1");
            }

            Bomb bomb = player.getBomb();
            if (bomb == null) {
                bombCounter.setText("0");
            } else {
                bombCounter.setText("1");
            }

        }
    }

}

