package eu32k.neonshooter.core.entitySystem.system;

import eu32k.gdx.artemis.base.Aspect;
import eu32k.gdx.artemis.base.Entity;
import eu32k.gdx.artemis.base.systems.EntityProcessingSystem;
import eu32k.neonshooter.core.entitySystem.component.PositionComponent;
import eu32k.neonshooter.core.entitySystem.component.SpawnerComponent;

public class SpawnerSystem extends EntityProcessingSystem {

   public SpawnerSystem() {
      super(Aspect.getAspectForAll(PositionComponent.class, SpawnerComponent.class));
   }

   @Override
   protected void process(Entity e) {

   }

}
