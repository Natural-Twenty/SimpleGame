package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

public class GoalAND implements Goal{

    List<Goal> subGoals;

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