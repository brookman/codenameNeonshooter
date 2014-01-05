package eu32k.neonshooter.core.spawning.trigger;

import com.badlogic.gdx.maps.MapProperties;

import eu32k.gdx.artemis.base.Entity;
import eu32k.gdx.artemis.base.World;
import eu32k.neonshooter.core.Neon;
import eu32k.neonshooter.core.fx.midi.ControlTrack;
import eu32k.neonshooter.core.fx.midi.NoteInfo;
import eu32k.neonshooter.core.spawning.PropertyReader;

public class NotePlayingTrigger implements Trigger {

   private String note;
   private ControlTrack track;

   public NotePlayingTrigger init(String note, ControlTrack track) {
      this.note = note;
      this.track = track;
      return this;
   }

   @Override
   public boolean triggers(World world, Entity e) {
      NoteInfo info = track.notes().get(note);
      if (info != null) {
         return info.on;
      }
      return false;
   }

   @Override
   public void reset() {

   }

   @Override
   public void init(MapProperties properties, String prefix) {
      note = PropertyReader.get(properties, prefix, "note");
      String trackId = PropertyReader.get(properties, prefix, "track");
      track = Neon.music.getControlTracks().tracks.get(trackId);
   }

}
