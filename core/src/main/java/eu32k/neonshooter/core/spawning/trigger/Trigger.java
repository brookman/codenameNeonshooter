package eu32k.neonshooter.core.spawning.trigger;

import eu32k.gdx.artemis.base.Entity;
import eu32k.gdx.artemis.base.World;

public interface Trigger {
   public boolean triggers(World world, Entity e);

   void reset();
}
