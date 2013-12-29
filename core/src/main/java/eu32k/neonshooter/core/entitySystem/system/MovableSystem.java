package eu32k.neonshooter.core.entitySystem.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;

import eu32k.gdx.artemis.base.Aspect;
import eu32k.gdx.artemis.base.ComponentMapper;
import eu32k.gdx.artemis.base.Entity;
import eu32k.gdx.artemis.base.systems.EntityProcessingSystem;
import eu32k.gdx.artemis.extension.component.ActorComponent;
import eu32k.neonshooter.core.entitySystem.component.MovableComponent;

public class MovableSystem extends EntityProcessingSystem {

	private ComponentMapper<MovableComponent> movableMapper;
	private ComponentMapper<ActorComponent> actorMapper;

	@SuppressWarnings("unchecked")
	public MovableSystem() {
		super(Aspect.getAspectForAll(MovableComponent.class));
	}

	@Override
	protected void initialize() {
		movableMapper = world.getMapper(MovableComponent.class);
		actorMapper = world.getMapper(ActorComponent.class);
	}

	@Override
	protected void process(Entity e) {
		MovableComponent movableComponent = movableMapper.get(e);
		ActorComponent actorComponent = actorMapper.get(e);

		Actor actor = actorComponent.actor;

		actor.setX(actor.getX() + movableComponent.speedX * Gdx.graphics.getDeltaTime());
		actor.setY(actor.getY() + movableComponent.speedY * Gdx.graphics.getDeltaTime());
	}
}
