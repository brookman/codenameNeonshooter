package eu32k.neonshooter.core.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {
	public Skin skin;

	public void create() {
		skin = new Skin(Gdx.files.internal("skins/uiskin.json"));
	}
}
