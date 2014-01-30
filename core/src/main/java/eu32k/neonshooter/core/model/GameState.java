package eu32k.neonshooter.core.model;

import eu32k.neonshooter.core.utils.ValueFader;

public class GameState {
   public String nextLevel = "levels/test.tmx";
   public ValueFader timeScale = new ValueFader(0.8f);

   private LevelData level;
   public ExtendedMap map;

   public GameState() {
      level = new LevelData();
   }

   public LevelData level() {
      return level;
   }

   public void create() {

   }
}
