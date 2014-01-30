package eu32k.neonshooter.core.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import eu32k.neonshooter.core.Neon;
import eu32k.neonshooter.core.model.loading.NeonScreen;

public class GameOverScreen extends NeonScreen {

   @Override
   protected void init() {
      Table table = new Table(Neon.assets.skin);
      table.setFillParent(true);
      stage.addActor(table);

      Label gameOverLabel = new Label("Game Over", Neon.assets.skin);
      table.add(gameOverLabel).row();

      TextButton retry = new TextButton("Retry", Neon.assets.skin);
      table.add(retry).row();
      retry.addListener(new InputListener() {
         @Override
         public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            Neon.ui.popScreen();
            // reset ???
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
   public void dispose() {
      // TODO Auto-generated method stub

   }

   @Override
   public void initAssets() {
      // TODO Auto-generated method stub

   }

   @Override
   public void reset() {
      // TODO Auto-generated method stub
   }
}