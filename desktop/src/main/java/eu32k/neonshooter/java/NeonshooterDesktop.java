package eu32k.neonshooter.java;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import eu32k.neonshooter.core.Neon;

public class NeonshooterDesktop {
	public static void main(String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Space-Dingus";
		config.vSyncEnabled = true;
		config.resizable = true;
		config.useGL20 = true;
		config.samples = 8;
		config.width = 1024;
		config.height = 576;
		config.addIcon("textures/icons/icon32.png", FileType.Local);

		new LwjglApplication(new Neon(), config);
	}
}
