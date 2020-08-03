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

    @Override
    public String getGoalString(int indent) {
        String str = "";
        List<String> l = new ArrayList<String>();
        int size = subGoals.size();

        //Create a list of subgoals, ignoring duplicates
        for (int i = 0; i < size; i++) {
            String sG = subGoals.get(i).getGoalString(indent + 1);
            if (!l.contains(sG)) {
                l.add(sG);
            }
        }

        //Combine list into 1 string
        size = l.size();
        for (int i = 0; i < size; i++) {
            String goal = l.get(i);
            if (i == size - 1) {
                str = str + goal+"";
            } else {
                str = str + goal + " OR\n";
            }
        }

        return str;
    }
}