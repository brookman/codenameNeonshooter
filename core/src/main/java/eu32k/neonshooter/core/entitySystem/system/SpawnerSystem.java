package eu32k.neonshooter.core.entitySystem.system;

import com.badlogic.gdx.Gdx;

import eu32k.gdx.artemis.base.Aspect;
import eu32k.gdx.artemis.base.Entity;
import eu32k.gdx.artemis.base.systems.EntityProcessingSystem;
import eu32k.neonshooter.core.Neon;
import eu32k.neonshooter.core.entitySystem.common.Mappers;
import eu32k.neonshooter.core.entitySystem.component.PositionComponent;
import eu32k.neonshooter.core.entitySystem.component.SpawnerComponent;
import eu32k.neonshooter.core.entitySystem.factory.EntityFactory;
import eu32k.neonshooter.core.fx.midi.ControlTrack;
import eu32k.neonshooter.core.fx.midi.ControlTracks;
import eu32k.neonshooter.core.fx.midi.NoteInfo;

public class SpawnerSystem extends EntityProcessingSystem {

   final private EntityFactory factory;

   public SpawnerSystem(EntityFactory factory) {
      super(Aspect.getAspectForAll(PositionComponent.class, SpawnerComponent.class));
      this.factory = factory;
   }

   @Override
   protected void process(Entity e) {
      // Gdx.app.log("SpawnerSystem", "Processing spawner");
      SpawnerComponent spawner = Mappers.spawnerMapper.get(e);
      ControlTracks tracks = Neon.music.getControlTracks();
      ControlTrack track = tracks.getTrack(spawner.track);
      if (track == null) {
         return;
      }
      boolean canSpawn = false;
      switch (spawner.source) {
      case AnyNote:
         canSpawn = anyNoteEnabled(spawner, track);
         break;
      case Note:
         canSpawn = noteEnabled(spawner, track);
      default:
         break;
      }
      if (canSpawn) {
         trySpawn(e, spawner);
         spawner.spawnedLastTime = true;
      } else {
         spawner.spawnedLastTime = false;
         spawner.timer = 0f;
      }
   }

   private void trySpawn(Entity e, SpawnerComponent spawner) {
      if (!canSpawn(spawner)) {
         return;
      }
      spawner.timer -= world.delta;
      if (spawner.timer <= 0f) {
         spawner.timer = spawner.spawnFrequency;
         spawn(e, spawner);
      }
   }

   private void spawn(Entity e, SpawnerComponent spawner) {
      Gdx.app.log("SpawnerSystem", "Spawn");
      PositionComponent position = Mappers.positionMapper.get(e);
      factory.createEnemyShip(position.x, position.y).addToWorld();
   }

   private boolean canSpawn(SpawnerComponent spawner) {
      return spawner.spawnContinously || !spawner.spawnedLastTime;
   }

   private boolean anyNoteEnabled(SpawnerComponent spawner, ControlTrack track) {
      return track.anyNotePlaying();
   }

   private boolean noteEnabled(SpawnerComponent spawner, ControlTrack track) {
      NoteInfo note = track.notes().get(spawner.note);
      if (note != null) {
         return note.on;
      }
      return false;
   }

}
