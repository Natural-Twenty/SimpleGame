package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

public class Treasure extends Entity implements Goal, Subject{
    private boolean collected;
    private List<Observer> listObservers;

    public Treasure(int x, int y) {
        super(x, y);
        collected = false;
        listObservers = new ArrayList<>();
    }

    @Override
    public boolean isComplete() {
        return collected;
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
            o.update(this);
        }
    }

    @Override
    public void onCollide(Entity e) {
        if (e instanceof Player) {
            Player p = (Player) e;
            collected = true;
            p.equip(this);
            updateObservers();
        }
    }
}