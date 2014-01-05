package eu32k.neonshooter.core.spawning.trigger;

import java.util.ArrayList;
import java.util.List;

import eu32k.gdx.artemis.base.Entity;

public class OrTrigger implements Trigger {
   private List<Trigger> triggers;

   public OrTrigger() {
      this.triggers = new ArrayList<Trigger>();
   }

   public void init(List<Trigger> triggers) {
      for (Trigger trigger : triggers) {
         this.triggers.add(trigger);
      }
   }

   @Override
   public boolean triggers(Entity e) {
      for (Trigger trigger : triggers) {
         if (trigger.triggers(e)) {
            return true;
         }
      }
      return false;
   }

   @Override
   public void reset() {
      for (Trigger trigger : triggers) {
         trigger.reset();
      }
   }

}
