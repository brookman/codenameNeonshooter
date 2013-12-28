package eu32k.neonshooter.core.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {
	private AssetManager manager;
	public Skin skin;

	public void create() {
		createEssentials();

		manager = new AssetManager();
		queueGameAssets();
	}

	private void createEssentials() {
		skin = new Skin(Gdx.files.internal("skins/uiskin.json"));
	}

	private void queueGameAssets() {
		// Queue sound fx, images for loading etc...
	}

	public boolean complete() {
		return manager.getQueuedAssets() == 0;
	}

	public boolean updateManager() {
		return manager.update();
	}
}
