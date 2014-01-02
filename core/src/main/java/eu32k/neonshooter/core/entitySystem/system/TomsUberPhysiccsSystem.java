package eu32k.neonshooter.core.entitySystem.system;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import eu32k.gdx.artemis.base.Aspect;
import eu32k.gdx.artemis.base.ComponentMapper;
import eu32k.gdx.artemis.base.Entity;
import eu32k.gdx.artemis.base.EntitySystem;
import eu32k.gdx.artemis.base.utils.ImmutableBag;
import eu32k.gdx.artemis.extension.component.ActorComponent;
import eu32k.gdx.artemis.extension.component.PhysicsComponent;

/**
 * Just a subtle hint that I can't edit the base physics system ;)
 * 
 * @author atombrot
 * 
 */
public class TomsUberPhysiccsSystem extends EntitySystem {

	private ComponentMapper<PhysicsComponent> pm;
	private ComponentMapper<ActorComponent> am;

	private float timeScale;

	private World box2dWorld;

	@SuppressWarnings("unchecked")
	public TomsUberPhysiccsSystem(World box2dWorld) {
		super(Aspect.getAspectForAll(PhysicsComponent.class, ActorComponent.class));
		this.box2dWorld = box2dWorld;
		timeScale = 1f;
	}

	@Override
	protected void initialize() {
		pm = world.getMapper(PhysicsComponent.class);
		am = world.getMapper(ActorComponent.class);
	}

	@Override
	protected boolean checkProcessing() {
		return true;
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		// TODO: Maybe the direct access to neon is a bit bad. A generic scale
		// variable would be better
		box2dWorld.step(1.0f / 45.0f * timeScale, 6, 2);
		for (int i = 0; i < entities.size(); i++) {
			process(entities.get(i));
		}
	}

	private void process(Entity e) {
		Actor actor = am.get(e).actor;
		PhysicsComponent physics = pm.get(e);

		actor.setX(physics.body.getPosition().x);
		actor.setY(physics.body.getPosition().y);
		actor.setRotation(physics.body.getAngle() * MathUtils.radiansToDegrees);
	}

	public float getTimeScale() {
		return timeScale;
	}

	public void setTimeScale(float timeScale) {
		this.timeScale = timeScale;
	}

}
