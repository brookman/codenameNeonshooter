package eu32k.neonshooter.core.entitySystem.system;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import eu32k.gdx.artemis.base.Aspect;
import eu32k.gdx.artemis.base.Entity;
import eu32k.gdx.artemis.base.systems.EntityProcessingSystem;
import eu32k.gdx.artemis.extension.component.PhysicsComponent;
import eu32k.neonshooter.core.Neon;
import eu32k.neonshooter.core.entitySystem.common.Mappers;
import eu32k.neonshooter.core.entitySystem.component.ControllableComponent;

public class ControlSystem extends EntityProcessingSystem {

   @SuppressWarnings("unchecked")
   public ControlSystem() {
      super(Aspect.getAspectForAll(PhysicsComponent.class, ControllableComponent.class));
   }

   @Override
   protected void process(Entity e) {
      PhysicsComponent physicsComponent = Mappers.physicsMapper.get(e);

      Vector2 velocity = new Vector2();

      if (Neon.controls.up) {
         velocity.add(0, 1);
      }
      if (Neon.controls.down) {
         velocity.add(0, -1);
      }
      if (Neon.controls.left) {
         velocity.add(-1, 0);
      }
      if (Neon.controls.right) {
         velocity.add(1, 0);
      }
      velocity.nor().scl(2.5f);

      if (Neon.controls.padLeft.len() > 0) {
         velocity.set(Neon.controls.padLeft);
         velocity.scl(2.5f);
      }

      if (velocity.len2() > 0.01f) {
         physicsComponent.body.setLinearVelocity(velocity);
         physicsComponent.body.setTransform(physicsComponent.body.getPosition(), velocity.angle() * MathUtils.degRad);
      }
      physicsComponent.body.setAngularVelocity(0);
   }
}