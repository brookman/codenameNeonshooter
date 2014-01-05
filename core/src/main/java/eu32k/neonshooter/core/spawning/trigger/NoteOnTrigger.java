package eu32k.neonshooter.core.spawning.trigger;

import eu32k.gdx.artemis.base.Entity;
import eu32k.gdx.artemis.base.World;
import eu32k.neonshooter.core.fx.midi.ControlTrack;
import eu32k.neonshooter.core.fx.midi.NoteInfo;

public class NoteOnTrigger implements Trigger {
   private boolean wasOn;
   private String note;
   private ControlTrack track;

   public NoteOnTrigger init(String note, ControlTrack track) {
      this.note = note;
      this.track = track;
      reset();
      return this;
   }

   @Override
   public boolean triggers(World world, Entity e) {
      NoteInfo info = track.notes().get(note);
      if (info != null) {

         if (info.on && !wasOn) {
            wasOn = true;
            return true;
         }
         wasOn = info.on;
      }
      return false;
   }

   @Override
   public void reset() {
      wasOn = false;
   }

}
