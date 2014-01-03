package eu32k.neonshooter.core.model;


public class GameState {
   public String nextLevel = "levels/test.tmx";
   public float timeScale = 1f;
   public float targetTimeScale = 1f;

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
