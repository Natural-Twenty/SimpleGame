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

    public void addSubGoal(Goal goal) {
        subGoals.add(goal);
    }
}