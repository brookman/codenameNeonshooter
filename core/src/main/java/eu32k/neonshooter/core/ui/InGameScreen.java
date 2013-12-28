package eu32k.neonshooter.core.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.MathUtils;
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

	@Override
	public void render(float delta) {
		mainStage.act(delta);
		hudStage.act(delta);

		float rot = MathUtils.atan2(Neon.controls.mouseY - player.getY(), Neon.controls.mouseX - player.getX());
		player.setRotation(rot * MathUtils.radiansToDegrees);

		if (Neon.controls.mousePressed) {
			shoot(player.getX(), player.getY(), player.getRotation());
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
