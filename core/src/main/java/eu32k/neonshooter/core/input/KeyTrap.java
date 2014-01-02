package eu32k.neonshooter.core.input;

import com.badlogic.gdx.Gdx;

public class KeyTrap extends Trap {
	private int key;

	public KeyTrap(int key) {
		this.key = key;
	}

	@Override
	protected boolean isPressed() {
		return Gdx.input.isKeyPressed(key);
	}
}
