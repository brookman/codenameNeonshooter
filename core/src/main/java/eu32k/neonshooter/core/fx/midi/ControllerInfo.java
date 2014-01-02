package eu32k.neonshooter.core.fx.midi;

import java.util.ArrayList;
import java.util.List;

public class ControllerInfo {
   public int channel;
   public int type;
   public int initValue;
   public int value;
   public String trackName;
   private List<ControllerHandler> handlers;

   public ControllerInfo(int channel, int note) {
      this.channel = channel;
      this.type = note;
      handlers = new ArrayList<ControllerHandler>();
   }

   public void addHandler(ControllerHandler handler) {
      handlers.add(handler);
   }

   public void removeHandler(ControllerHandler handler) {
      handlers.remove(handler);
   }

   public void notifyHandlers() {
      for (ControllerHandler handler : handlers) {
         handler.controllerChanged(this);
      }
   }

   public interface ControllerHandler {
      void controllerChanged(ControllerInfo event);
   }
}
