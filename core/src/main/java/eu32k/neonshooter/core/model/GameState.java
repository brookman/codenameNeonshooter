package eu32k.neonshooter.core.model;

import com.badlogic.gdx.maps.tiled.TiledMap;

import eu32k.neonshooter.core.model.Assets.SoundSet;

public class GameState {
   public String nextLevel = "levels/test.tmx";
   public String soundFile = null;
   public String controlFile = null;
   public float timeScale = 1f;

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

   public void setSoundset(SoundSet set) {
      soundFile = set.soundFile;
      controlFile = set.controlFile;
   }
}
