package eu32k.neonshooter.core.rendering.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class VectorUtils {

   private static Vector2 temp1 = new Vector2();
   private static Vector2 temp2 = new Vector2();

   public static float getAngleOnStage(Stage stage, float x1, float y1, float x2, float y2) {
      temp1.set(x1, y1);
      temp2.set(x2, y2);
      stage.screenToStageCoordinates(temp1);
      temp1.sub(temp2);
      return temp1.angle();
   }
}
