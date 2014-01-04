package eu32k.neonshooter.core.fx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.leff.midi.MidiFile;

import eu32k.neonshooter.core.Neon;
import eu32k.neonshooter.core.fx.midi.ControlTracks;
import eu32k.neonshooter.core.fx.midi.ControlTracksDisplay;

public class MusicManager implements Serializable {
   private List<SoundSet> trackList;
   private Map<String, SoundSet> tracks;

   private SoundSet currentSet;

   private List<SoundSet> arcadeTracks;
   private ControlTracks controlTracks;
   private long soundId;
   private ControlTracksDisplay midiDisplay;
   private Sound sound;

   public MusicManager() {
      controlTracks = new ControlTracks();
      trackList = new ArrayList<SoundSet>();
      tracks = new HashMap<String, SoundSet>();
      arcadeTracks = new ArrayList<SoundSet>();
   }

   public List<SoundSet> arcade() {
      return arcadeTracks;
   }

   public void create() {
      String content = Gdx.files.internal("music/tracks.json").readString();
      JsonReader reader = new JsonReader();
      JsonValue root = reader.parse(content);
      JsonValue tracks = root.get("tracks");
      JsonValue child = tracks.child();
      Json json = new Json();
      while (child != null) {
         SoundSet set = json.readValue(SoundSet.class, child);
         Gdx.app.log("MusicManager", "Loading track '" + set + "'");
         this.tracks.put(set.id, set);
         child = child.next();
      }
      JsonValue arcadeTracks = root.get("arcadeTracks");
      JsonValue arcadeTrack = arcadeTracks.child();
      while (arcadeTrack != null) {
         String id = arcadeTrack.asString();
         SoundSet set = this.tracks.get(id);
         if (set == null) {
            Gdx.app.error("MusicManager", "Cannot find track with id '" + id + "'");
         } else {
            Gdx.app.log("MusicManager", "Adding track '" + id + "' to arcade.");
            this.arcadeTracks.add(set);
         }
         arcadeTrack = arcadeTrack.next();
      }
   }

   public ControlTracksDisplay getMidiDisplay() {
      if (midiDisplay == null) {
         midiDisplay = new ControlTracksDisplay(null);
      }
      return midiDisplay;
   }

   @Override
   public void write(Json json) {

   }

   @Override
   public void read(Json json, JsonValue jsonData) {

   }

   @Override
   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("Tracks\n");
      return builder.toString();
   }

   public void loadTrack() {
      if (currentSet == null) {
         return;
      }
      sound = Neon.assets.manager.get(currentSet.audioFile, Sound.class);
      MidiFile mid = Neon.assets.manager.get(currentSet.controlFile, MidiFile.class);
      controlTracks.load(mid);
      getMidiDisplay().setState(controlTracks);
   }

   public void play() {
      controlTracks.play();
      soundId = sound.play(0.5f);
   }

   public void prepareSet(String id) {
      prepareSet(tracks.get(id));
   }

   public void prepareSet(SoundSet set) {
      if (set == null) {
         return;
      }
      this.currentSet = set;
      Neon.assets.manager.load(set.audioFile, Sound.class);
      Neon.assets.manager.load(set.controlFile, MidiFile.class);
   }

   public void prepareAnyArcadeTrack() {
      if (arcadeTracks.size() > 1) {
         prepareSet(arcadeTracks.get(Neon.random.nextInt(arcadeTracks.size())));
      } else if (arcadeTracks.size() == 1) {
         prepareSet(arcadeTracks.get(0));
      }
   }

   public void update(float delta) {
      controlTracks.update(delta);
   }

   public void pitch(float pitch) {
      sound.setPitch(soundId, pitch);
   }

   public ControlTracks getControlTracks() {
      return controlTracks;
   }

}
