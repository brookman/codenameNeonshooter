package eu32k.neonshooter.core.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import eu32k.neonshooter.core.Neon;
import eu32k.neonshooter.core.model.LoadableScreen;

public class SettingsScreen extends LoadableScreen {

   private Slider musicVolume;
   private Slider fxVolume;

   @Override
   public void initAssets() {

   }

   @Override
   protected void init() {
      Table table = new Table(Neon.assets.skin);
      table.setFillParent(true);
      stage.addActor(table);

      table.add(new Label("Music volumne", Neon.assets.skin));
      musicVolume = new Slider(0, 1, 0.01f, false, Neon.assets.skin);
      musicVolume.addListener(new ChangeListener() {
         @Override
         public void changed(ChangeEvent event, Actor actor) {
            Neon.settings.musicVolume = musicVolume.getValue();
         }
      });
      table.add(musicVolume).row();

      table.add(new Label("Fx volumne", Neon.assets.skin));
      fxVolume = new Slider(0, 1, 0.01f, false, Neon.assets.skin);
      fxVolume.addListener(new ChangeListener() {
         @Override
         public void changed(ChangeEvent event, Actor actor) {
            Neon.settings.fxVolume = fxVolume.getValue();
         }
      });
      table.add(fxVolume).row();

      TextButton reset = new TextButton("Reset", Neon.assets.skin);
      table.add(reset).row();
      reset.addListener(new InputListener() {
         @Override
         public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            Neon.settings.reset();
            load();
            return false;
         }
      });

      TextButton cancel = new TextButton("Cancel", Neon.assets.skin);
      table.add(cancel).row();
      cancel.addListener(new InputListener() {
         @Override
         public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            Neon.settings.load();
            Neon.ui.showScreen(MainMenuScreen.class);
            return false;
         }
      });

      TextButton save = new TextButton("Save", Neon.assets.skin);
      table.add(save);
      save.addListener(new InputListener() {
         @Override
         public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            Neon.settings.save();
            Neon.ui.showScreen(MainMenuScreen.class);
            return false;
         }
      });
   }

   private void load() {
      Gdx.app.log("SettingsScreen", "load");
      musicVolume.setValue(Neon.settings.musicVolume);
      fxVolume.setValue(Neon.settings.fxVolume);
   }

   @Override
   public void show() {
      super.show();
      load();
   }

   @Override
   public void dispose() {
   }

   @Override
   public void reset() {
   }
}
