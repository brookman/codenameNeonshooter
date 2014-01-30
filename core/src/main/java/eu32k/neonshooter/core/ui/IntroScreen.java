package eu32k.neonshooter.core.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController.AnimationDesc;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController.AnimationListener;

import eu32k.neonshooter.core.Neon;
import eu32k.neonshooter.core.model.Asset;
import eu32k.neonshooter.core.model.loading.NeonScreen;

public class IntroScreen extends NeonScreen {

   private Environment environment;
   private PerspectiveCamera camera;
   private ModelBatch modelBatch;
   private ModelInstance instance;
   private AnimationController animation;
   private Asset<Model> model;

   @Override
   public void initAssets() {
      model = createAsset("models/text.g3dj", Model.class);
   }

   @Override
   protected void init() {
      environment = new Environment();
      environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1.f));
      environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

      camera = new PerspectiveCamera(50, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
      camera.position.set(0f, 1f, 5f);
      camera.lookAt(0, 0, 0);
      camera.near = 0.1f;
      camera.far = 300f;
      camera.update();

      modelBatch = new ModelBatch();

      instance = new ModelInstance(model.get());
      animation = new AnimationController(instance);
      animation.animate("Default Take", 1, 1f, null, 0.0f).listener = new AnimationListener() {

         @Override
         public void onLoop(AnimationDesc animation) {
         }

         @Override
         public void onEnd(AnimationDesc animation) {
            Neon.ui.popScreen();
         }
      };
   }

   @Override
   public void render(float delta) {
      animation.update(delta);

      modelBatch.begin(camera);
      modelBatch.render(instance, environment);
      modelBatch.end();
   }

   @Override
   public void dispose() {
      modelBatch.dispose();
      model.get().dispose();
   }

   @Override
   public boolean keyDown(int keycode) {
      Neon.ui.popScreen();
      return false;
   }

   @Override
   public boolean touchDown(int screenX, int screenY, int pointer, int button) {
      Neon.ui.popScreen();
      return false;
   }

   @Override
   public void reset() {
      // NOP
   }
}
