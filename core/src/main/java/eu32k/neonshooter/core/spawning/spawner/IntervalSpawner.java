package eu32k.neonshooter.core.spawning.spawner;

import com.badlogic.gdx.maps.MapProperties;

import eu32k.gdx.artemis.base.Entity;
import eu32k.gdx.artemis.base.World;

public class IntervalSpawner implements Spawner {
   private float timer = 0;
   private float spawnTime = 1f;

   public IntervalSpawner init(float spawnTime) {
      this.spawnTime = spawnTime;
      return this;
   }

   @Override
   public boolean spawns(World world, Entity entity) {
      timer -= world.getDelta();
      if (timer <= 0) {
         timer += spawnTime;
         return true;
      }
      return false;
   }

   @Override
   public void activate() {
      timer = 0f;
   }

   @Override
   public void deactivate() {

   }

   @Override
   public void init(MapProperties properties, String prefix) {

   }

}
