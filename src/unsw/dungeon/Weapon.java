package unsw.dungeon;

/**
 * Weapon interface 
 * 
 * An interface for all weapons
 * 
 * @author Frank Merriman, The Tran
 */
public interface Weapon {
    /**
     * Checks whether a weapon has no uses remaining
     * @return True if weapon has no uses. Otherwise, false
     */
    public boolean isBroken();
    /**
     * Use the weapon, taking away one of its remaining uses
     */
    public void useWeapon();
}