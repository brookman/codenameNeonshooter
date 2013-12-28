package eu32k.neonshooter.core.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class Ship extends Actor {

	private Drawable drawable;

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		Color color = getColor();
		batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
		drawable.draw(batch, getX(), getY(), getWidth(), getHeight());
	}
}