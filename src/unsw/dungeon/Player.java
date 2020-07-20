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
    private List<Entity> inventory;
    private Dungeon dungeon;
    private int prevX;
    private int prevY;

    /**
     * Create a player positioned in square (x,y)
     * @param x
     * @param y
     */
    public Player(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
        this.listObservers = new ArrayList<>();
        this.inventory = new ArrayList<>();
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
            setPrevX(getX());
            setPrevY(getY());
            x().set(newX);
            y().set(newY);
            collide(newX, newY);
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
                //e.onCollide(this);
            }
        }
        return true;
        
    }

    private void collide(int x, int y) {
        List<Entity> entities = dungeon.getEntities(x, y);
        for (Entity e : entities) {
            if (e != null) {
                e.onCollide(this);
            }
        }
    }

    public int getPrevX() {
        return prevX;
    }
    
    public int getPrevY() {
        return prevY;
    }

    public void setPrevX(int x) {
        prevX = x;
    }

    public void setPrevY(int y) {
        prevY = y;
    }

    public int computeXDirection(Entity e) {
        int xDiff = e.getX() - getX();
        return e.getX() + xDiff;
    }

    public int computeYDirection(Entity e) {
        int yDiff = e.getY() - getY();
        return e.getY() + yDiff;
    }

    public int computePrevXDirection(Entity e) {
        int xDiff = e.getX() - getPrevX();
        return e.getX() + xDiff;
    }

    public int computePrevYDirection(Entity e) {
        int yDiff = e.getY() - getPrevY();
        return e.getY() + yDiff;
    }

    public void equip(Entity e) {
        inventory.add(e);
        //if e is a potion its also an observer to player movement
        if (e instanceof Potion) {
            attach( (Potion) e);
        }
        dungeon.removeEntity(e);
    }

    /**
     * Remove a given entity from players inventory
     * @param e entity to be removed
     */
    public void unequip(Entity e) {
        inventory.remove(e);
    }

    /**
     * Method that checks players inventory for a sword
     * @return true if player has a sword inventory
     */
    public boolean hasSword() {
        for (Entity e : inventory) {
            if (e instanceof Sword) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method that checks players inventory for a potion
     * @return true if player has a potion inventory
     */
    public boolean isInvincible() {
        for (Entity e : inventory) {
            if (e instanceof Potion) {
                return true;
            }
        }
        return false;
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

    /**
     * 
     * @param hunter enemy the player is fighting
     * @return true if player has an item that can be used to fight
     */
    public boolean canFight(Hunter hunter) {

        for (Entity e : inventory) {
            if (e instanceof Weapon) { 
                return true;
            }
        }

        return false;
    }

    @Override
    public void onCollide(Entity e) {
        if (e instanceof Hunter) {
            Hunter h = (Hunter) e;
            if(this.canFight(h)) {
                dungeon.removeEntity(h);
                h.defeat();
            } else {
                dungeon.removeEntity(this);
            }
        }
    }

    public List<Entity> getInventory() {
        return inventory;
    }
}
