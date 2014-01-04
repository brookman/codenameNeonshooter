package eu32k.neonshooter.core.fx.midi;

import java.util.ArrayList;
import java.util.List;

public class NoteInfo {
   public int channel;
   public int note;
   public boolean on;
   public String trackName = "";
   private List<NoteHandler> handlers;

   public NoteInfo(int channel, int note) {
      this.channel = channel;
      this.note = note;
      handlers = new ArrayList<NoteHandler>();
   }

   public void addHandler(NoteHandler handler) {
      handlers.add(handler);
   }

   public void removeHandler(NoteHandler handler) {
      handlers.remove(handler);
   }

   public void notifyNoteOn() {
      on = true;
      for (NoteHandler handler : handlers) {
         handler.noteOn(this);
      }
   }

   public void notifyNoteOff() {
      on = false;
      for (NoteHandler handler : handlers) {
         handler.noteOff(this);
      }
   }

   public interface NoteHandler {
      void noteOn(NoteInfo event);

      void noteOff(NoteInfo event);
   }
}
