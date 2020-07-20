package unsw.dungeon;

/**
 * An observer interface
 * 
 * An interface for all Observers
 * @author Frank Merriman, The Tran
 */

public interface Observer {
    /**
     * Behaves differently based on the Object passed
     * @param o An object to update
     */
    public void update(Object o);
}