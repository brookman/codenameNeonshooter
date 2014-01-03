package eu32k.neonshooter.core.model;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public class LevelManager {
   private Map<String, LevelInfo> levels;

   public LevelManager() {
      levels = new HashMap<String, LevelInfo>();
   }

   public void create() {
      String content = Gdx.files.internal("levels/levels.json").readString();
      JsonReader reader = new JsonReader();
      JsonValue root = reader.parse(content);
      JsonValue levelsNode = root.get("levels");
      JsonValue child = levelsNode.child();
      Json json = new Json();
      while (child != null) {
         LevelInfo level = json.readValue(LevelInfo.class, child);
         Gdx.app.log("LevelManager", "Loaded level '" + level + "'");
         levels.put(level.id, level);
         child = child.next();
      }
   }

}
