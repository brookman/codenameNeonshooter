package eu32k.neonshooter.core.model;

public class Ship extends GameEntity {

	public Ship(float x, float y) {
		super("ship", x, y);
		setWidth(100);
		setHeight(100);
	}
}