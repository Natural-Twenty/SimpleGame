/**
 *
 */
package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

/**
 * A dungeon in the interactive dungeon player.
 *
 * A dungeon can contain many entities, each occupy a square. More than one
 * entity can occupy the same square.
 *
 * @author Robert Clifton-Everest
 *
 */
public class Dungeon implements Observer{

    private int width, height;
    private List<Entity> entities;
    private Player player;
    private GoalOR goal;
    private boolean levelComplete;

    public Dungeon(int width, int height) {
        this.width = width;
        this.height = height;
        this.entities = new ArrayList<>();
        this.player = null;
        this.goal = new GoalOR();
        this.levelComplete = false;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void addEntity(Entity entity) { //add a case for adding enemy which attaches it as an observer to player
        entities.add(entity);
    }

    /**
     * Add a sub goal to dungeons GoalAND, This might need to be made into GoalOR
     * @param newGoal is a subGoal we wish to add to the dungeon. Only one subgoal being added is the json equivelant
     * of not using AND/OR
     */
    public void addGoal(Goal newGoal) {
        goal.addSubGoal(newGoal);
    }

    public void checkGoalStatus() {
        if(goal.isComplete()){
            levelComplete = true;
            System.out.println("Congrats you win");
        }
    }

    //quick method for checking if a level is in completed mode
    public boolean getCompletion() {
        return levelComplete;
    }

    @Override
    public void update() {
        checkGoalStatus();
    }

    /**
     * Return all entities at the given coordinates in the dungeon
     * @param x
     * @param y
     * @return null if no entity at location otherwise a list of type entity
     */
    public List<Entity> getEntities(int x, int y) {
        List<Entity> tileEntities= new ArrayList<>();

        for (Entity e : entities) {
            if (e != null) {
                int eX = e.getX();
                int eY = e.getY();

                if (x == eX && y == eY) {
                    tileEntities.add(e);
                }
            }
        }

        return tileEntities;

    }
}
