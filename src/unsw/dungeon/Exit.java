package unsw.dungeon;

import java.util.List;
import java.util.ArrayList;

public class Exit extends Entity implements Goal, Subject {

    private boolean completed;
    private List<Observer> listObservers;

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

    @Override
    public void detach(Observer o) {
        listObservers.remove(o);
    }

    @Override
    public void attach(Observer o) {
        listObservers.add(o);
    }

    @Override
    public void updateObservers() {
        for (Observer o : listObservers) {
            o.update();
        }
    }
    
}