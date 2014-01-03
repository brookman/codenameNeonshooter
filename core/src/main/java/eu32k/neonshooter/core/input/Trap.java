package eu32k.neonshooter.core.input;

public abstract class Trap {

   public boolean isDown;
   public boolean isPressed;
   public boolean isUp;

   protected abstract boolean isPressed();

   public void flush() {
      isDown = false;
      isPressed = true;
      isUp = true;
   }

   public void update() {
      boolean wasPressed = isPressed;
      isPressed = isPressed();
      isDown = isPressed && !wasPressed;
      isUp = !isPressed && wasPressed;
      // if(isDown)Gdx.app.log("", "Trap down");
      // if(isUp)Gdx.app.log("", "Trap up");
   }

}