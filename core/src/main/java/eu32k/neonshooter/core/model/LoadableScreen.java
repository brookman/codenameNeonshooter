package eu32k.neonshooter.core.model;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;

import eu32k.neonshooter.core.Neon;

public abstract class LoadableScreen implements Screen, Loadable {

   protected Stage stage;
   protected List<Stage> stages = new ArrayList<Stage>();

   protected AssetManager assetManager;
   private boolean initialized = false;

   public LoadableScreen() {
      stage = addStage();
   }

   protected Stage addStage() {
      Stage newStage = new Stage();
      stages.add(newStage);
      return newStage;
   }

   @Override
   public void initialize() {
      if (!initialized) {
         init();
         initialized = true;
      }
   }

   protected abstract void init();

   protected <T> Asset<T> getAsset(String fileName, Class<T> assetType) {
      return new Asset<T>(fileName, assetType);
   }

   @Override
   public void render(float delta) {
      for (Stage stage : stages) {
         stage.act(delta);
         stage.draw();
      }
   }

   @Override
   public void hide() {
      // TODO Auto-generated method stub

   }

   @Override
   public void pause() {
      // TODO Auto-generated method stub

   }

   @Override
   public void resize(int width, int height) {
      Rectangle viewport = Neon.viewport;
      for (Stage s : stages) {
         s.setViewport(stage.getWidth(), stage.getHeight(), true, (int) viewport.x, (int) viewport.y, (int) viewport.width, (int) viewport.height);
      }
   }

   @Override
   public void resume() {
      // TODO Auto-generated method stub

   }

   @Override
   public void show() {
      Gdx.input.setInputProcessor(stage);
   }
}
