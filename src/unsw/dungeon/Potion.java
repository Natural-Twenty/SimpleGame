package unsw.dungeon;

import java.util.List;

public class Potion extends Entity implements Weapon, Observer {

    int durability;

    /**
     * Potion lasts 10 turns once picked up
     * @param x
     * @param y
     */
    public Potion(int x, int y) {
        super(x, y);
        this.durability = 10;
    }

    @Override
    public void update(Object o) {
        if (o instanceof Player) {
            update( (Player) o);
        }
    }

    @Override
    public void update(Player player) {
        List<Entity> inventory = player.getInventory();

        if (inventory.contains(this)) {
            useWeapon();
        }
    }

    @Override
    public void useWeapon() {
        durability--;
    }

    @Override
    public int getDurability() {
        return durability;
    }
}