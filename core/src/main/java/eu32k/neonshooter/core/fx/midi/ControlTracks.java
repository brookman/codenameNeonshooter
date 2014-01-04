package eu32k.neonshooter.core.fx.midi;

import java.util.ArrayList;
import java.util.List;

import com.leff.midi.MidiFile;
import com.leff.midi.MidiTrack;
import com.leff.midi.event.MidiEvent;
import com.leff.midi.event.meta.TrackName;

import eu32k.neonshooter.core.Neon;

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
      float bpm = 130;

      for (MidiTrack track : file.getTracks()) {
         String name = findTrackName(track).trim();
         if (name.startsWith("bpm:")) {
            bpm = Float.parseFloat(name.substring(4));
         }
      }
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
            controlTrack.load(track, file.getResolution(), bpm);
         }
      }
      if (beatTrack.noteList.size() > 0) {
         beatTrack.noteList.get(0).addHandler(Neon.fx.beatHandler());
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

   public enum TrackType {
      Beat, Bass, Lead, Pad, Fx
   }

   public ControlTrack getTrack(TrackType track) {
      switch (track) {
      case Bass:
         return bassTrack;
      case Beat:
         return beatTrack;
      case Lead:
         return leadTrack;
      case Pad:
         return padTrack;
      case Fx:
         return fxTrack;
      default:
         return null;
      }
   }
}
