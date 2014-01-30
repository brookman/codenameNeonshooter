package eu32k.neonshooter.core.model.loading;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;

import eu32k.neonshooter.core.Neon;
import eu32k.neonshooter.core.model.Asset;

public abstract class NeonScreen implements Screen, Loadable, InputProcessor {

   protected InputMultiplexer multiplexer;

   protected List<Stage> stages = new ArrayList<Stage>();
   protected Stage stage;

   protected AssetManager assetManager;
   private boolean initialized = false;

   public NeonScreen() {
      multiplexer = new InputMultiplexer();
      multiplexer.addProcessor(this);

      stages = new ArrayList<Stage>();
      stage = addStage();
   }

   protected Stage addStage() {
      Stage newStage = new Stage();
      multiplexer.addProcessor(newStage);
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

   protected static <T> Asset<T> createAsset(String fileName, Class<T> assetType) {
      return new Asset<T>(fileName, assetType);
   }

   @Override
   public void render(float delta) {
      for (Stage s : stages) {
         s.act(delta);
         s.draw();
      }
   }

   @Override
   public void hide() {
      // NOP
   }

   @Override
   public void pause() {
      // NOP
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
      // NOP
   }

   @Override
   public void show() {
      Gdx.input.setInputProcessor(multiplexer);
   }

   @Override
   public boolean keyDown(int keycode) {
      return false;
   }

   @Override
   public boolean keyUp(int keycode) {
      return false;
   }

   @Override
   public boolean keyTyped(char character) {
      return false;
   }

   @Override
   public boolean touchDown(int screenX, int screenY, int pointer, int button) {
      return false;
   }

   @Override
   public boolean touchUp(int screenX, int screenY, int pointer, int button) {
      return false;
   }

   @Override
   public boolean touchDragged(int screenX, int screenY, int pointer) {
      return false;
   }

   @Override
   public boolean mouseMoved(int screenX, int screenY) {
      return false;
   }

   @Override
   public boolean scrolled(int amount) {
      return false;
   }
}
