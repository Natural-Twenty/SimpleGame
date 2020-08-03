package unsw.dungeon;

import java.util.List;
import java.util.ArrayList;

/**
 * One type of goal that is completed simply by standing on it
 * @author Frank Merriman, The Tran
 */
public class Exit extends Entity implements Goal, Subject {

    private boolean completed;
    private List<Observer> listObservers;
    private GoalAND parentAND;

    /**
     * Creates a new exit entity at the given coordinates
     * @param x axis location
     * @param y axis location
     */
    public Exit(int x, int y) {
        super(x, y);
        completed = false;
        listObservers = new ArrayList<>();
        parentAND = null;
    }

    @Override
    public void onCollide(Entity e) {
        if (e instanceof Player && canComplete()) {
            completed = true;
            updateObservers();
        }
    }

    public void setParentAND(GoalAND parent) {
        parentAND = parent;
    }

    /**
     * Checks if Exits parent GoalAND (if it has one), has completed all
     * other objectives, if all non-exit goals are complete, then we are allowed
     * to complete this exit
     */
    public boolean canComplete() {
        if (parentAND != null && parentAND.isCompleteExceptExit(this) == false) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isComplete() {
        return completed;
    }

    /**
     * Remove an observer from a subject.
     * @param o An observer to remove
     */
    @Override
    public void detach(Observer o) {
        listObservers.remove(o);
    }

    /**
     * Add an observer to the subject.
     * @param o An observer to add
     */
    @Override
    public void attach(Observer o) {
        listObservers.add(o);
    }

    /**
     * Notify all observers that are attached to subject
     */
    @Override
    public void updateObservers() {
        for (Observer o : listObservers) {
            o.update(this);
        }
    }

    @Override
    public String getGoalString(int indent) {
        String str = "";
        for (int i = 0; i < indent; i++) {
            str = str + "--";
        }
        str = str+"> Get to the exit";
        return str;
        
    }
    
}