package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.List;

/**
 * Loads a dungeon from a .json file.
 *
 * By extending this class, a subclass can hook into entity creation. This is
 * useful for creating UI elements with corresponding entities.
 *
 * @author Robert Clifton-Everest
 *
 */
public abstract class DungeonLoader {

    private JSONObject json;

    public DungeonLoader(String filename) throws FileNotFoundException {
        json = new JSONObject(new JSONTokener(new FileReader("dungeons/" + filename)));
    }

    /**
     * Parses the JSON to create a dungeon.
     * @return
     */
    public Dungeon load() {
        int width = json.getInt("width");
        int height = json.getInt("height");

        Dungeon dungeon = new Dungeon(width, height);

        //Adds entities to dungeon
        JSONArray jsonEntities = json.getJSONArray("entities");
        for (int i = 0; i < jsonEntities.length(); i++) {
            loadEntity(dungeon, jsonEntities.getJSONObject(i));
        }
        
        //Creates a goal tree and adds to dungeon
        JSONObject jsonGoals = json.getJSONObject("goal-condition");
        dungeon.addGoal(loadGoal(dungeon, jsonGoals));

        //Initialise any enemies to track player
        loadEnemies(dungeon.getAllEntities(), dungeon.getPlayer());

        return dungeon;
    }

    private void loadEntity(Dungeon dungeon, JSONObject json) {
        String type = json.getString("type");
        int x = json.getInt("x");
        int y = json.getInt("y");

        Entity entity = null;
        switch (type) {
        case "player":
            Player player = new Player(dungeon, x, y);
            dungeon.setPlayer(player);
            onLoad(player);
            entity = player;
            break;

        case "wall":
            Wall wall = new Wall(x, y);
            onLoad(wall);
            entity = wall;
            break;

        case "exit":
            Exit exit = new Exit(x, y);
            exit.attach(dungeon);
            onLoad(exit);
            entity = exit;
            break;

        case "treasure":
            break;

        case "door":
            break;

        case "key":
            break;

        case "boulder":
            Boulder boulder = new Boulder(dungeon, x, y);
            onLoad(boulder);
            entity = boulder;
            break;

        case "switch":
            FloorSwitch floorSwitch = new FloorSwitch(dungeon, x, y);
            floorSwitch.attach(dungeon);
            onLoad(floorSwitch);
            entity = floorSwitch;
            break;

        case "portal":
            int id = json.getInt("id");
            Portal portal = new Portal(dungeon, x, y, id);
            onLoad(portal);
            entity = portal;
            break;

        case "enemy":
            Hunter hunter = new Hunter(dungeon, x, y);
            hunter.attach(dungeon);
            onLoad(hunter);
            entity = hunter;
            break;

        case "sword":
            break;

        case "invincibility":
            break;

        }
        dungeon.addEntity(entity);
    }

    //This is a prototype for adding goals in via kind of recursion?
    private Goal loadGoal (Dungeon dungeon, JSONObject json) {

        String goal = json.getString("goal");

        if (goal.equals("AND")) {
            GoalAND newGoal = new GoalAND();
            JSONArray subgoals = json.getJSONArray("subgoals");

            for (int i = 0; i < subgoals.length(); i++) {
                newGoal.addSubGoal(loadGoal(dungeon, subgoals.getJSONObject(i)));
            }
            return newGoal;

        } else if (goal.equals("OR")) {
            GoalOR newGoal = new GoalOR();
            JSONArray subgoals = json.getJSONArray("subgoals");

            for (int i = 0; i < subgoals.length(); i++) {
                newGoal.addSubGoal(loadGoal(dungeon, subgoals.getJSONObject(i)));
            }
            return newGoal;

        } else if (goal.equals("exit")) {
            Goal newGoal = null;
            List<Entity> entities = dungeon.getAllEntities();
            for (Entity e : entities) {
                if (e instanceof Exit) {
                    return newGoal = (Goal) e;
                }
            }
            return newGoal;

        } else if (goal.equals("enemies")) {
            GoalAND newGoal = new GoalAND();
            List<Entity> entities = dungeon.getAllEntities();
            for (Entity e : entities) {
                if (e instanceof Hunter) {
                    newGoal.addSubGoal( (Goal) e);
                }
            }
            return newGoal;

        } else if (goal.equals("treasure")) {
            GoalAND newGoal = new GoalAND();
            List<Entity> entities = dungeon.getAllEntities();
            // for (Entity e : entities) {
            //     if (e instanceof Treasure) {
            //         newGoal.addSubGoal( (Goal) e);
            //     }
            // }
            return newGoal;

        } else if (goal.equals("boulders")) {
            GoalAND newGoal = new GoalAND();
            List<Entity> entities = dungeon.getAllEntities();
            for (Entity e : entities) {
                if (e instanceof FloorSwitch) {
                    newGoal.addSubGoal( (Goal) e);
                }
            }
            return newGoal;
        }

        return null;
    }


    private void loadEnemies(List<Entity> entities, Player player) {
        if (player == null) {
            return;
        }
        for (Entity e : entities) {
            if (e instanceof Hunter) {
                player.attach( (Hunter) e);
            }
        }
    }

    public abstract void onLoad(Player player); // This used to be Entity player change back if breaks

    public abstract void onLoad(Wall wall);

    public abstract void onLoad(Exit exit);

    //public abstract void onLoad(Treasure treasure);

    //public abstract void onLoad(Door door);

    //public abstract void onLoad(Key key);

    public abstract void onLoad(Boulder boulder);

    public abstract void onLoad(FloorSwitch floorSwitch);

    public abstract void onLoad (Portal portal);

    public abstract void onLoad(Hunter hunter);

    //public abstract void onLoad(Sword sword);

    //public abstract void onLoad(Potion potion);


}
