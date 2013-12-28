package eu32k.neonshooter.core.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import eu32k.neonshooter.core.Neon;

public class MainMenuScreen implements Screen {
	Stage stage;
	Texture texture;
	SpriteBatch batch;
	float elapsed;

	@Override
	public void render(float delta) {
		elapsed += delta;
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(texture, 100 + 100 * (float) Math.cos(elapsed),
				100 + 25 * (float) Math.sin(elapsed));
		batch.end();

		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {
		if (stage == null) {
			buildStage();
		}

	}

	private void buildStage() {
		texture = new Texture(Gdx.files.internal("textures/image.png"));
		batch = new SpriteBatch();
		stage = new Stage();
		Table table = new Table(Neon.assets.skin);
		table.setFillParent(true);
		stage.addActor(table);

		Label label = new Label("Hello", Neon.assets.skin);
		table.add(label);
		label.addAction(Actions.forever(Actions.sequence(
				Actions.moveBy(0f, 20f, 2f), Actions.moveBy(0f, -20f, 2f))));
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
