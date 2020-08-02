package unsw.dungeon;

import java.util.List;


/**
 * A entity that blocks all entities from passing
 * @author Frank Merriman, The Tran
 */
public class Wall extends Entity{

    /**
     * Creates a new wall at given location
     * @param x axis location
     * @param y axis location
     */
    public Wall(int x, int y) {
        super(x, y);
    }

    @Override
    public boolean isBarrier(Entity e) {
        if (e instanceof Player) {
            Player player = (Player) e;
            if (player.hasPickaxe()) {
                return false;
            }
        }
        return true;
    }
    @Override
    public void onCollide(Entity e) {
        if (e instanceof Player) {
            Player player = (Player) e;
            playerCollision(player);
        }
    }

    private void playerCollision(Player player) {
        List<Entity> inventory = player.getInventory();
        Pickaxe pickaxe = null;
        for (Entity e : inventory) {
            if (e instanceof Pickaxe) {
                pickaxe = (Pickaxe) e;
            }
        }
        if (pickaxe != null) {
            pickaxe.useWeapon();
            if (pickaxe.isBroken()) {
                player.unequip(pickaxe);
            }
        }
        player.removeWall(this);
        setDisplayOnScreen(false);
    }
}
