package eu32k.neonshooter.core.fx.midi;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;

import eu32k.neonshooter.core.Neon;

public class MidiStateDisplay extends Actor {
	public final static int SIZE = 32;
	public final static int PADDING = 0;
	private BitmapFont font;
	private MidiState state;
	private Texture texture;

	public MidiStateDisplay(BitmapFont font) {
		this.font = font;
		this.texture = Neon.assets.manager.get("textures/square.png", Texture.class);
	}

	public void setState(MidiState state) {
		this.state = state;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (state == null) {
			return;
		}
		float x = 0;
		for (NoteInfo n : state.notes().values()) {
			if (n.on) {
				batch.setColor(Color.GREEN);
			} else {
				batch.setColor(Color.GRAY);
			}
			batch.draw(texture, 0 + x * (SIZE + PADDING), 0, SIZE, SIZE);
			x++;
		}
	}
}
