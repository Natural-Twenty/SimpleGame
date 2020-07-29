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

    /**
     * Creates a new exit entity at the given coordinates
     * @param x axis location
     * @param y axis location
     */
    public Exit(int x, int y) {
        super(x, y);
        completed = false;
        listObservers = new ArrayList<>();
    }

    @Override
    public void onCollide(Entity e) {
        if (e instanceof Player) {
            completed = true; //doesnt address problem of exit being always last in multi-goal
            updateObservers();
        }
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
    
}