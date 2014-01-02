package eu32k.neonshooter.core.input;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.math.Vector2;

public class Controls {

	public boolean up = false;
	public boolean down = false;
	public boolean left = false;
	public boolean right = false;

	public Vector2 padLeft = new Vector2();
	public Vector2 padRight = new Vector2();

	public boolean mousePressed = false;

	public float mouseX = 0;
	public float mouseY = 0;

	public TouchTrap touchAny;
	public ButtonTrap mouseLeft;
	public ButtonTrap mouseRight;

	private List<Trap> traps;

	public void create() {
		traps = new ArrayList<Trap>();
		touchAny = new TouchTrap();
		mouseLeft = new ButtonTrap(Input.Buttons.LEFT);
		mouseRight = new ButtonTrap(Input.Buttons.RIGHT);

		traps.add(touchAny);
		traps.add(mouseLeft);
		traps.add(mouseRight);
	}

	public void update() {
		up = Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP);
		down = Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN);
		left = Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT);
		right = Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT);

		mousePressed = Gdx.input.isButtonPressed(Buttons.LEFT);

		mouseX = Gdx.input.getX();
		mouseY = Gdx.input.getY();

		// TODO: Refactor traps to use KeyListener or something like that in
		// Controls
		for (Trap trap : traps) {
			trap.update();
		}
	}
}
