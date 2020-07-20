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
     * Takes in an object of type goal and adds
     * it to the this.subGoals list
     * @param goal new goal to be added to subGoals list
     */
    public void addSubGoal(Goal goal) {
        subGoals.add(goal);
    }
}