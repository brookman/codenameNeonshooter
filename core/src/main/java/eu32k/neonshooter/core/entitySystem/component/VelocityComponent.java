package eu32k.neonshooter.core.entitySystem.component;

import com.badlogic.gdx.math.Vector2;

import eu32k.gdx.artemis.base.Component;

public class VelocityComponent extends Component {

	public Vector2 velocity = new Vector2();

	public VelocityComponent init(float speedX, float speedY) {
		velocity.set(speedX, speedY);
		return this;
	}
}
