package eu32k.neonshooter.core.ui;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import eu32k.neonshooter.core.Neon;

public class LoadingScreen implements Screen {
	public Class targetScreen;
	Stage stage;

	public LoadingScreen(Class targetScreen) {
		this.targetScreen = targetScreen;
	}

	@Override
	public void render(float delta) {
		if (Neon.assets.updateManager()) {
			Neon.ui.showScreen(targetScreen);
			return;
		}
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {
		if (stage == null) {
			stage = new Stage();
			Table table = new Table(Neon.assets.skin);
			table.setFillParent(true);
			stage.addActor(table);

			Label label = new Label("Loading", Neon.assets.skin);
			table.add(label);
			label.addAction(Actions.forever(Actions.sequence(
					Actions.moveBy(0f, 20f, 2f), Actions.moveBy(0f, -20f, 2f))));
		}
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
		// TODO Auto-generated method stub

	}

}
