package eu32k.neonshooter.core.spawning.spawner;

import eu32k.gdx.artemis.base.Entity;

public interface Spawner {
   public boolean spawn(Entity entity);

   public void activate();

   public void deactivate();
}
