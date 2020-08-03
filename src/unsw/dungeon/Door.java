package unsw.dungeon;

/**
 * A Door class that represents a door in the dungeon.
 * Uses state design pattern.
 * 
 * @author Frank Merriman, The Tran
 */

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
public class Door extends Entity {
    // private DoorState closedState;
    // private DoorState openState;

    // private DoorState currState;

    private BooleanProperty openState;

    

    private int u_id;
    /**
     * A constructor to create a door in the dungeon.
     * @param x  x-coordinate of the door to appear in
     * @param y y-coordinate of the door to appear in
     * @param u_id  Unique ID of the door for interaction with a matching ID key.
     */
    public Door(int x, int y, int u_id) {
        super(x, y);
        // closedState = new ClosedDoorState(this);
        // openState = new OpenDoorState(this);
        // currState = closedState;
        openState = new SimpleBooleanProperty(false);
        this.u_id = u_id;
    }

    public BooleanProperty openState() {
        return openState;
    }

    public boolean getOpenState() {
        return openState().get();
    }

    public void setOpenState(boolean state) {
        openState().set(state);
    }

    private boolean correctKey(Key key) {
        if (getID() == key.getID()) {
            openState.set(true);
            return true;
        }
        return false;
    }

    public int getID() {
        return u_id;
    }

    @Override
    public boolean isBarrier(Entity e) {
        if (getOpenState()) {// Door is open
            return false;
        }
        
        if (e instanceof Player) {
            return playerDoorInteraction((Player) e);
        }

        return true;
    }

    /**
     * Takes in a player and checks if player can open the this door
     * Key is automatically consumed and door is opened if it is right key
     * @param p player whose key we are verifying
     * @return true if door is a barrier at end of interaction
     */
    public boolean playerDoorInteraction(Player p) {
        Key key = p.getKey();
        if (key != null && correctKey(key)) {
            p.unequip(key);
            return false;
        }
        return true;
    }

}