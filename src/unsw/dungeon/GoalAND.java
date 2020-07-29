package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates a goal that is only complete when all of
 * its children are also marked as complete
 * @author Frank Merriman, The Tran
 */
public class GoalAND implements Goal{

    List<Goal> subGoals;

    /**
     * Create a new composite goal
     */
    public GoalAND() {
        subGoals = new ArrayList<>();
    }

    @Override
    public boolean isComplete() {
        boolean allDone = true;

        for (Goal g : subGoals) {
            if(g.isComplete() == false) { //any goal not done means AND goal isn't finished
                allDone = false;
            }
        }
        
        return allDone;
    }

    /**
     * Calls isComplete on all direct children of the current GoalAND
     * that are not the given e
     * @param e instance of an exit object
     * @return true if all non-exit direct subgoals are complete
     */
    public boolean isCompleteExceptExit(Exit e) {

        for (Goal g : subGoals) {
            if (!g.equals(e)) {
                if (g.isComplete() == false) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Takes in an object of type goal and adds
     * it to the this.subGoals list
     * @param goal new goal to be added to subGoals list
     */
    public void addSubGoal(Goal goal) {
        subGoals.add(goal);
    }
}