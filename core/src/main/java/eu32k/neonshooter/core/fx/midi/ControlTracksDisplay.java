package eu32k.neonshooter.core.fx.midi;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;

import eu32k.neonshooter.core.Neon;

public class ControlTracksDisplay extends Actor {
	public final static int SIZE = 32;
	public final static int PADDING = 0;
	private BitmapFont font;
	private ControlTracks state;
	private Texture texture;

	public ControlTracksDisplay(BitmapFont font) {
		this.font = font;
		this.texture = Neon.assets.manager.get("textures/square.png", Texture.class);
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
		float x = 0;
		for (NoteInfo n : track.noteList) {
			if (n.on) {
				batch.setColor(Color.GREEN);
			} else {
				batch.setColor(Color.GRAY);
			}
			batch.draw(texture, 0 + x * (SIZE + PADDING), y, SIZE, SIZE);
			x++;
		}
	}
}
