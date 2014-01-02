package eu32k.neonshooter.core.input;

import com.badlogic.gdx.Gdx;

public class ButtonTrap extends Trap {
	private int key;

	public ButtonTrap(int key) {
		this.key = key;
	}

	@Override
	protected boolean isPressed() {
		return Gdx.input.isButtonPressed(key);
	}
}
