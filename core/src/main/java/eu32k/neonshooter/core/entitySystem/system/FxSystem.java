package eu32k.neonshooter.core.entitySystem.system;

import com.badlogic.gdx.scenes.scene2d.Actor;

import eu32k.gdx.artemis.base.Aspect;
import eu32k.gdx.artemis.base.Entity;
import eu32k.gdx.artemis.base.systems.EntityProcessingSystem;
import eu32k.gdx.artemis.extension.component.ActorComponent;
import eu32k.neonshooter.core.Neon;
import eu32k.neonshooter.core.entitySystem.common.Mappers;

public class FxSystem extends EntityProcessingSystem {

   @SuppressWarnings("unchecked")
   public FxSystem() {
      super(Aspect.getAspectForAll(ActorComponent.class));
   }

   @Override
   protected void process(Entity e) {
      ActorComponent actorComponent = Mappers.actorMapper.get(e);
      Actor actor = actorComponent.actor;
      actor.setColor(Neon.config.primaryColor);
   }
}