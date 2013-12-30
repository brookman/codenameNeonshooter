package eu32k.neonshooter.core.entitySystem.system;

import eu32k.gdx.artemis.base.Aspect;
import eu32k.gdx.artemis.base.ComponentMapper;
import eu32k.gdx.artemis.base.Entity;
import eu32k.gdx.artemis.base.systems.EntityProcessingSystem;
import eu32k.gdx.artemis.extension.component.ActorComponent;
import eu32k.neonshooter.core.Neon;
import eu32k.neonshooter.core.entitySystem.component.ControlableComponent;
import eu32k.neonshooter.core.entitySystem.component.VelocityComponent;

public class ControlSystem extends EntityProcessingSystem {

	private ComponentMapper<VelocityComponent> velocityMapper;
	private ComponentMapper<ActorComponent> actorMapper;

	@SuppressWarnings("unchecked")
	public ControlSystem() {
		super(Aspect.getAspectForAll(VelocityComponent.class, ControlableComponent.class));
	}

	@Override
	protected void initialize() {
		velocityMapper = world.getMapper(VelocityComponent.class);
		actorMapper = world.getMapper(ActorComponent.class);
	}

	@Override
	protected void process(Entity e) {
		VelocityComponent velocityComponent = velocityMapper.get(e);

		velocityComponent.velocity.set(0, 0);
		if (Neon.controls.up) {
			velocityComponent.velocity.add(0, 1);
		}
		if (Neon.controls.down) {
			velocityComponent.velocity.add(0, -1);
		}
		if (Neon.controls.left) {
			velocityComponent.velocity.add(-1, 0);
		}
		if (Neon.controls.right) {
			velocityComponent.velocity.add(1, 0);
		}
		velocityComponent.velocity.nor().scl(200);

		if (actorMapper.has(e)) {
			actorMapper.get(e).actor.setRotation(velocityComponent.velocity.angle());
		}
	}
}
