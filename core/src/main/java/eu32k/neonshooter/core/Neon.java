package eu32k.neonshooter.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import eu32k.neonshooter.core.config.Config;
import eu32k.neonshooter.core.config.Settings;
import eu32k.neonshooter.core.input.Controls;
import eu32k.neonshooter.core.model.Assets;
import eu32k.neonshooter.core.ui.LoadingScreen;
import eu32k.neonshooter.core.ui.Ui;

public class Neon extends Game {
	public static Neon instance;
	public static Controls controls;
	public static Ui ui;
	public static Config config;
	public static Settings settings;
	public static Assets assets;

	public Neon() {
		Neon.config = new Config();
	}

	@Override
	public void create() {
		Neon.instance = this;
		Neon.settings = new Settings();
		Neon.controls = new Controls();
		Neon.ui = new Ui();
		Neon.assets = new Assets();

		Neon.config.create();
		Neon.settings.create();
		Neon.assets.create();
		Neon.controls.create();
		Neon.ui.create();

		Neon.ui.showScreen(LoadingScreen.class);
	}

	@Override
	public void render() {
		Neon.controls.update();

		Gdx.graphics.getGL20().glClearColor(0, 0, 0, 1);
		Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		Neon.config.setResolution(width, height);
		super.resize(width, height);
	}
}
