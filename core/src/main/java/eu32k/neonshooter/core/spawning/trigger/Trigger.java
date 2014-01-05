package eu32k.neonshooter.core.spawning.trigger;

import eu32k.gdx.artemis.base.Entity;

public interface Trigger {
   public boolean triggers(Entity e);

   void reset();
}
