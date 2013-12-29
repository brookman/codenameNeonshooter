package eu32k.neonshooter.core.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Pools;

import eu32k.neonshooter.core.Neon;
import eu32k.neonshooter.core.model.Enemy;
import eu32k.neonshooter.core.model.Player;
import eu32k.neonshooter.core.model.Projectile;
import eu32k.neonshooter.core.rendering.utils.VectorUtils;

public class InGameScreen implements Screen {

	private Stage mainStage;
	private Stage hudStage;

	private Player player;

	public InGameScreen() {

		mainStage = new Stage();
		hudStage = new Stage();

		player = new Player(300, 300);

		mainStage.addActor(player);
		mainStage.addActor(new Enemy(200, 50));

	}

	private void shoot(float x, float y, float direction) {
		Projectile projectile = Pools.obtain(Projectile.class);
		projectile.setPosition(x, y);
		projectile.setRotation(direction);
		mainStage.addActor(projectile);
	}

	long lastShot = 0;

	@Override
	public void render(float delta) {
		mainStage.act(delta);
		hudStage.act(delta);

		// ugly test code:

		Vector2 direction = new Vector2();
		direction.add(Neon.controls.right ? 1 : 0 + (Neon.controls.left ? -1 : 0), Neon.controls.up ? 1 : 0 + (Neon.controls.down ? -1 : 0));
		if (direction.len() > 0.0f) {
			direction.nor().scl(delta * 600.0f);
			player.velocity.add(direction);
			float speed = Math.min(player.velocity.len(), 200);
			player.velocity.nor().scl(speed);
		} else {
			player.velocity.scl(0.9f);
		}

		player.setRotation(player.velocity.angle());

		player.setX(player.getX() + player.velocity.x * delta);
		player.setY(player.getY() + player.velocity.y * delta);

		// mainStage.getCamera().position.set(player.getX(), player.getY(), 0);

		if (Neon.controls.mousePressed && System.currentTimeMillis() - lastShot > 200) {
			float angle = VectorUtils.getAngleOnStage(mainStage, Neon.controls.mouseX, Neon.controls.mouseY, player.getX(), player.getY());
			shoot(player.getX(), player.getY(), angle);
			lastShot = System.currentTimeMillis();
		}

		for (Actor actor : mainStage.getActors()) {
			if (actor.getClass() == Projectile.class) {
				Projectile projectile = (Projectile) actor;
				projectile.update(delta);
				Rectangle worldRectangle = new Rectangle(-300, -300, Neon.VIRTUAL_WIDTH + 300, Neon.VIRTUAL_HEIGHT + 300);
				if (!worldRectangle.contains(projectile.getX(), projectile.getY())) {
					projectile.remove();
					Pools.free(projectile);
				}
			}
		}

		mainStage.draw();
		hudStage.draw();
	}

	@Override
	public void resize(int width, int height) {
		Rectangle viewport = Neon.viewport;
		mainStage.setViewport(mainStage.getWidth(), mainStage.getHeight(), true, viewport.x, viewport.y, viewport.width, viewport.height);
		hudStage.setViewport(hudStage.getWidth(), hudStage.getHeight(), true, viewport.x, viewport.y, viewport.width, viewport.height);
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(hudStage);
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
