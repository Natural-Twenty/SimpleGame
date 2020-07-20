package unsw.dungeon;

import java.util.List;

public class Key extends Entity {
    private int u_id;
    

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
            }
        }
    }

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