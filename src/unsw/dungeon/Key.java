package unsw.dungeon;

import java.util.List;

/**
 * A entity that can be used by the player to open a specific door
 * @author Frank Merriman, The Tran
 */
public class Key extends Entity {
    private int u_id;
    
    /**
     * Creates a new key with a unique id at given location
     * @param x axis location
     * @param y axis location
     * @param u_id unique id(corresponds to a door)
     */
    public Key(int x, int y, int u_id) {
        super(x, y);
        this.u_id = u_id;
        
    }

    public int getID() {
        return u_id;
    }

    @Override
    public void onCollide(Entity e) {
        if (e instanceof Player) {
            Player player = (Player) e;
            List<Entity> inventory = player.getInventory();
            if(canPickUpKey(inventory)) {
                player.equip(this);
                setDisplayOnScreen(false);
            }
        }
    }

    /**
     * Checks a players inventory for any existing keys
     * to see if there is room for this key
     * @param inventory of player we are checking
     * @return true if inventory has no key else false
     */
    private boolean canPickUpKey(List<Entity> inventory) {
        for (Entity e : inventory) {
            if (e instanceof Key) {
                return false;
            }
        }
        // We are here because there is no key in inventory.
        return true;
    }
}