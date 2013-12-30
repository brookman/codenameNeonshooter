package eu32k.neonshooter.core.model;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;

public class LevelData {
	private World world;
	private TiledMap level;

	public boolean isLoaded() {
		return level != null;
	}

	public void load(TiledMap level) {
		if (isLoaded()) {
			unload();
		}

		this.level = level;
		initEntities();
	}

	public void reload() {
		initEntities();
	}

	public void unload() {

	}

	protected void initEntities() {

	}
}
