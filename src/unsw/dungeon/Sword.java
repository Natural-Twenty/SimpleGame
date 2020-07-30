package unsw.dungeon;

/**
 * A sword entity that can be used by player as a weapon to 
 * defeat enemies
 * @authour Frank Merriman, The Tran
 */
public class Sword extends Entity implements Weapon{

    private int durability;

    /**
     * Creates a new sword entity at coordinates
     * @param x axis location
     * @param y axis location
     */
    public Sword(int x, int y) {
        super(x, y);
        this.durability = 5;
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
            if (player.hasSword() == false) {
                player.equip(this);
                setDisplayOnScreen(false);
            }
        }
    }

}