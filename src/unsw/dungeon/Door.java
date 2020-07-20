package unsw.dungeon;

import java.util.List;
/**
 * A Door class that represents a door in the dungeon.
 * Uses state design pattern.
 * 
 * @author Frank Merriman, The Tran
 */
public class Door extends Entity {
    private DoorState closedState;
    private DoorState openState;

    private DoorState currState;

    private int u_id;
    /**
     * A constructor to create a door in the dungeon.
     * @param x  x-coordinate of the door to appear in
     * @param y y-coordinate of the door to appear in
     * @param u_id  Unique ID of the door for interaction with a matching ID key.
     */
    public Door(int x, int y, int u_id) {
        super(x, y);
        closedState = new ClosedDoorState(this);
        openState = new OpenDoorState(this);
        currState = closedState;
        this.u_id = u_id;
    }

    private boolean cannotUseKey(Key key) {
        if (getID() == key.getID()) {
            currState.openDoor();
            return false;
        }
        return true;
    }

    public int getID() {
        return u_id;
    }

    @Override
    public boolean isBarrier(Entity e) {
        if (currState == openState) {
            // Door is open
            return false;
        } else if (e instanceof Player) {
            Player player = (Player) e;
            List<Entity> inventory = player.getInventory();
            for (Entity ent : inventory) {
                if (ent instanceof Key) {
                    Key key = (Key) ent;
                    // If key cannot be used, door is barrier. If key can be used, door is not barrier.
                    boolean result = cannotUseKey(key);
                    if (!result) {
                        player.unequip(key);
                    }
                    return result;
                }
            }
            // No key found.
            return true;
        } else {
            // We get here if entity is not a player and door is closed.
            return true;
        }
    }

    public DoorState getClosedState() {
        return closedState;
    }

    public DoorState getOpenState() {
        return openState;
    }

    public void setState(DoorState state) {
        currState = state;
    }

}