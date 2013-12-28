package eu32k.neonshooter.core;

import com.badlogic.gdx.Game;

import eu32k.neonshooter.core.config.Config;
import eu32k.neonshooter.core.config.Settings;
import eu32k.neonshooter.core.input.Controls;
import eu32k.neonshooter.core.ui.MainMenuScreen;
import eu32k.neonshooter.core.ui.Ui;

public class Neon extends Game {
	public static Neon instance;
	public static Controls controls;
	public static Ui ui;
	public static Config config;
	public static Settings settings;

	public Neon() {
		Neon.config = new Config();
	}

	@Override
	public void create() {
		Neon.instance = this;
		Neon.settings = new Settings();
		Neon.controls = new Controls();
		Neon.ui = new Ui();

		Neon.config.create();
		Neon.settings.create();
		Neon.controls.create();
		Neon.ui.create();

		Neon.ui.showScreen(MainMenuScreen.class);
	}

	@Override
	public void resize(int width, int height) {
		Neon.config.setResolution(width, height);
	}
}
