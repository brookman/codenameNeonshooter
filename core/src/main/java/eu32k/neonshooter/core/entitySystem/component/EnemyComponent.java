package eu32k.neonshooter.core.entitySystem.component;

import com.badlogic.gdx.math.MathUtils;

import eu32k.gdx.artemis.base.Component;

public class EnemyComponent extends Component {

   public enum EnemyBehaviour {
      PASSIVE, FOLLOW, FLEE, WANDER
   };

   public EnemyBehaviour behaviour;
   public float currentWanderDirection;

   public EnemyComponent init(EnemyBehaviour behaviour) {
      this.behaviour = behaviour;
      currentWanderDirection = MathUtils.random(360);
      return this;
   }
}
