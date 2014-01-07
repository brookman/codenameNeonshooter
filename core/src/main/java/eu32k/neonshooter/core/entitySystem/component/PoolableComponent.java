package eu32k.neonshooter.core.entitySystem.component;

import com.badlogic.gdx.utils.Pool;

import eu32k.gdx.artemis.base.Component;

public class PoolableComponent<T> extends Component {

   public Pool<T> pool;

   public PoolableComponent<T> init(Pool<T> pool) {
      this.pool = pool;
      return this;
   }
}
