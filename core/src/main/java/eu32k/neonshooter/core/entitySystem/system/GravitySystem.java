package eu32k.neonshooter.core.entitySystem.system;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import eu32k.gdx.artemis.base.Aspect;
import eu32k.gdx.artemis.base.Entity;
import eu32k.gdx.artemis.base.managers.GroupManager;
import eu32k.gdx.artemis.base.systems.EntityProcessingSystem;
import eu32k.gdx.artemis.extension.component.ActorComponent;
import eu32k.gdx.artemis.extension.component.PhysicsComponent;
import eu32k.neonshooter.core.entitySystem.common.Groups;
import eu32k.neonshooter.core.entitySystem.common.Mappers;
import eu32k.neonshooter.core.entitySystem.common.TempVector;

public class GravitySystem extends EntityProcessingSystem {

   private TempVector force = new TempVector();

   @SuppressWarnings("unchecked")
   public GravitySystem() {
      super(Aspect.getAspectForAll(PhysicsComponent.class));
   }

   @Override
   protected void process(Entity e) {
      Body body = Mappers.physicsMapper.get(e).body;

      if (body.getType() == BodyType.StaticBody) {
         return;
      }

      float radiusOfInfluence = 4;

      for (Entity attractor : world.getManager(GroupManager.class).getEntities(Groups.ATTRACTOR)) {
         ActorComponent actor = Mappers.actorMapper.get(attractor);
         force.set(actor.actor.getX(), actor.actor.getY());
         float dist = force.dst(body.getPosition());
         float strength = 1.0f - Math.min(dist, radiusOfInfluence) / radiusOfInfluence;

         force.set(actor.actor.getX() - body.getPosition().x, actor.actor.getY() - body.getPosition().y);
         if (force.len2() < 2) {
            force.nor().scl(body.getMass() * world.delta * 1000.0f * strength);
            body.applyForceToCenter(force, true);
         }
      }
   }
}
