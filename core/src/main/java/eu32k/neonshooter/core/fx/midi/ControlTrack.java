package eu32k.neonshooter.core.fx.midi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.leff.midi.MidiTrack;
import com.leff.midi.event.Controller;
import com.leff.midi.event.MidiEvent;
import com.leff.midi.event.NoteOff;
import com.leff.midi.event.NoteOn;
import com.leff.midi.event.meta.Tempo;
import com.leff.midi.event.meta.TrackName;

public class ControlTrack {
	public static final int NOTE_ON = 0x90;
	public static final int NOTE_OFF = 0x80;
	public static final String[] NOTE_NAMES = { "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B" };

	private TimedQueue<MidiEvent> queue;
	private Map<Integer, NoteInfo> notes;
	List<NoteInfo> noteList;
	private Map<Integer, ControllerInfo> controllers;
	private List<ControllerInfo> controllerList;

	public String key;
	private String trackName;
	private float resolution;

	public ControlTrack() {
		queue = new TimedQueue<MidiEvent>();
		notes = new HashMap<Integer, NoteInfo>();
		noteList = new ArrayList<NoteInfo>();
		controllers = new HashMap<Integer, ControllerInfo>();
		controllerList = new ArrayList<ControllerInfo>();
	}

	public void load(MidiTrack track, float resolution) {
		clear();
		// TODO: Find out where the bpm come from...
		this.resolution = 60f / (130f * resolution);
		for (MidiEvent event : track.getEvents()) {
			registerEvent(event, resolution);
		}
	}

	public void clear() {
		trackName = "";
		queue.clear();
		notes.clear();
		noteList.clear();
		controllers.clear();
		controllerList.clear();
	}

	private void registerEvent(MidiEvent event, float resolution) {
		if (event instanceof NoteOn) {
			registerNoteOnEvent((NoteOn) event);
			registerInQueue(event);
		} else if (event instanceof Controller) {
			registerControllerEvent((Controller) event);
			registerInQueue(event);
		} else if (event instanceof NoteOff) {
			registerInQueue(event);
		} else if (event instanceof TrackName) {
			TrackName name = (TrackName) event;
			trackName = name.getTrackName();
		} else if (event instanceof Tempo) {
			Tempo tempo = (Tempo) event;
			this.resolution = 60f / (tempo.getBpm() * resolution);
			Gdx.app.log("MidiState", "Set resolution to : " + this.resolution);
		} else {
			Gdx.app.log("MidiState", "Discarding event: " + event);
		}
	}

	private void registerInQueue(MidiEvent event) {
		queue.add(event, event.getTick() * resolution);
	}

	private void registerNoteOnEvent(NoteOn event) {
		int value = event.getNoteValue();
		if (notes.containsKey(value)) {
			return;
		}
		NoteInfo note = new NoteInfo(event.getChannel(), event.getNoteValue());
		note.trackName = trackName;
		notes.put(value, note);
		noteList.add(note);

	}

	private void registerControllerEvent(Controller event) {
		int value = event.getControllerType();
		if (controllers.containsKey(value)) {
			return;
		}
		ControllerInfo note = new ControllerInfo(event.getChannel(), event.getControllerType());
		note.trackName = trackName;
		controllers.put(value, note);
		controllerList.add(note);
	}
}
