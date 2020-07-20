package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

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

        JSONArray jsonEntities = json.getJSONArray("entities");

        for (int i = 0; i < jsonEntities.length(); i++) {
            loadEntity(dungeon, jsonEntities.getJSONObject(i));
        }
        //in here set a loop for linking observers to respective subjects (e.g goals to dungeon, enemies to player)
        //possible way to add goals
        // - loop thru dungeons entities and check if instanceof goalType (i.e Exit) and add to dungeon.addGoal();
        JSONObject jsonGoals = json.getJSONObject("goal-condition");
        loadGoal(dungeon, jsonGoals);

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
        // TODO Handle other possible entities
        }
        dungeon.addEntity(entity);
    }

    //This is a prototype for adding goals in -> finish in milestone 3
    private void loadGoal (Dungeon dungeon, JSONObject json) {

        String goal = json.getString("goal");
        JSONArray subgoals = json.getJSONArray("subgoals");

        for (int i = 0; i < subgoals.length(); i++) {
            loadGoal(dungeon, subgoals.getJSONObject(i));
        } 


        Goal newGoal = null;
        switch (goal) {
        case "AND":

        case "OR":

        case "exit":
            
        case "enemies":

        case "treasure":

        case "boulders":

        }
    }

    public abstract void onLoad(Player player); // This used to be Entity player change back if breaks

    public abstract void onLoad(Wall wall);

    // TODO Create additional abstract methods for the other entities

}
