package eu32k.neonshooter.core.entitySystem.factory;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.scenes.scene2d.Stage;

import eu32k.gdx.artemis.base.Entity;
import eu32k.gdx.artemis.extension.ExtendedWorld;
import eu32k.gdx.artemis.extension.component.PhysicsComponent;
import eu32k.gdx.artemis.extension.component.TextureRegionComponent;
import eu32k.gdx.artemis.extension.factory.Factory;
import eu32k.gdx.common.Bits;
import eu32k.gdx.common.PhysicsModel;
import eu32k.neonshooter.core.Neon;
import eu32k.neonshooter.core.entitySystem.common.GameBits;
import eu32k.neonshooter.core.entitySystem.component.ControllableComponent;

public class EntityFactory extends Factory {

	public EntityFactory(ExtendedWorld world, Stage stage) {
		super(world, stage);
	}

	public Entity createChlotz(float x, float y) {
		Entity e = createActorEntity(x, y, 1, 1, 0, null);

		e.addComponent(get(TextureRegionComponent.class).init(Neon.assets.getTextureRegion("square")));

		PhysicsModel square = new PhysicsModel(world.box2dWorld, e, "models.json", "Square1", 0.0f, 1.0f, 0.0f, GameBits.SCENERY, false, 1.5f);
		square.getBody().setType(BodyType.StaticBody);
		PhysicsComponent pc = get(PhysicsComponent.class).init(square.getBody());
		pc.activate(new Vector2(x, y), 0, new Vector2(0, 0));
		e.addComponent(pc);

		return e;
	}

	public Entity createShip(float x, float y, Bits bits) {
		Entity e = createActorEntity(x, y, 1, 1, 0, null);

		e.addComponent(get(TextureRegionComponent.class).init(Neon.assets.getTextureRegion("ship")));

		PhysicsModel shipModel = new PhysicsModel(world.box2dWorld, e, "models.json", "Ship1", 2.0f, 1.0f, 0.0f, bits, false, 1.0f);
		PhysicsComponent pc = get(PhysicsComponent.class).init(shipModel.getBody());
		pc.activate(new Vector2(x, y), 0, new Vector2(0, 0));
		e.addComponent(pc);

		return e;
	}

	public Entity createPlayerShip(float x, float y) {
		Entity e = createShip(x, y, GameBits.PLAYER);
		e.addComponent(get(ControllableComponent.class));
		return e;
	}

}
