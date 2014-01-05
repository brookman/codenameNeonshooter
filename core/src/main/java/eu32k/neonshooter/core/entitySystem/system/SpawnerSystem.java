package eu32k.neonshooter.core.entitySystem.system;

import com.badlogic.gdx.Gdx;

import eu32k.gdx.artemis.base.Aspect;
import eu32k.gdx.artemis.base.Entity;
import eu32k.gdx.artemis.base.systems.EntityProcessingSystem;
import eu32k.neonshooter.core.entitySystem.common.Mappers;
import eu32k.neonshooter.core.entitySystem.component.PositionComponent;
import eu32k.neonshooter.core.entitySystem.component.SpawnerComponent;
import eu32k.neonshooter.core.entitySystem.factory.EntityFactory;
import eu32k.neonshooter.core.spawning.spawner.Spawner;
import eu32k.neonshooter.core.spawning.trigger.Trigger;

public class SpawnerSystem extends EntityProcessingSystem {

   final private EntityFactory factory;

   @SuppressWarnings("unchecked")
   public SpawnerSystem(EntityFactory factory) {
      super(Aspect.getAspectForAll(PositionComponent.class, SpawnerComponent.class));
      this.factory = factory;
   }

   @Override
   protected void process(Entity e) {
      SpawnerComponent spawnerComponent = Mappers.spawnerMapper.get(e);
      Trigger trigger = spawnerComponent.trigger;
      Spawner spawner = spawnerComponent.spawner;
      if (spawner == null || trigger == null) {
         return;
      }
      if (trigger.triggers(world, e)) {
         if (!spawnerComponent.triggeredLastUpdate) {
            spawnerComponent.triggeredLastUpdate = true;
            spawner.activate();
         }
         if (spawner.spawns(world, e)) {
            spawn(e, spawnerComponent);
         }
      } else {
         if (spawnerComponent.triggeredLastUpdate) {
            spawner.deactivate();
         }
         spawnerComponent.triggeredLastUpdate = false;
      }
   }

   private void spawn(Entity e, SpawnerComponent spawner) {
      Gdx.app.log("SpawnerSystem", "Spawn");
      PositionComponent position = Mappers.positionMapper.get(e);
      factory.createEnemyShip(position.x, position.y).addToWorld();
   }

}
