package eu32k.neonshooter.core.spawning.spawner;

import com.badlogic.gdx.maps.MapProperties;

import eu32k.gdx.artemis.base.Entity;
import eu32k.gdx.artemis.base.World;

public interface Spawner {
   public boolean spawns(World world, Entity entity);

   public void activate();

   public void deactivate();

   public void init(MapProperties properties, String prefix);
}
