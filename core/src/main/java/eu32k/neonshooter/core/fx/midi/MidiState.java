package eu32k.neonshooter.core.fx.midi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import com.badlogic.gdx.Gdx;
import com.leff.midi.MidiFile;
import com.leff.midi.MidiTrack;
import com.leff.midi.event.Controller;
import com.leff.midi.event.MidiEvent;
import com.leff.midi.event.NoteOff;
import com.leff.midi.event.NoteOn;
import com.leff.midi.event.meta.Tempo;
import com.leff.midi.event.meta.TrackName;
import com.leff.midi.util.MidiEventListener;
import com.leff.midi.util.MidiProcessor;

import eu32k.neonshooter.core.fx.midi.TimedQueue.TimedQueueListener;

public class MidiState implements TimedQueueListener<MidiEvent> {
	private MidiTrack track;
	private Map<Integer, NoteInfo> noteState;
	private Map<Integer, ControllerInfo> controllerState;
	private MidiProcessor processor;
	private MidiFile file;
	private TimedQueue<MidiEvent> queue;
	private boolean running;
	private float resolution;
	private String trackName;

	public MidiState() {
		noteState = new HashMap<Integer, NoteInfo>();
		controllerState = new HashMap<Integer, ControllerInfo>();
		queue = new TimedQueue<MidiEvent>();
		queue.setListener(this);
	}

	public Map<Integer, NoteInfo> notes() {
		return noteState;
	}

	public Map<Integer, ControllerInfo> controllers() {
		return controllerState;
	}

	public boolean loaded() {
		return track != null && processor != null;
	}

	public void load(MidiFile file) {
		if (loaded()) {
			unload();
		}
		file.getResolution();
		file.getLengthInTicks();
		MidiProcessor processor = new MidiProcessor(file);
		registerProcessorEvents(processor);

		resolution = 60f / (120f * file.getResolution());
		this.file = file;
		List<MidiTrack> tracks = file.getTracks();

		for (MidiTrack track : tracks) {
			trackName = "";
			TreeSet<MidiEvent> events = track.getEvents();
			for (MidiEvent event : events) {
				registerEvent(event);
			}
		}
		queue.init();
		// TODO: Most likely obsolete
		this.track = file.getTracks().get(0);
		this.processor = processor;
	}

	private void registerEvent(MidiEvent event) {
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
			resolution = 60f / (tempo.getBpm() * file.getResolution());
			Gdx.app.log("MidiState", "Set resolution to : " + resolution);
		} else {
			Gdx.app.log("MidiState", "Discarding event: " + event);
		}
	}

	private void registerInQueue(MidiEvent event) {
		queue.add(event, event.getTick() * resolution);
	}

	private void registerNoteOnEvent(NoteOn event) {
		int value = event.getNoteValue();
		if (noteState.containsKey(value)) {
			return;
		}
		NoteInfo note = new NoteInfo(event.getChannel(), event.getNoteValue());
		note.trackName = trackName;
		noteState.put(value, note);

	}

	private void registerControllerEvent(Controller event) {
		int value = event.getControllerType();
		if (controllerState.containsKey(value)) {
			return;
		}
		ControllerInfo note = new ControllerInfo(event.getChannel(), event.getControllerType());
		note.trackName = trackName;
		controllerState.put(value, note);

	}

	private void registerProcessorEvents(MidiProcessor processor) {
		processor.registerEventListener(new MidiEventListener() {
			@Override
			public void onStop(boolean finished) {

			}

			@Override
			public void onStart(boolean fromBeginning) {

			}

			@Override
			public void onEvent(MidiEvent event, long ms) {
				noteOn((NoteOn) event);
			}
		}, NoteOn.class);
		processor.registerEventListener(new MidiEventListener() {
			@Override
			public void onStop(boolean finished) {

			}

			@Override
			public void onStart(boolean fromBeginning) {

			}

			@Override
			public void onEvent(MidiEvent event, long ms) {
				noteOff((NoteOff) event);
			}
		}, NoteOff.class);
		processor.registerEventListener(new MidiEventListener() {
			@Override
			public void onStop(boolean finished) {

			}

			@Override
			public void onStart(boolean fromBeginning) {

			}

			@Override
			public void onEvent(MidiEvent event, long ms) {
				controllerEvent((Controller) event);
			}
		}, Controller.class);
	}

	protected void noteOn(NoteOn event) {
		// System.out.println("Note on ");
		int value = event.getNoteValue();
		NoteInfo note = noteState.get(value);
		if (note == null) {
			return;
		}
		note.on = true;
	}

	protected void noteOff(NoteOff event) {
		// System.out.println("Note off");
		int value = event.getNoteValue();
		NoteInfo note = noteState.get(value);
		if (note == null) {
			return;
		}
		note.on = false;
	}

	protected void controllerEvent(Controller event) {
		// System.out.println("Controller Change");
		int value = event.getControllerType();
		ControllerInfo controller = controllerState.get(value);
		if (controller == null) {
			return;
		}
		controller.value = event.getValue();
		controller.initValue = controller.value;
	}

	public void start() {
		if (loaded()) {
			running = true;
			// processor.start();
		}
	}

	public void update(float delta) {
		if (loaded() && running) {
			queue.update(delta);
		}
	}

	public void stop() {
		if (loaded()) {
			running = false;
			// processor.stop();
		}
	}

	public void reset() {
		processor.stop();
		processor.reset();

		for (NoteInfo note : noteState.values()) {
			note.on = false;
		}
		for (ControllerInfo controller : controllerState.values()) {
			controller.value = controller.initValue;
		}
	}

	public void unload() {
		this.file = null;
		this.track = null;
		noteState.clear();
		controllerState.clear();
		queue.clear();
		this.processor.stop();
		this.processor = null;
	}

	public void print() {
		if (!loaded()) {
			System.out.println("Not loaded");
			return;
		}
		StringBuilder builder = new StringBuilder();
		for (ControllerInfo controller : controllerState.values()) {
			builder.append("C-+ " + controller.channel + "-" + controller.type + ": " + controller.value + " | ");
		}
		for (NoteInfo note : noteState.values()) {
			if (note.on) {
				builder.append("N-" + note.channel + "-" + note.note + " | ");
			}
		}
		Gdx.app.log("MidiState", builder.toString());
	}

	@Override
	public void event(MidiEvent value, float time) {
		if (value instanceof NoteOn) {
			noteOn((NoteOn) value);
		} else if (value instanceof NoteOff) {
			noteOff((NoteOff) value);
		} else if (value instanceof Controller) {
			controllerEvent((Controller) value);
		}
	}
}
