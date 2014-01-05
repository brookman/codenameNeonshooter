package eu32k.neonshooter.core.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import eu32k.neonshooter.core.Neon;
import eu32k.neonshooter.core.rendering.BasicMapRenderer;

public class LevelManager {
   private LevelInfo currentLevel;

   private Map<String, LevelInfo> levels;
   private List<LevelInfo> arcadeLevels;

   private MapRenderer mapRenderer;

   public LevelManager() {
      levels = new HashMap<String, LevelInfo>();
      arcadeLevels = new ArrayList<LevelInfo>();
   }

   public List<LevelInfo> arcade() {
      return arcadeLevels;
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

   public void loadLevel(World box2dWorld) {
      Neon.game.map = new ExtendedMap(Neon.assets.manager.get(currentLevel.file, TiledMap.class));
      Neon.game.level().load(Neon.game.map);

      mapRenderer = new BasicMapRenderer(Neon.game.map, Neon.assets.manager.get("textures/line.png", Texture.class), box2dWorld);
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

   public MapRenderer getMapRenderer() {
      return mapRenderer;
   }
}
