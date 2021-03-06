package eu32k.neonshooter.core.ui;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapRenderer;
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
import eu32k.neonshooter.core.entitySystem.system.GravitySystem;
import eu32k.neonshooter.core.entitySystem.system.SpawnerSystem;
import eu32k.neonshooter.core.entitySystem.system.WeaponSystem;
import eu32k.neonshooter.core.model.loading.NeonScreen;
import eu32k.neonshooter.core.spawning.SpawnerInfo;

public class InGameScreen extends NeonScreen {

   private Stage gameStage;
   private Stage hudStage;

   private Label fpsLabel;

   private World box2dWorld;
   private ExtendedWorld artemisWorld;

   private Box2DDebugRenderer debugRenderer;
   private MapRenderer mapRenderer;

   @Override
   protected void init() {
      gameStage = stage;
      gameStage.setViewport(Neon.VIRTUAL_WIDTH, Neon.VIRTUAL_HEIGHT, true);
      hudStage = addStage();

      createHud();

      box2dWorld = new World(new Vector2(0, 0), true);
      artemisWorld = new ExtendedWorld(box2dWorld, gameStage);
      artemisWorld.setManager(new GroupManager());

      EntityFactory factory = new EntityFactory(artemisWorld, gameStage);

      artemisWorld.setSystem(new WeaponSystem(factory, gameStage));
      artemisWorld.setSystem(new GravitySystem());
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

      Neon.music.loadTrack();
      Neon.levels.loadLevel(box2dWorld);
      mapRenderer = Neon.levels.getMapRenderer();

      factory.createPlayerShip(Neon.game.map.playerSpawn.x, Neon.game.map.playerSpawn.y).addToWorld();
      for (SpawnerInfo spawner : Neon.game.map.enemySpawns) {
         factory.createSpawner(spawner).addToWorld();
      }
      for (Vector2 blackHole : Neon.game.map.blackHoles) {
         factory.createBlackHole(blackHole.x, blackHole.y).addToWorld();
      }
   }

   @Override
   public boolean keyDown(int key) {
      if (key == Keys.P || key == Keys.ESCAPE) {
         Neon.ui.pushScreen(PauseScreen.class);
      }
      return false;
   }

   @Override
   public void show() {
      Neon.music.play(Neon.settings.musicVolume);
      Gdx.input.setInputProcessor(multiplexer);
      Neon.game.timeScale.setFade(1, 1);
   }

   private void createHud() {
      Table table = new Table(Neon.assets.skin);
      table.setFillParent(true);
      // table.debug();

      fpsLabel = new Label("", Neon.assets.skin);
      fpsLabel.setFontScale(0.5f);

      table.add(fpsLabel).expand().top().left().pad(10);
      if (Gdx.app.getType() == ApplicationType.Android) {
         table.row();
         Neon.controls.padLeft = new Touchpad(20.0f, Neon.assets.skin);
         Neon.controls.padRight = new Touchpad(20.0f, Neon.assets.skin);
         table.add(Neon.controls.padLeft).prefWidth(150).prefHeight(150).expand().bottom().left().pad(10);
         table.add(Neon.controls.padLeft).prefWidth(150).prefHeight(150).expand().bottom().right().pad(10);
      }

      hudStage.addActor(table);
      hudStage.addActor(Neon.music.getMidiDisplay());
   }

   @Override
   public void render(float delta) {
      float scaledDelta = Neon.game.timeScale.update(delta) * delta;

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

   @Override
   public void initAssets() {
      // NOP
   }

   @Override
   public void reset() {
      // NOP
   }

   @Override
   public void dispose() {
      // NOP
   }
}
