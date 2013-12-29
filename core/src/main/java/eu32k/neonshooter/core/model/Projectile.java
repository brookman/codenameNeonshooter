package eu32k.neonshooter.core.model;

import com.badlogic.gdx.math.Vector2;

public class Projectile extends GameEntity {

	private float speed = 400.0f;
	private Vector2 velocity = new Vector2();

	public GameEntity source;

	public Projectile() {
		this(0, 0);
	}

	public Projectile(float x, float y) {
		super("projectile", x, y);
		setWidth(5);
		setHeight(5);
		setColor(0, 1, 0, 1);
	}

	public void update(float delta) {
		velocity.set(speed, 0);
		velocity.rotate(getRotation());
		setX(getX() + velocity.x * delta);
		setY(getY() + velocity.y * delta);
	}
}