package eu32k.neonshooter.core.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import eu32k.neonshooter.core.Neon;

public class LevelManager {
   private LevelInfo currentLevel;

   private Map<String, LevelInfo> levels;
   private List<LevelInfo> arcadeLevels;

   private OrthogonalTiledMapRenderer mapRenderer;

   public LevelManager() {
      levels = new HashMap<String, LevelInfo>();
      arcadeLevels = new ArrayList<LevelInfo>();
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

      JsonValue arcadeLevelsNode = root.get("arcadeLevels");
      arcadeLevelsNode = arcadeLevelsNode.child();
      while (arcadeLevelsNode != null) {
         String key = arcadeLevelsNode.asString();
         if (levels.containsKey(key)) {
            arcadeLevels.add(levels.get(key));
         } else {
            Gdx.app.error("LevelManager", "Cannot find arcade level '" + key + "'");
         }
         arcadeLevelsNode = arcadeLevelsNode.next();
      }
   }

   public void loadLevel() {
      Neon.game.map = Neon.assets.manager.get(currentLevel.file, TiledMap.class);
      Neon.game.level().load(Neon.game.map);
      mapRenderer = new OrthogonalTiledMapRenderer(Neon.game.map, 1f / 512f);
   }

   public void prepareLevel(String id) {
      prepareLevel(levels.get(id));
   }

   public void prepareLevel(LevelInfo levelInfo) {
      if (levelInfo == null) {
         return;
      }
      currentLevel = levelInfo;
      Neon.assets.manager.load(levelInfo.file, TiledMap.class);
   }

   public void prepareAnyArcadeLevel() {
      if (arcadeLevels.size() > 1) {
         prepareLevel(arcadeLevels.get(Neon.random.nextInt(arcadeLevels.size())));
      } else if (arcadeLevels.size() == 1) {
         prepareLevel(arcadeLevels.get(0));
      }
   }

   public TiledMapRenderer getMapRenderer() {
      return mapRenderer;
   }

}
