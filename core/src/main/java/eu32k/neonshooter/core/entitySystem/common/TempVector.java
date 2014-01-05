package eu32k.neonshooter.core.entitySystem.common;

import com.badlogic.gdx.math.Vector2;

public class TempVector extends Vector2 {
   private static final long serialVersionUID = 1L;

   public TempVector s(float x, float y) {
      this.set(x, y);
      return this;
   }
}
