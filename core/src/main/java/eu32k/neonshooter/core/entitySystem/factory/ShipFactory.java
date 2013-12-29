package eu32k.neonshooter.core.entitySystem.factory;

import com.badlogic.gdx.scenes.scene2d.Stage;

import eu32k.gdx.artemis.base.Entity;
import eu32k.gdx.artemis.extension.ExtendedWorld;
import eu32k.gdx.artemis.extension.component.TextureRegionComponent;
import eu32k.gdx.artemis.extension.factory.Factory;
import eu32k.neonshooter.core.Neon;
import eu32k.neonshooter.core.entitySystem.component.MovableComponent;

public class ShipFactory extends Factory {

	public ShipFactory(ExtendedWorld world, Stage stage) {
		super(world, stage);
	}

	public Entity createShip(float x, float y) {
		Entity e = createActorEntity(x, y, 25, 25, 0, null);

		e.addComponent(get(MovableComponent.class).init(100, 0));
		e.addComponent(get(TextureRegionComponent.class).init(Neon.assets.getTextureRegion("ship")));
		// e.addComponent(Pools.obtain(StabilizerComponent.class).init(true,
		// true));
		// e.addComponent(Pools.obtain(HealthComponent.class).init(100));
		return e;
	}
}
