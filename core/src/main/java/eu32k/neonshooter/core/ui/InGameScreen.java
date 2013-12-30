package eu32k.neonshooter.core.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;

import eu32k.gdx.artemis.base.managers.GroupManager;
import eu32k.gdx.artemis.extension.ExtendedWorld;
import eu32k.gdx.artemis.extension.system.PhysicsSystem;
import eu32k.gdx.artemis.extension.system.RemoveSystem;
import eu32k.neonshooter.core.Neon;
import eu32k.neonshooter.core.entitySystem.common.GameBits;
import eu32k.neonshooter.core.entitySystem.factory.EntityFactory;
import eu32k.neonshooter.core.entitySystem.system.ControlSystem;

public class InGameScreen implements Screen {

	private Stage gameStage;
	private Stage hudStage;

	private World box2dWorld;
	private ExtendedWorld artemisWorld;

	private Box2DDebugRenderer debugRenderer;

	@Override
	public void show() {
		if (gameStage == null) {
			gameStage = new Stage(Neon.VIRTUAL_WIDTH, Neon.VIRTUAL_HEIGHT, true);
			hudStage = new Stage();

			box2dWorld = new World(new Vector2(0, 0), true);
			artemisWorld = new ExtendedWorld(box2dWorld, gameStage);
			artemisWorld.setManager(new GroupManager());

			EntityFactory shipFactory = new EntityFactory(artemisWorld, gameStage);

			artemisWorld.setSystem(new PhysicsSystem(box2dWorld));
			artemisWorld.setSystem(new ControlSystem());
			artemisWorld.setSystem(new RemoveSystem());

			artemisWorld.initialize();

			shipFactory.createPlayerShip(3, 3).addToWorld();
			shipFactory.createShip(4, 3, GameBits.SCENERY).addToWorld();
			shipFactory.createChlotz(9, 1).addToWorld();
			shipFactory.createChlotz(9, 3).addToWorld();
			shipFactory.createChlotz(2, 1).addToWorld();

			debugRenderer = new Box2DDebugRenderer();
			debugRenderer.setDrawAABBs(true);
			debugRenderer.setDrawBodies(true);
			// debugRenderer.setDrawContacts(true);
			debugRenderer.setDrawInactiveBodies(true);
			debugRenderer.setDrawJoints(true);
			debugRenderer.setDrawVelocities(true);
		}
		Gdx.input.setInputProcessor(hudStage);
	}

	@Override
	public void render(float delta) {
		gameStage.act(delta);
		hudStage.act(delta);

		artemisWorld.setDelta(delta);
		artemisWorld.process();

		gameStage.draw();
		hudStage.draw();

		debugRenderer.render(box2dWorld, gameStage.getCamera().combined);

		// ugly test code:

		// Vector2 direction = new Vector2();
		// direction.add(Neon.controls.right ? 1 : 0 + (Neon.controls.left ? -1
		// : 0), Neon.controls.up ? 1 : 0 + (Neon.controls.down ? -1 : 0));
		// if (direction.len() > 0.0f) {
		// direction.nor().scl(delta * 600.0f);
		// player.velocity.add(direction);
		// float speed = Math.min(player.velocity.len(), 200);
		// player.velocity.nor().scl(speed);
		// } else {
		// player.velocity.scl(0.9f);
		// }
		//
		// player.setRotation(player.velocity.angle());
		//
		// player.setX(player.getX() + player.velocity.x * delta);
		// player.setY(player.getY() + player.velocity.y * delta);
		//
		// // mainStage.getCamera().position.set(player.getX(), player.getY(),
		// 0);
		//
		// if (Neon.controls.mousePressed && System.currentTimeMillis() -
		// lastShot > 200) {
		// float angle = VectorUtils.getAngleOnStage(mainStage,
		// Neon.controls.mouseX, Neon.controls.mouseY, player.getX(),
		// player.getY());
		// shoot(player.getX(), player.getY(), angle, true);
		// lastShot = System.currentTimeMillis();
		// }
		//
		// for (Actor actor : mainStage.getActors()) {
		// if (actor.getClass() == Projectile.class) {
		// Projectile projectile = (Projectile) actor;
		// projectile.update(delta);
		// Rectangle worldRectangle = new Rectangle(-300, -300,
		// Neon.VIRTUAL_WIDTH + 300, Neon.VIRTUAL_HEIGHT + 300);
		// if (!worldRectangle.contains(projectile.getX(), projectile.getY())) {
		// projectile.remove();
		// Pools.free(projectile);
		// }
		// }
		// }
	}

	@Override
	public void resize(int width, int height) {
		Rectangle viewport = Neon.viewport;
		gameStage.setViewport(gameStage.getWidth(), gameStage.getHeight(), true, viewport.x, viewport.y, viewport.width, viewport.height);
		hudStage.setViewport(hudStage.getWidth(), hudStage.getHeight(), true, viewport.x, viewport.y, viewport.width, viewport.height);
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}
}
