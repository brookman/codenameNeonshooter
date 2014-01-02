package eu32k.neonshooter.core.ui;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.leff.midi.MidiFile;

import eu32k.neonshooter.core.Neon;

public class MainMenuScreen implements Screen {

	private Stage stage;
	private TextButton start;
	private TextButton settings;
	private TextButton exit;

	@Override
	public void render(float delta) {
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		Rectangle viewport = Neon.viewport;
		stage.setViewport(stage.getWidth(), stage.getHeight(), true, viewport.x, viewport.y, viewport.width,
				viewport.height);
	}

	@Override
	public void show() {
		if (stage == null) {
			buildStage();
		}
		Gdx.input.setInputProcessor(stage);
	}

	private void buildStage() {
		stage = new Stage();

		Table table = new Table(Neon.assets.skin);
		table.setFillParent(true);
		stage.addActor(table);

		start = new TextButton("Start", Neon.assets.skin);
		table.add(start).row();
		start.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				start();
				return false;
			}
		});

		settings = new TextButton("Settings", Neon.assets.skin);
		table.add(settings).row();
		settings.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Neon.ui.showScreen(SettingsScreen.class);
				return false;
			}
		});

		if (Gdx.app.getType() != ApplicationType.WebGL) {
			exit = new TextButton("Exit", Neon.assets.skin);
			table.add(exit);
			exit.addListener(new InputListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					Gdx.app.exit();
					return false;
				}
			});
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

	}

	private void start() {
		Gdx.app.log("MainMenuScreen", "Loading music");
		Neon.assets.manager.load("levels/test.tmx", TiledMap.class);
		// Neon.assets.manager.load("music/acid rain.ogg", Music.class);
		Neon.assets.manager.load("music/acid rain.ogg", Sound.class);
		Neon.assets.manager.load("music/acid rain.mid", MidiFile.class);
		Neon.ui.loadThenShowScreen(InGameScreen.class);
	}
}
