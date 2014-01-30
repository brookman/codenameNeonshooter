package eu32k.neonshooter.core.model.state;

import java.util.Stack;

import eu32k.neonshooter.core.model.loading.NeonScreen;

public class ScreenStack extends Stack<NeonScreen> {
   private static final long serialVersionUID = -3035235424591732505L;

   public void swapTop(NeonScreen newTop) {
      pop();
      push(newTop);
   }
}
