package eu32k.neonshooter.core.spawning.spawner;

import eu32k.gdx.artemis.base.Entity;

public interface Spawner {
   public boolean spawns(Entity entity);

   public void activate();

   public void deactivate();
}
