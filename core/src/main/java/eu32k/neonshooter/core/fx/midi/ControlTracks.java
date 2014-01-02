package eu32k.neonshooter.core.fx.midi;

import com.leff.midi.MidiFile;
import com.leff.midi.MidiTrack;
import com.leff.midi.event.MidiEvent;
import com.leff.midi.event.meta.TrackName;

public class ControlTracks {
	public final static String KEY_BEATTRACK = "beat";
	public final static String KEY_LEADTRACK = "lead";

	public ControlTrack beatTrack;
	public ControlTrack leadTrack;

	public ControlTracks() {
		beatTrack = new ControlTrack();
		leadTrack = new ControlTrack();
	}

	public void load(MidiFile file) {
		unload();
		for (MidiTrack track : file.getTracks()) {
			String name = findTrackName(track).trim();
			ControlTrack controlTrack = null;
			if (KEY_BEATTRACK.equals(name)) {
				controlTrack = beatTrack;
			} else if (KEY_LEADTRACK.equals(name)) {
				controlTrack = leadTrack;
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

	public void unload() {
		// TODO: Remove existing listeners...
		beatTrack.clear();
		leadTrack.clear();
	}
}
