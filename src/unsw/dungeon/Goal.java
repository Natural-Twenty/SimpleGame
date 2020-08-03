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

    /**
     * returns the current goal object in string form
     * if the goal has subgoals, they are appended via recursion
     * @return string
     */
    public String getGoalString();
    
}