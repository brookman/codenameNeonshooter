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

import eu32k.neonshooter.core.fx.midi.TimedQueue.TimedQueueListener;

public class ControlTrack implements TimedQueueListener<MidiEvent> {
   public static final int NOTE_ON = 0x90;
   public static final int NOTE_OFF = 0x80;
   public static final String[] NOTE_NAMES = { "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B" };

   public static String noteToName(int value) {
      return NOTE_NAMES[value % 12];
   }

   private TimedQueue<MidiEvent> queue;
   private Map<String, NoteInfo> notes;
   List<NoteInfo> noteList;
   private Map<Integer, ControllerInfo> controllers;
   private List<ControllerInfo> controllerList;
   private List<AnyNoteHandler> anyHandlers;
   public String id;
   public String key;
   private String trackName;
   private float resolution;

   private ControllerInfo on;

   public ControllerInfo on() {
      return on;
   }

   private int notesPlaying = 0;
   private ControllerInfo intensity;

   public ControllerInfo intensity() {
      return intensity;
   }

   public ControlTrack(String id) {
      this.id = id;
      on = new ControllerInfo(0, 1);
      intensity = new ControllerInfo(0, 2);
      queue = new TimedQueue<MidiEvent>();
      notes = new HashMap<String, NoteInfo>();
      noteList = new ArrayList<NoteInfo>();
      controllers = new HashMap<Integer, ControllerInfo>();
      controllerList = new ArrayList<ControllerInfo>();
      anyHandlers = new ArrayList<AnyNoteHandler>();
      clear();
      queue.setListener(this);
   }

   public String id() {
      return id;
   }

   public void load(MidiTrack track, float resolution, float bpm) {
      clear();
      // TODO: Find out where the bpm come from...
      this.resolution = 60f / (bpm * resolution);
      for (MidiEvent event : track.getEvents()) {
         registerEvent(event, resolution);
      }
      queue.init();
   }

   public void clear() {
      trackName = "";
      queue.clear();
      notes.clear();
      noteList.clear();
      controllers.clear();
      controllerList.clear();
      controllers.put(on.type, on);
      controllerList.add(on);
      controllers.put(intensity.type, intensity);
      controllerList.add(intensity);
      notesPlaying = 0;
      // TODO: Remove all handlers?
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
      String key = noteToName(event.getNoteValue());
      if (notes.containsKey(key)) {
         return;
      }
      NoteInfo note = new NoteInfo(event.getChannel(), key);
      note.trackName = trackName;
      notes.put(key, note);
      noteList.add(note);

   }

   private void registerControllerEvent(Controller event) {
      int value = event.getControllerType();
      int channel = event.getChannel();
      if (controllers.containsKey(value)) {
         return;
      }
      ControllerInfo note = new ControllerInfo(channel, event.getControllerType());
      note.trackName = trackName;
      controllers.put(value, note);
      controllerList.add(note);
   }

   public void update(float delta) {
      queue.update(delta);
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

   protected void noteOn(NoteOn event) {
      // System.out.println("Note on ");
      int value = event.getNoteValue();
      NoteInfo note = notes.get(noteToName(value));
      if (note == null) {
         return;
      }
      note.on = true;
      note.notifyNoteOn();
      if (notesPlaying == 0) {
         notifyAnyPlaying();
      }
      notesPlaying++;
   }

   protected void noteOff(NoteOff event) {
      // System.out.println("Note off");
      int value = event.getNoteValue();
      NoteInfo note = notes.get(noteToName(value));
      if (note == null) {
         return;
      }
      note.on = false;
      note.notifyNoteOff();
      notesPlaying--;
      if (notesPlaying == 0) {
         notifyAnyStopped();
      }
      // TODO: On reset, all notes have to be turned off
   }

   private void notifyAnyPlaying() {
      for (AnyNoteHandler handler : anyHandlers) {
         handler.playing(this);
      }
   }

   private void notifyAnyStopped() {
      for (AnyNoteHandler handler : anyHandlers) {
         handler.stopped(this);
      }
   }

   protected void controllerEvent(Controller event) {
      // System.out.println("Controller Change");
      int value = event.getControllerType();
      ControllerInfo controller = controllers.get(value);
      if (controller == null) {
         return;
      }
      controller.value = event.getValue();
      controller.initValue = controller.value;
      controller.notifyHandlers();
      // TODO: On reset, all handlers have to communicate their init value
   }

   public boolean anyNotePlaying() {
      for (NoteInfo note : notes.values()) {
         if (note.on) {
            return true;
         }
      }
      return false;
   }

   public interface AnyNoteHandler {
      public boolean playing(ControlTrack track);

      public boolean stopped(ControlTrack track);
   }

   public Map<String, NoteInfo> notes() {
      return notes;
   }
}
