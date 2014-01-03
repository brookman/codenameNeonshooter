package eu32k.neonshooter.core.input;

import com.badlogic.gdx.Gdx;

public class TouchTrap extends Trap {

   private int key;

   public TouchTrap() {
      this(-1);
   }

   public TouchTrap(int key) {
      this.key = key;
   }

   @Override
   protected boolean isPressed() {
      return key == -1 ? Gdx.input.isTouched() : Gdx.input.isTouched(key);
   }

}
