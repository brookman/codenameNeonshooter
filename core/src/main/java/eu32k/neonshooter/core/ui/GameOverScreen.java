package eu32k.neonshooter.core.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import eu32k.neonshooter.core.Neon;

public class GameOverScreen implements Screen {

   private Stage stage;
   private TextButton select;
   private TextButton exit;

   @Override
   public void render(float delta) {
      stage.act(delta);
      stage.draw();
   }

   @Override
   public void resize(int width, int height) {
      Rectangle viewport = Neon.viewport;
      stage.setViewport(stage.getWidth(), stage.getHeight(), true, viewport.x, viewport.y, viewport.width, viewport.height);
   }

   @Override
   public void show() {
      if (stage == null) {
         buildStage();
      }
      Gdx.input.setInputProcessor(stage);
   }

   private void buildStage() {
      stage = new Stage();

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
            Neon.ui.showScreen(StartScreen.class);
            return false;
         }
      });

      TextButton select = new TextButton("Level select", Neon.assets.skin);
      table.add(select).row();
      select.addListener(new InputListener() {
         @Override
         public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            Neon.ui.showScreen(StartScreen.class);
            return false;
         }
      });

      TextButton exit = new TextButton("Main menu", Neon.assets.skin);
      table.add(exit);
      exit.addListener(new InputListener() {
         @Override
         public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            Neon.ui.showScreen(MainMenuScreen.class);
            return false;
         }
      });
   }

   @Override
   public void hide() {

   }

   @Override
   public void pause() {

   }

   @Override
   public void resume() {

   }

   @Override
   public void dispose() {

   }
}