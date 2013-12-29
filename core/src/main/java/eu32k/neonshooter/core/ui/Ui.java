package eu32k.neonshooter.core.ui;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Screen;

import eu32k.neonshooter.core.Neon;

public class Ui {
	private Map<Class<?>, Screen> screens;

	public Ui() {
		screens = new HashMap<Class<?>, Screen>();
	}

	public void create() {
		addScreen(new LoadingScreen(IntroScreen.class));
		addScreen(new IntroScreen());
		addScreen(new MainMenuScreen());
		addScreen(new SettingsScreen());
		addScreen(new InGameScreen());
	}

	protected void addScreen(Screen screen) {
		screens.put(screen.getClass(), screen);
	}

	public void showScreen(Class<?> clazz) {
		Screen screen = screens.get(clazz);
		if (screen != null) {
			Neon.instance.setScreen(screen);
		}
	}
}
