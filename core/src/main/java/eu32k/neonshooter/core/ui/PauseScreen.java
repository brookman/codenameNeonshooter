package eu32k.neonshooter.core.ui;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import eu32k.neonshooter.core.Neon;
import eu32k.neonshooter.core.model.loading.NeonScreen;

public class PauseScreen extends NeonScreen {

   @Override
   protected void init() {
      Table table = new Table(Neon.assets.skin);
      table.setFillParent(true);
      stage.addActor(table);

      Label gameOverLabel = new Label("Paused", Neon.assets.skin);
      table.add(gameOverLabel).row();

      TextButton unpause = new TextButton("Continue", Neon.assets.skin);
      table.add(unpause).row();
      unpause.addListener(new InputListener() {
         @Override
         public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            Neon.ui.popScreen();
            return false;
         }
      });

      TextButton settings = new TextButton("Settings", Neon.assets.skin);
      table.add(settings).row();
      settings.addListener(new InputListener() {
         @Override
         public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            Neon.ui.pushScreen(SettingsScreen.class);
            return false;
         }
      });

      TextButton select = new TextButton("Back to level select", Neon.assets.skin);
      table.add(select).row();
      select.addListener(new InputListener() {
         @Override
         public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            Neon.ui.popScreens(2);
            return false;
         }
      });
   }

   @Override
   public boolean keyDown(int key) {
      if (key == Keys.P || key == Keys.ESCAPE) {
         Neon.ui.popScreen();
      }
      return false;
   }

   @Override
   public void dispose() {
      // NOP
   }

   @Override
   public void initAssets() {
      // NOP
   }

   @Override
   public void reset() {
      // NOP
   }
}