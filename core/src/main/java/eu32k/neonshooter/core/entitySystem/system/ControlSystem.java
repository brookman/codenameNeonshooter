package eu32k.neonshooter.core.entitySystem.system;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import eu32k.gdx.artemis.base.Aspect;
import eu32k.gdx.artemis.base.Entity;
import eu32k.gdx.artemis.base.systems.EntityProcessingSystem;
import eu32k.gdx.artemis.extension.component.PhysicsComponent;
import eu32k.neonshooter.core.Neon;
import eu32k.neonshooter.core.entitySystem.common.Mappers;
import eu32k.neonshooter.core.entitySystem.component.ControllableComponent;

public class ControlSystem extends EntityProcessingSystem {

   private Vector2 force = new Vector2();

   @SuppressWarnings("unchecked")
   public ControlSystem() {
      super(Aspect.getAspectForAll(PhysicsComponent.class, ControllableComponent.class));
   }

   @Override
   protected void process(Entity e) {
      Body body = Mappers.physicsMapper.get(e).body;

      force.set(0, 0);

      if (Neon.controls.t.isPressed || Neon.controls.mousePressed) {
         Neon.game.targetTimeScale = 0.45f;
      } else {
         Neon.game.targetTimeScale = 1f;
      }

      if (Neon.controls.up) {
         force.add(0, 1);
      }
      if (Neon.controls.down) {
         force.add(0, -1);
      }
      if (Neon.controls.left) {
         force.add(-1, 0);
      }
      if (Neon.controls.right) {
         force.add(1, 0);
      }
      force.nor();

      if (Neon.controls.padLeft.len() > 0) {
         force.set(Neon.controls.padLeft);
      }

      force.scl(2.7f);

      if (force.len2() > 0.01f) {
         body.applyForceToCenter(force, true);
         body.setLinearDamping(7);
         body.setTransform(body.getPosition(), force.angle() * MathUtils.degRad);
      } else {
         body.setLinearDamping(10);
      }
      body.setAngularVelocity(0);
   }
}