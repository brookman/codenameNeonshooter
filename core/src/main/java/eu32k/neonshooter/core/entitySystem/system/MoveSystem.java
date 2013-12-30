package eu32k.neonshooter.core.entitySystem.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;

import eu32k.gdx.artemis.base.Aspect;
import eu32k.gdx.artemis.base.ComponentMapper;
import eu32k.gdx.artemis.base.Entity;
import eu32k.gdx.artemis.base.systems.EntityProcessingSystem;
import eu32k.gdx.artemis.extension.component.ActorComponent;
import eu32k.neonshooter.core.entitySystem.component.VelocityComponent;

public class MoveSystem extends EntityProcessingSystem {

	private ComponentMapper<VelocityComponent> velocityMapper;
	private ComponentMapper<ActorComponent> actorMapper;

	@SuppressWarnings("unchecked")
	public MoveSystem() {
		super(Aspect.getAspectForAll(VelocityComponent.class, ActorComponent.class));
	}

	@Override
	protected void initialize() {
		velocityMapper = world.getMapper(VelocityComponent.class);
		actorMapper = world.getMapper(ActorComponent.class);
	}

	@Override
	protected void process(Entity e) {
		VelocityComponent velocityComponent = velocityMapper.get(e);
		ActorComponent actorComponent = actorMapper.get(e);

		Actor actor = actorComponent.actor;

		actor.setX(actor.getX() + velocityComponent.velocity.x * Gdx.graphics.getDeltaTime());
		actor.setY(actor.getY() + velocityComponent.velocity.y * Gdx.graphics.getDeltaTime());
	}
}
