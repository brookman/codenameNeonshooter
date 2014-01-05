package eu32k.neonshooter.core.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

import eu32k.neonshooter.core.Neon;

public class StartScreen implements Screen, EventListener {
   private Stage stage;
   private TextButton backButton;
   private TextButton startButton;

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
         stage = new Stage();
         Skin skin = Neon.assets.skin;
         Table mainTable = new Table(skin);
         mainTable.setFillParent(true);

         Table levelTable = new Table(skin);
         ScrollPane levelScrollPane = new ScrollPane(levelTable);

         Table soundTable = new Table(skin);
         ScrollPane soundScrollPane = new ScrollPane(soundTable);

         Label levelCaption = new Label("Levels", skin);
         Label soundCaption = new Label("Sound Sets", skin);

         mainTable.add(levelCaption);
         mainTable.add(soundCaption);

         mainTable.row();

         mainTable.add(levelScrollPane);
         mainTable.add(soundScrollPane);

         backButton = new TextButton("Back", skin);
         backButton.addListener(this);
         startButton = new TextButton("Start", skin);
         startButton.addListener(this);

         mainTable.row();
         mainTable.add(backButton);
         mainTable.add(startButton);

         stage.addActor(mainTable);
      }

      Gdx.input.setInputProcessor(stage);
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

   @Override
   public boolean handle(Event event) {
      if (event instanceof ChangeEvent) {
         if (event.getTarget() == startButton) {
            Neon.levels.prepareAnyArcadeLevel();
            Neon.music.prepareAnyArcadeTrack();
            Neon.ui.loadThenShowScreen(InGameScreen.class);
         } else if (event.getTarget() == backButton) {
            Neon.ui.showScreen(MainMenuScreen.class);
         }
      }
      return false;
   }

}
