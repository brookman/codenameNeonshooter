package eu32k.neonshooter.core.spawning.trigger;

import eu32k.gdx.artemis.base.Entity;
import eu32k.neonshooter.core.fx.midi.ControlTrack;
import eu32k.neonshooter.core.fx.midi.NoteInfo;

public class NotePlayingTrigger implements Trigger {

   private String note;
   private ControlTrack track;

   public void init(String note, ControlTrack track) {
      this.note = note;
      this.track = track;
   }

   @Override
   public boolean triggers(Entity e) {
      NoteInfo info = track.notes().get(note);
      if (info != null) {
         return info.on;
      }
      return false;
   }

   @Override
   public void reset() {

   }

}
