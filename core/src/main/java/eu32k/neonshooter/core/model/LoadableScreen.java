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
   public void resize(int arg0, int arg1) {
      Gdx.app.log("LoadableScreen", "resize " + Neon.viewport);
      Rectangle viewport = Neon.viewport;
      for (Stage stage : stages) {
         stage.setViewport(stage.getWidth(), stage.getHeight(), true, viewport.x, viewport.y, viewport.width, viewport.height);
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
