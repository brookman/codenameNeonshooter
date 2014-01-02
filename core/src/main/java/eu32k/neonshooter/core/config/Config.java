package eu32k.neonshooter.core.config;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Config {

   public int resolutionX;
   public int resolutionY;
   public float gameSpeed = 1.0f;
   public float delta = 0.0f;

   public final static Color[] PRIMARY_COLORS = new Color[] { new Color(1f, 0f, 0f, 1f), new Color(0f, 1f, 0f, 1f), new Color(0f, 0f, 1f, 1f) };

   public Color primaryColor;
   public Color filteredPrimaryColor;
   private Stage fxStage;
   private Actor primaryColorActor;

   public void create() {
      primaryColor = new Color(PRIMARY_COLORS[0]);
      filteredPrimaryColor = new Color();
      fxStage = new Stage();
      primaryColorActor = new Actor();
      primaryColorActor.setColor(primaryColor);

      float dur = 3f;
      Color[] c = PRIMARY_COLORS;
      primaryColorActor.addAction(Actions.forever(Actions.sequence(Actions.color(c[1], dur), Actions.color(c[2], dur), Actions.color(c[0], dur))));
      fxStage.addActor(primaryColorActor);
   }

   public void setResolution(int resolutionX, int resolutionY) {
      this.resolutionX = resolutionX;
      this.resolutionY = resolutionY;
   }

   public void update(float delta) {
      // TODO: Move fx stage and stuff to own class
      fxStage.act(delta);
      primaryColor.set(primaryColorActor.getColor());
   }
}
