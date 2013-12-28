package eu32k.neonshooter.core;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;

import eu32k.neonshooter.core.input.Controls;
import eu32k.neonshooter.core.ui.MainMenuScreen;
import eu32k.neonshooter.core.ui.Ui;

public class Neon extends Game {
	public static Neon instance;
	public static Controls controls;
	public static Ui ui;
	
	
	@Override
	public void create () {
		Neon.instance = this;
		Neon.controls = new Controls();
		Neon.ui = new Ui();
		
		ui.create();
		ui.showScreen(MainMenuScreen.class);
	}
}
