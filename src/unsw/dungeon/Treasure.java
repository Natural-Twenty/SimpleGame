package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;
/**
 * A Treasure class to represent treasure in the dungeon.
 * 
 */
public class Treasure extends Entity implements Goal, Subject{
    private boolean collected;
    private List<Observer> listObservers;
    /**
     * A constructor to create a treasure entity in the dungeon
     * @param x x-coordinate for the treasure to appear in
     * @param y y-coordinate for the treasure to appear in
     */
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