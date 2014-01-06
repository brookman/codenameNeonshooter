package eu32k.neonshooter.core.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;

import eu32k.gdx.artemis.base.managers.GroupManager;
import eu32k.gdx.artemis.extension.ExtendedWorld;
import eu32k.gdx.artemis.extension.system.CameraSystem;
import eu32k.gdx.artemis.extension.system.PhysicsSystem;
import eu32k.gdx.artemis.extension.system.RemoveSystem;
import eu32k.neonshooter.core.Neon;
import eu32k.neonshooter.core.entitySystem.common.Mappers;
import eu32k.neonshooter.core.entitySystem.factory.EntityFactory;
import eu32k.neonshooter.core.entitySystem.system.CollisionSystem;
import eu32k.neonshooter.core.entitySystem.system.ControlSystem;
import eu32k.neonshooter.core.entitySystem.system.DeactivateSystem;
import eu32k.neonshooter.core.entitySystem.system.EnemySystem;
import eu32k.neonshooter.core.entitySystem.system.FxSystem;
import eu32k.neonshooter.core.entitySystem.system.SpawnerSystem;
import eu32k.neonshooter.core.entitySystem.system.WeaponSystem;
import eu32k.neonshooter.core.spawning.SpawnerInfo;

public class InGameScreen implements Screen {

   private static final float TIME_SCALE_RATE = 0.8f;
   private Stage gameStage;
   private Stage hudStage;

   private Label fpsLabel;
   private Touchpad padLeft;
   private Touchpad padRight;

   private World box2dWorld;
   private ExtendedWorld artemisWorld;

   private Box2DDebugRenderer debugRenderer;
   private MapRenderer mapRenderer;

   @Override
   public void show() {
      EntityFactory factory = null;
      if (gameStage == null) {
         gameStage = new Stage(Neon.VIRTUAL_WIDTH, Neon.VIRTUAL_HEIGHT, true);

         createHud();

         box2dWorld = new World(new Vector2(0, 0), true);
         artemisWorld = new ExtendedWorld(box2dWorld, gameStage);
         artemisWorld.setManager(new GroupManager());

         factory = new EntityFactory(artemisWorld, gameStage);

         artemisWorld.setSystem(new WeaponSystem(factory, gameStage));
         artemisWorld.setSystem(new PhysicsSystem(box2dWorld));
         artemisWorld.setSystem(new CollisionSystem(box2dWorld, factory));
         artemisWorld.setSystem(new DeactivateSystem());
         artemisWorld.setSystem(new ControlSystem());
         artemisWorld.setSystem(new EnemySystem());
         artemisWorld.setSystem(new RemoveSystem());
         artemisWorld.setSystem(new FxSystem());
         artemisWorld.setSystem(new CameraSystem(gameStage.getCamera()));
         artemisWorld.setSystem(new SpawnerSystem(factory));

         artemisWorld.initialize();
         Mappers.init(artemisWorld);

         debugRenderer = new Box2DDebugRenderer();
         // debugRenderer.setDrawAABBs(true);
         debugRenderer.setDrawBodies(true);
         // debugRenderer.setDrawContacts(true);
         debugRenderer.setDrawInactiveBodies(true);
         debugRenderer.setDrawJoints(true);
         debugRenderer.setDrawVelocities(true);

      }
      Neon.music.loadTrack();
      Neon.levels.loadLevel(box2dWorld);
      this.mapRenderer = Neon.levels.getMapRenderer();

      factory.createPlayerShip(Neon.game.map.playerSpawn.x, Neon.game.map.playerSpawn.y).addToWorld();
      for (SpawnerInfo spawner : Neon.game.map.enemySpawns) {
         factory.createSpawner(spawner).addToWorld();
      }

      Neon.music.play();
      Gdx.input.setInputProcessor(hudStage);
      Neon.game.timeScale = 1f;
   }

   private void createHud() {
      hudStage = new Stage();

      Table table = new Table(Neon.assets.skin);
      table.setFillParent(true);
      // table.debug();

      fpsLabel = new Label("", Neon.assets.skin);
      fpsLabel.setFontScale(0.5f);

      padLeft = new Touchpad(20.0f, Neon.assets.skin);
      padRight = new Touchpad(20.0f, Neon.assets.skin);

      table.add(fpsLabel).expand().top().left().pad(10);
      table.row();
      table.add(padLeft).prefWidth(150).prefHeight(150).expand().bottom().left().pad(10);
      table.add(padRight).prefWidth(150).prefHeight(150).expand().bottom().right().pad(10);

      hudStage.addActor(table);
      hudStage.addActor(Neon.music.getMidiDisplay());
   }

   @Override
   public void render(float delta) {
      float scale = Neon.game.timeScale;
      float lastScale = scale;
      float target = Neon.game.targetTimeScale;

      handleTimeScale(delta, scale, lastScale, target);

      float scaledDelta = delta * Neon.game.timeScale;
      Neon.controls.padLeft.set(padLeft.getKnobPercentX(), padLeft.getKnobPercentY());
      Neon.controls.padRight.set(padRight.getKnobPercentX(), padRight.getKnobPercentY());

      Neon.fx.update(scaledDelta);

      gameStage.act(scaledDelta);
      hudStage.act(scaledDelta);

      artemisWorld.setDelta(scaledDelta);
      artemisWorld.process();

      fpsLabel.setText("FPS: " + Gdx.graphics.getFramesPerSecond());

      mapRenderer.setView((OrthographicCamera) gameStage.getCamera());
      mapRenderer.render();

      gameStage.draw();
      hudStage.draw();

      Neon.music.update(scaledDelta);

      // Table.drawDebug(hudStage);
      // debugRenderer.render(box2dWorld, gameStage.getCamera().combined);
   }

   private void handleTimeScale(float delta, float scale, float lastScale, float target) {
      if (scale < target) {
         scale += TIME_SCALE_RATE * delta;
         if (scale > target) {
            scale = target;
         }
         Neon.game.timeScale = scale;
      } else if (scale > target) {
         scale -= TIME_SCALE_RATE * delta;
         if (scale < target) {
            scale = target;
         }
      }
      if (lastScale != scale) {
         Neon.game.timeScale = scale;
         Neon.music.pitch(Neon.game.timeScale);
         Gdx.app.log("InGameScreen", "Sound scale set to " + Neon.game.timeScale);
      }
   }

   @Override
   public void resize(int width, int height) {
      Rectangle viewport = Neon.viewport;
      gameStage.setViewport(gameStage.getWidth(), gameStage.getHeight(), true, viewport.x, viewport.y, viewport.width, viewport.height);
      hudStage.setViewport(hudStage.getWidth(), hudStage.getHeight(), true, viewport.x, viewport.y, viewport.width, viewport.height);
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
