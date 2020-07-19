package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

public class GoalOR implements Goal{
    
    List<Goal> subGoals;

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

    public void addSubGoal(Goal goal) {
        subGoals.add(goal);
    }
}