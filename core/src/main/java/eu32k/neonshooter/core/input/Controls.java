package eu32k.neonshooter.core.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;

public class Controls {

	public boolean up = false;
	public boolean down = false;
	public boolean left = false;
	public boolean right = false;

	public boolean mousePressed = false;

	public float mouseX = 0;
	public float mouseY = 0;

	public void create() {

	}

	public void update() {
		up = Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP);
		down = Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN);
		left = Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT);
		right = Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT);

		mousePressed = Gdx.input.isButtonPressed(Buttons.LEFT);

		mouseX = Gdx.input.getX();
		mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
	}
}
