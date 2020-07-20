package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates a goal that is only complete when atleast one of
 * its children are also marked as complete
 * @author Frank Merriman, The Tran
 */
public class GoalOR implements Goal{
    
    List<Goal> subGoals;

    /**
     * Create a new composite goal
     */
    public GoalOR() {
        subGoals = new ArrayList<>();
    }

    @Override
    public boolean isComplete() {
        boolean allDone = false;

        for (Goal g : subGoals) {
            if(g.isComplete()) { //any goal is done then OR is done
                allDone = true;
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