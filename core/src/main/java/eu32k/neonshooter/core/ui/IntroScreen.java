package eu32k.neonshooter.core.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.lights.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.lights.Lights;
import com.badlogic.gdx.graphics.g3d.materials.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.materials.Material;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController.AnimationDesc;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController.AnimationListener;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

import eu32k.neonshooter.core.Neon;

public class IntroScreen implements Screen {

	public Lights lights;
	public PerspectiveCamera camera;
	public ModelBatch modelBatch;
	public Model model;
	public ModelInstance instance;
	private AnimationController animation;

	public IntroScreen() {

		lights = new Lights(new Color(0.4f, 0.4f, 0.4f, 1.0f));

		DirectionalLight dl = new DirectionalLight();
		dl.set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f);

		lights.add(dl);

		camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(3f, 3f, 3f);
		camera.lookAt(0, 0, 0);
		camera.near = 0.1f;
		camera.far = 300f;
		camera.update();

		modelBatch = new ModelBatch();

		ModelBuilder modelBuilder = new ModelBuilder();
		model = modelBuilder.createBox(5f, 5f, 5f, new Material(ColorAttribute.createDiffuse(Color.GREEN)), Usage.Position | Usage.Normal);

		Neon.assets.manager.finishLoading();
		instance = new ModelInstance(Neon.assets.manager.get("models/test.g3db", Model.class));
		animation = new AnimationController(instance);
		animation.animate("Default Take", 1, 1f, null, 0.0f).listener = new AnimationListener() {

			@Override
			public void onLoop(AnimationDesc animation) {
			}

			@Override
			public void onEnd(AnimationDesc animation) {
				Neon.ui.showScreen(MainMenuScreen.class);
			}
		};
	}

	@Override
	public void render(float delta) {
		animation.update(delta);

		modelBatch.begin(camera);
		modelBatch.render(instance, lights);
		modelBatch.end();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {

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
		modelBatch.dispose();
		model.dispose();
	}

}
