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
                str = str + goal + "";
            } else {
                str = str + goal + " AND\n";
            }
        }

        return str;
    }
}