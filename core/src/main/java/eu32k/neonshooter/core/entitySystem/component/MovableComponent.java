package eu32k.neonshooter.core.entitySystem.component;

import eu32k.gdx.artemis.base.Component;

public class MovableComponent extends Component {

	public float speedX = 0;
	public float speedY = 0;

	public MovableComponent init(float speedX, float speedY) {
		this.speedX = speedX;
		this.speedY = speedY;
		return this;
	}
}
