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
import com.leff.midi.util.MidiEventListener;
import com.leff.midi.util.MidiProcessor;

public class MidiState {
	private MidiTrack track;
	private Map<Integer, NoteInfo> noteState;
	private Map<Integer, ControllerInfo> controllerState;
	private MidiProcessor processor;
	private MidiFile file;

	public MidiState() {
		noteState = new HashMap<Integer, NoteInfo>();
		controllerState = new HashMap<Integer, ControllerInfo>();
	}

	public boolean loaded() {
		return track != null && processor != null;
	}

	public void load(MidiFile file) {
		if (loaded()) {
			unload();
		}

		MidiProcessor processor = new MidiProcessor(file);
		registerProcessorEvents(processor);

		this.file = file;
		List<MidiTrack> tracks = file.getTracks();
		for (MidiTrack track : tracks) {
			TreeSet<MidiEvent> events = track.getEvents();
			for (MidiEvent event : events) {
				registerEvent(event);
			}
		}
		// TODO: Most likely obsolete
		this.track = file.getTracks().get(0);
		this.processor = processor;
	}

	private void registerEvent(MidiEvent event) {
		if (event instanceof NoteOn) {
			registerNoteOnEvent((NoteOn) event);
		}
		if (event instanceof Controller) {
			registerControllerEvent((Controller) event);
		}
	}

	private void registerNoteOnEvent(NoteOn event) {
		int value = event.getNoteValue();
		if (noteState.containsKey(value)) {
			return;
		}
		NoteInfo note = new NoteInfo(event.getChannel(), event.getNoteValue());
		noteState.put(value, note);
	}

	private void registerControllerEvent(Controller event) {
		int value = event.getControllerType();
		if (controllerState.containsKey(value)) {
			return;
		}
		ControllerInfo note = new ControllerInfo(event.getChannel(), event.getControllerType());
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
				noteOn((NoteOn) event, ms);
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
				noteOff((NoteOff) event, ms);
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
				controllerEvent((Controller) event, ms);
			}
		}, Controller.class);
	}

	protected void noteOn(NoteOn event, long ms) {
		// System.out.println("Note on ");
		int value = event.getNoteValue();
		NoteInfo note = noteState.get(value);
		if (note == null) {
			return;
		}
		note.on = true;
	}

	protected void noteOff(NoteOff event, long ms) {
		// System.out.println("Note off");
		int value = event.getNoteValue();
		NoteInfo note = noteState.get(value);
		if (note == null) {
			return;
		}
		note.on = false;
	}

	protected void controllerEvent(Controller event, long ms) {
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
			processor.start();
		}
	}

	public void stop() {
		if (loaded()) {
			processor.stop();
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
		this.processor.stop();
		this.processor = null;
	}

	public void print() {
		if (!loaded()) {
			System.out.println("Not loaded");
			return;
		}
		StringBuilder builder = new StringBuilder();
		for (NoteInfo note : noteState.values()) {
			if (note.on) {
				builder.append("Note (" + note.channel + "/" + note.note + ") on; ");
			}
		}
		for (ControllerInfo controller : controllerState.values()) {
			builder.append("Controller ( + " + controller.channel + "/" + controller.type + "): " + controller.value
					+ " ;");
		}
		Gdx.app.log("MidiState", builder.toString());
	}
}
