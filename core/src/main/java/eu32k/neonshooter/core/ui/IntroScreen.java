package eu32k.neonshooter.core.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController.AnimationDesc;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController.AnimationListener;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

import eu32k.neonshooter.core.Neon;

public class IntroScreen implements Screen {

	public Environment environment;
	public PerspectiveCamera camera;
	public ModelBatch modelBatch;
	public Model model;
	public ModelInstance instance;
	private AnimationController animation;

	public IntroScreen() {
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1.f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

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
		Model model = Neon.assets.manager.get("models/test.g3db", Model.class);
		instance = new ModelInstance(model);
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
		modelBatch.render(instance, environment);
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
