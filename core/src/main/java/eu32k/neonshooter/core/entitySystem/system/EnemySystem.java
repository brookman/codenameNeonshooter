package eu32k.neonshooter.core.entitySystem.system;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import eu32k.gdx.artemis.base.Aspect;
import eu32k.gdx.artemis.base.Entity;
import eu32k.gdx.artemis.base.managers.GroupManager;
import eu32k.gdx.artemis.base.systems.EntityProcessingSystem;
import eu32k.gdx.artemis.extension.component.PhysicsComponent;
import eu32k.neonshooter.core.entitySystem.common.Groups;
import eu32k.neonshooter.core.entitySystem.common.Mappers;
import eu32k.neonshooter.core.entitySystem.component.EnemyComponent;
import eu32k.neonshooter.core.entitySystem.component.EnemyComponent.EnemyBehaviour;

public class EnemySystem extends EntityProcessingSystem {

   private Vector2 nearestPosition = new Vector2();
   private Vector2 force = new Vector2();

   @SuppressWarnings("unchecked")
   public EnemySystem() {
      super(Aspect.getAspectForAll(EnemyComponent.class, PhysicsComponent.class));
   }

   @Override
   protected void process(Entity e) {
      Body body = Mappers.physicsMapper.get(e).body;

      EnemyComponent enemyComponent = Mappers.enemyMapper.get(e);

      if (enemyComponent.behaviour == EnemyBehaviour.PASSIVE) {
         return;
      }

      float nearest = Float.MAX_VALUE;
      nearestPosition.set(0, 0);
      for (Entity player : world.getManager(GroupManager.class).getEntities(Groups.PLAYER)) {
         Vector2 playerPosition = Mappers.physicsMapper.get(player).body.getPosition();
         float dist = playerPosition.dst2(body.getPosition());
         if (dist <= nearest) {
            nearest = dist;
            nearestPosition.set(playerPosition);
         }

      }

      force.set(nearestPosition);
      force.sub(body.getPosition());

      if (enemyComponent.behaviour == EnemyBehaviour.FOLLOW) {
         force.nor().scl(1.3f);
      } else if (enemyComponent.behaviour == EnemyBehaviour.FLEE) {
         force.nor().scl(-1.3f);
      } else if (enemyComponent.behaviour == EnemyBehaviour.WANDER) {
         enemyComponent.currentWanderDirection += (Math.random() - 0.5f) * world.delta * 500.0f;
         force.setAngle(enemyComponent.currentWanderDirection);
         force.nor().scl(1.3f);
      }

      body.applyForceToCenter(force, true);
      body.setLinearDamping(4);
      // body.setTransform(body.getPosition(), force.angle() *
      // MathUtils.degRad);
      // body.setAngularVelocity(0);
   }
}