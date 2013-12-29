package eu32k.neonshooter.core.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import eu32k.neonshooter.core.Neon;

public class GameEntity extends Actor {

	public Vector2 velocity = new Vector2();
	private float rotation = 0;
	private String textureRegionName;
	private TextureRegion region;

	public GameEntity(String textureRegionName, float x, float y) {
		this.textureRegionName = textureRegionName;
		setX(x);
		setY(y);
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		if (region == null) {
			region = Neon.assets.getTextureRegion(textureRegionName);
		}
		Color color = getColor();
		batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
		batch.draw(region, getX() - getWidth() / 2.0f, getY() - getHeight() / 2.0f, getWidth() / 2.0f, getHeight() / 2.0f, getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
	}

	@Override
	public float getRotation() {
		return rotation;
	}

	@Override
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}
}
