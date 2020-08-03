package unsw.dungeon;


/**
 * A potion entity that makes a player impervious to attacks
 * for 10 turns and also can destroy enemies on contact
 * @author Frank Merriman, The Tran
 */
public class Potion extends Entity implements Weapon, Observer {

    int durability;

    /**
     * Potion lasts 10 turns once picked up
     * @param x
     * @param y
     */
    public Potion(int x, int y) {
        super(x, y);
        this.durability = 11; //Need an extra durability over expected 
                             //since update runs after equiping
    }

    @Override
    public void update(Object o) {
        if (o instanceof Player) {
            update( (Player) o);
        }
    }

    /**
     * Called everytime the observer pull is of type Player
     * @param player
     */
    public void update(Player player) {
        useWeapon();
        if (isBroken()) {
            player.unequip(this);
        }
    }

    @Override
    public void useWeapon() {
        durability--;
    }

    @Override
    public boolean isBroken() {
        if (durability <= 0) {
            return true;
        }
        return false;
    }

    @Override
    public void onCollide(Entity e) {
        if (e instanceof Player) {
            Player player = (Player) e;
            if (player.getPotion() == null) {
                player.equip(this);
                setDisplayOnScreen(false);
            }
        }
    }
}