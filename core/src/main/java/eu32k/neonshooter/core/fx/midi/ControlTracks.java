package eu32k.neonshooter.core.fx.midi;

import java.util.ArrayList;
import java.util.List;

import com.leff.midi.MidiFile;
import com.leff.midi.MidiTrack;
import com.leff.midi.event.MidiEvent;
import com.leff.midi.event.meta.TrackName;

public class ControlTracks {
	public final static String KEY_BEAT = "beat";
	public final static String KEY_BASS = "bass";
	public final static String KEY_LEAD = "lead";
	public final static String KEY_PAD = "pad";
	public final static String KEY_FX = "fx";

	public ControlTrack beatTrack;
	public ControlTrack bassTrack;
	public ControlTrack leadTrack;
	public ControlTrack padTrack;
	public ControlTrack fxTrack;

	public List<ControlTrack> tracks;
	private boolean running;

	public ControlTracks() {
		beatTrack = new ControlTrack();
		bassTrack = new ControlTrack();
		leadTrack = new ControlTrack();
		padTrack = new ControlTrack();
		fxTrack = new ControlTrack();

		tracks = new ArrayList<ControlTrack>();
		tracks.add(beatTrack);
		tracks.add(bassTrack);
		tracks.add(leadTrack);
		tracks.add(padTrack);
		tracks.add(fxTrack);
	}

	public void load(MidiFile file) {
		unload();
		for (MidiTrack track : file.getTracks()) {
			String name = findTrackName(track).trim();
			ControlTrack controlTrack = null;
			if (KEY_BEAT.equals(name)) {
				controlTrack = beatTrack;
			} else if (KEY_BASS.equals(name)) {
				controlTrack = bassTrack;
			} else if (KEY_LEAD.equals(name)) {
				controlTrack = leadTrack;
			} else if (KEY_PAD.equals(name)) {
				controlTrack = padTrack;
			} else if (KEY_FX.equals(name)) {
				controlTrack = fxTrack;
			}
			if (controlTrack != null) {
				controlTrack.load(track, file.getResolution());
			}
		}
	}

	public String findTrackName(MidiTrack track) {
		for (MidiEvent event : track.getEvents()) {
			if (event instanceof TrackName) {
				return ((TrackName) event).getTrackName();
			}
		}
		return "";
	}

	public void play() {
		running = true;
	}

	public void stop() {
		running = false;
	}

	public void update(float delta) {
		if (running) {
			for (ControlTrack track : tracks) {
				track.update(delta);
			}
		}
	}

	public void unload() {
		// TODO: Remove existing listeners...
		beatTrack.clear();
		leadTrack.clear();
	}
}
