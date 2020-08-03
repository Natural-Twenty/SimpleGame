package unsw.dungeon;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.List;

public class Bomb extends Entity implements Observer{
    private Dungeon dungeon;
    private IntegerProperty fuseTimer;
    private boolean detonated;
    public Bomb(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
        this.fuseTimer = new SimpleIntegerProperty(-1);
        this.detonated = false;
    }

    @Override
    public void onCollide(Entity e) {
        if (e instanceof Player) {
            Player player = (Player) e;
            playerCollision(player);
        }
    }

    private void playerCollision(Player player) {
        if (player.getBomb() == null) {
            player.equip(this);
            setDisplayOnScreen(false);
        }
    }

    public void ignite() {
        fuseTimer.set(3);
    }

    public IntegerProperty getFuseTimer() {
        return fuseTimer;
    }

    public boolean hasDetonated() {
        return detonated;
    }


    public void update(Object obj) {
        if (obj instanceof Player) {
            Player player = (Player) obj;
            countdown(player);

        }
        
    }

    public void countdown(Player player) {
        if (fuseTimer.get() == 0) {
            explode();
        } else if (fuseTimer.get() != -1) {
            fuseTimer.set(fuseTimer.get() - 1);
        }
    }

    private void explode() {
        int [][] AoE = {{-1, -1}, {0, -1}, {1, -1},
                        {-1, 0}, {0, 0}, {1, 0},
                        {-1, 1}, {0, 1}, {1, 1}};
        
        for (int [] direction : AoE) {
            int x = direction[0] + getX();
            int y = direction[1] + getY();
            destroy(x, y);
        }
    }

    private void destroy(int x, int y) {
        List<Entity> entities = dungeon.getEntities(x, y);
        for (Entity e : entities) {
            if (e instanceof Hunter) {
                Hunter hunter = (Hunter) e;
                hunter.defeat();
                dungeon.removeEntity(hunter);
                hunter.setDisplayOnScreen(false);
            } else if (e instanceof Player) {
                Player player = (Player) e;
                dungeon.removeEntity(player);
                player.setDisplayOnScreen(false);
                dungeon.setlevelComplete(2);
            } else {
                dungeon.removeEntity(e);
                e.setDisplayOnScreen(false);
            }
        }
        fuseTimer.set(-1);
    }

    public void plant(int x, int y) {
        x().set(x);
        y().set(y);
        setDisplayOnScreen(true);
        dungeon.addEntity(this);
    }

}