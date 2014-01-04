package eu32k.neonshooter.core.entitySystem.component;

import eu32k.gdx.artemis.base.Component;

public class PositionComponent extends Component {
   public float x;
   public float y;

   public Component init() {
      x = 0;
      y = 0;
      return this;
   }
}
