package unsw.dungeon;

/**
 * Component part of the Goal composite pattern
 * @author Frank Merriman, The Tran
 */
public interface Goal {

    /**
     * Checks its objects completion status and returns a boolean
     * @return true if the goal has been completed
     */
    public boolean isComplete();

    
}