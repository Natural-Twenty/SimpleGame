package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

/**
 * The player entity
 * @author Robert Clifton-Everest
 *
 */
public class Player extends Entity implements MoveBehaviour, Subject{

    private List<Observer> listObservers;
    private Dungeon dungeon;

    /**
     * Create a player positioned in square (x,y)
     * @param x
     * @param y
     */
    public Player(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
        listObservers = new ArrayList<>();
    }

    public void moveUp() {
        if (getY() > 0 )
            moveTo(getX(), getY() - 1);
    }

    public void moveDown() {
        if (getY() < dungeon.getHeight() - 1)
            moveTo(getX(), getY() + 1);
    }

    public void moveLeft() {
        if (getX() > 0 )
            moveTo(getX() - 1, getY());
    }

    public void moveRight() {
        if (getX() < dungeon.getWidth() - 1)
            moveTo(getX() + 1, getY());
    }

    /**
     * This method takes in an x and y coordinate and attempts to move the player into it, checking collide behaviour first
     * @param x cooridnate of tile we are moving to
     * @param y cooridnate of tile we are moving to
     */
    public void moveTo (int newX, int newY) {
        if (canMove(newX, newY)) {
            x().set(newX);
            y().set(newY);
            updateObservers();
        }
    }

    /**
     * This method takes in an x and y coordinate and then checks the dungeon if there is a wall that matches
     * those coordinates (will need to update to include doors)
     * @param x cooridnate of tile we are checking
     * @param y cooridnate of tile we are checking
     * @return true if eligible tile for player movement
     */
    public boolean canMove(int x, int y) {

        if (dungeon.getCompletion()) { //when true dungeon is complete so player cannot move
            return false;
        }

        List<Entity> tileEntities = dungeon.getEntities(x, y);

        if (tileEntities.size() < 1) { //Basically same as old code for entity = null
            return true;
        }
        
        for (Entity e : tileEntities) {
            if (e.isBarrier(this)) {
                return false;
            } else {
                e.onCollide(this);
            }
        }
        
        return true;
        
    }

    @Override
    public void detach(Observer o) {
        listObservers.remove(o);
    }

    @Override
    public void attach(Observer o) {
        listObservers.add(o);
    }

    @Override
    public void updateObservers() {
        for (Observer o : listObservers) {
            o.update(this);
        }
    }

}
