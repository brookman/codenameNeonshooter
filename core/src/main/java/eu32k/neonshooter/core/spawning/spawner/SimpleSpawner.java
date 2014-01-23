package eu32k.neonshooter.core.spawning.spawner;

import com.badlogic.gdx.maps.MapProperties;

import eu32k.gdx.artemis.base.Entity;
import eu32k.gdx.artemis.base.World;

/**
 * The SimpleSpawner class simply spawns every time when it is triggered. Use
 * this for single triggers like NoteOn or NoteOff. When you use it on a
 * continuous trigger (NotePlaying, Controller), enemies might spawn every
 * frame.
 * 
 * @author atombrot
 * 
 */
public class SimpleSpawner implements Spawner {

   @Override
   public boolean spawns(World world, Entity entity) {
      return true;
   }

   @Override
   public void activate() {

   }

   @Override
   public void deactivate() {

   }

   @Override
   public void init(MapProperties properties, String prefix) {

   }

}
