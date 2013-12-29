package eu32k.neonshooter.core.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Pools;

import eu32k.neonshooter.core.Neon;
import eu32k.neonshooter.core.model.Enemy;
import eu32k.neonshooter.core.model.Player;
import eu32k.neonshooter.core.model.Projectile;

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

		if (Neon.controls.mousePressed && System.currentTimeMillis() - lastShot > 200) {
			float rot = MathUtils.atan2(Neon.controls.mouseY - player.getY(), Neon.controls.mouseX - player.getX()) * MathUtils.radDeg;
			shoot(player.getX(), player.getY(), rot);
			lastShot = System.currentTimeMillis();
		}

		for (Actor actor : mainStage.getActors()) {
			if (actor instanceof Projectile) {
				Projectile projectile = (Projectile) actor;
				projectile.update(delta);
				if (projectile.getX() < -100 || projectile.getX() > Gdx.graphics.getWidth() + 100 || projectile.getY() < -100 || projectile.getY() > Gdx.graphics.getHeight() + 100) {
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
