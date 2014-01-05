package eu32k.neonshooter.core.entitySystem.component;

import eu32k.gdx.artemis.base.Component;
import eu32k.neonshooter.core.spawning.spawner.Spawner;
import eu32k.neonshooter.core.spawning.trigger.Trigger;

public class SpawnerComponent extends Component {
   public boolean triggeredLastUpdate;
   public Trigger trigger;
   public Spawner spawner;

   public SpawnerComponent init(Trigger trigger, Spawner spawner) {
      this.trigger = trigger;
      this.spawner = spawner;
      return this;
   }
}
