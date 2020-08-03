package unsw.dungeon;

public class Pickaxe extends Entity implements Weapon {
    private int durability;

    public Pickaxe(int x, int y) {
        super(x,y);
        this.durability = 5;
    }

    public void useWeapon() {
        durability--;
    }

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
            if (player.getPickaxe() == null) {
                player.equip(this);
                setDisplayOnScreen(false);
            }
        }
    }
}