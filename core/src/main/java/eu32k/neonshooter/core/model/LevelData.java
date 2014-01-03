package eu32k.neonshooter.core.model;

import com.badlogic.gdx.physics.box2d.World;

public class LevelData {
   private World world;
   private ExtendedMap level;

   public boolean isLoaded() {
      return level != null;
   }

   public void load(ExtendedMap level) {
      if (isLoaded()) {
         unload();
      }

      this.level = level;
      initEntities();
   }

   public void reload() {
      initEntities();
   }

   public void unload() {

   }

   protected void initEntities() {

   }
}
