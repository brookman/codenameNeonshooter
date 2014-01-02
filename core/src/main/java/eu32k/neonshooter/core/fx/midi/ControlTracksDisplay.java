package eu32k.neonshooter.core.fx.midi;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;

import eu32k.neonshooter.core.Neon;

public class ControlTracksDisplay extends Actor {
	public final static int SIZE = 32;
	public final static int PADDING = 0;
	private BitmapFont font;
	private ControlTracks state;
	private Texture textureOutline;

	private final static Color noteOn = new Color(0f, 1f, 0f, 1f);
	private final static Color noteOff = new Color(0.5f, 0.5f, 0.5f, 0.5f);
	private final static Color noteDisabled = new Color(0.5f, 0.5f, 0.5f, 0.15f);

	private final static Color intensityHigh = new Color(1f, 0f, 0f, 0.25f);
	private final static Color intensityLow = new Color(0.25f, 0.25f, 1f, 0.85f);
	private final static Color intensityBackground = new Color(0.25f, 0.25f, 0.25f, 0.25f);

	private Color tempColor;
	private Texture textureFilled;

	public ControlTracksDisplay(BitmapFont font) {
		tempColor = new Color(1f, 1f, 1f, 1f);
		this.font = font;
		this.textureOutline = Neon.assets.manager.get("textures/square.png", Texture.class);
		this.textureFilled = Neon.assets.manager.get("textures/square-filled.png", Texture.class);
	}

	public void setState(ControlTracks state) {
		this.state = state;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (state == null) {
			return;
		}
		float y = 0;
		drawLine(batch, state.beatTrack, y);
		drawLine(batch, state.bassTrack, y + SIZE + PADDING);
		drawLine(batch, state.leadTrack, y + (SIZE + PADDING) * 2);
		drawLine(batch, state.padTrack, y + (SIZE + PADDING) * 3);
		drawLine(batch, state.fxTrack, y + (SIZE + PADDING) * 4);
	}

	private void drawLine(Batch batch, ControlTrack track, float y) {
		float ratio = track.intensity().value / 127f;
		if (ratio < 0) {
			ratio = 0;
		}
		if (ratio > 1) {
			ratio = 1;
		}

		float width = SIZE * 4 * ratio;
		if (width < 1f) {
			width = 1f;
		}
		float r = Interpolation.linear.apply(intensityLow.r, intensityHigh.r, ratio);
		float g = Interpolation.linear.apply(intensityLow.g, intensityHigh.g, ratio);
		float b = Interpolation.linear.apply(intensityLow.b, intensityHigh.b, ratio);
		float a = Interpolation.linear.apply(intensityLow.a, intensityHigh.a, ratio);
		tempColor.set(r, g, b, a);
		batch.setColor(intensityBackground);
		batch.draw(textureFilled, SIZE, y + SIZE / 8 * 3, SIZE * 4, SIZE / 4);
		batch.setColor(tempColor);
		batch.draw(textureFilled, SIZE, y + SIZE / 16 * 7, width, SIZE / 8);
		float x = 6;
		for (NoteInfo n : track.noteList) {
			boolean draw = false;
			if (n.on) {
				draw = true;
				batch.setColor(noteOn);
			} else {
				draw = true;
				if (track.on().value > 0) {
					batch.setColor(noteOff);
				} else {
					batch.setColor(noteDisabled);
				}
			}
			if (draw) {
				batch.draw(textureOutline, x * (SIZE + PADDING), y, SIZE, SIZE);
			}
			x++;
		}
	}
}
