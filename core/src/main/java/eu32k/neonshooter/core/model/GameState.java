package eu32k.neonshooter.core.model;

import com.badlogic.gdx.maps.tiled.TiledMap;

public class GameState {
   public String nextLevel = "levels/test.tmx";
   public float timeScale = 1f;
   public float targetTimeScale = 1f;

   private LevelData level;
   public TiledMap map;

   public GameState() {
      level = new LevelData();
   }

   public LevelData level() {
      return level;
   }

   public void create() {

   }
}
