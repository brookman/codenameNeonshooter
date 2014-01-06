package eu32k.neonshooter.core.entitySystem.component;

import com.badlogic.gdx.utils.Pool;

import eu32k.gdx.artemis.base.Component;

public class DeactivateComponent extends Component {

   public Pool pool;

   public DeactivateComponent init(Pool pool) {
      this.pool = pool;
      return this;
   }
}
