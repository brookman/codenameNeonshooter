package eu32k.neonshooter.core.fx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import eu32k.neonshooter.core.fx.midi.NoteInfo;
import eu32k.neonshooter.core.fx.midi.NoteInfo.NoteHandler;

public class FxManager {
   public final static Color[] PRIMARY_COLORS = new Color[] { new Color(1f, 0f, 0f, 1f), new Color(0f, 1f, 0f, 1f), new Color(0f, 0f, 1f, 1f) };

   public Color primaryColor;
   private Stage fxStage;
   private Actor primaryColorActor;

   private NoteHandler beatHandler;

   private Actor flashColorActor;

   public FxManager() {
      beatHandler = new NoteHandler() {

         @Override
         public void noteOn(NoteInfo event) {
            beat();
         }

         @Override
         public void noteOff(NoteInfo event) {

         }
      };
   }

   protected void beat() {
      flashColorActor.clearActions();
      flashColorActor.addAction(Actions.sequence(Actions.color(Color.WHITE, 0.01f), Actions.color(Color.BLACK, 0.25f)));
   }

   public void create() {
      primaryColor = new Color(PRIMARY_COLORS[0]);
      fxStage = new Stage();
      primaryColorActor = new Actor();
      primaryColorActor.setColor(primaryColor);
      flashColorActor = new Actor();
      flashColorActor.setColor(Color.BLACK);
      float dur = 3f;
      Color[] c = PRIMARY_COLORS;
      primaryColorActor.addAction(Actions.forever(Actions.sequence(Actions.color(c[1], dur), Actions.color(c[2], dur), Actions.color(c[0], dur))));
      fxStage.addActor(primaryColorActor);
      fxStage.addActor(flashColorActor);
   }

   public void update(float delta) {
      // TODO: Move fx stage and stuff to own class
      fxStage.act(delta);
      primaryColor.set(primaryColorActor.getColor());
      Color flash = flashColorActor.getColor();
      primaryColor.r = addTo1(primaryColor.r, flash.r);
      primaryColor.g = addTo1(primaryColor.g, flash.g);
      primaryColor.b = addTo1(primaryColor.b, flash.b);
   }

   public float addTo1(float a, float b) {
      float res = a + b;
      if (res > 1f) {
         return 1f;
      }
      if (res < 0f) {
         return 0f;
      }
      return res;
   }

   public NoteHandler beatHandler() {
      return beatHandler;
   }
}
