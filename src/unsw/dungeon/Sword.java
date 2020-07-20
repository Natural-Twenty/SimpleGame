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
    public int getDurability() {
        return durability;
    }

}