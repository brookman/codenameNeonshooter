package eu32k.neonshooter.core.entitySystem.system;

import com.badlogic.gdx.physics.box2d.Body;

import eu32k.gdx.artemis.base.Aspect;
import eu32k.gdx.artemis.base.Entity;
import eu32k.gdx.artemis.base.systems.EntityProcessingSystem;
import eu32k.gdx.artemis.extension.component.ActorComponent;
import eu32k.gdx.artemis.extension.component.PhysicsComponent;
import eu32k.neonshooter.core.entitySystem.common.Mappers;
import eu32k.neonshooter.core.entitySystem.component.DeactivateComponent;

public class DeactivateSystem extends EntityProcessingSystem {

   @SuppressWarnings("unchecked")
   public DeactivateSystem() {
      super(Aspect.getAspectForAll(DeactivateComponent.class, PhysicsComponent.class, ActorComponent.class));
   }

   @Override
   protected void process(Entity e) {
      Mappers.deactivateMapper.get(e).pool.free(e);
      Body body = Mappers.physicsMapper.get(e).body;
      body.setActive(false);
      Mappers.actorMapper.get(e).actor.setVisible(false);
      e.removeComponent(DeactivateComponent.class);
      e.disable();
   }
}
