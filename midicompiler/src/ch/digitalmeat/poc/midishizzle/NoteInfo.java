package ch.digitalmeat.poc.midishizzle;

import java.util.List;

public class NoteInfo {
   public int channel;
   public int note;
   public boolean on;
   private List<NoteHandler> handlers;

   public NoteInfo(int channel, int note) {
      this.channel = channel;
      this.note = note;
   }

   public void addHandler(NoteHandler handler) {
      handlers.add(handler);
   }

   public void removeHandler(NoteHandler handler) {
      handlers.remove(handler);
   }

   public void notifyNoteOn() {
      for (NoteHandler handler : handlers) {
         handler.noteOn(this);
      }
   }

   public void notifyNoteOff() {
      for (NoteHandler handler : handlers) {
         handler.noteOff(this);
      }
   }

   public interface NoteHandler {
      void noteOn(NoteInfo event);

      void noteOff(NoteInfo event);
   }

}
