package unsw.dungeon;

public class Sword extends Entity implements Weapon{

    private int durability;

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
            }
        }
    }

}