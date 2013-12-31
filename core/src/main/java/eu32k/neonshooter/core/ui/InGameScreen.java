package eu32k.neonshooter.core.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import eu32k.gdx.artemis.base.managers.GroupManager;
import eu32k.gdx.artemis.extension.ExtendedWorld;
import eu32k.gdx.artemis.extension.system.PhysicsSystem;
import eu32k.gdx.artemis.extension.system.RemoveSystem;
import eu32k.neonshooter.core.Neon;
import eu32k.neonshooter.core.entitySystem.factory.EntityFactory;
import eu32k.neonshooter.core.entitySystem.system.ControlSystem;

public class InGameScreen implements Screen {

   private Stage gameStage;
   private Stage hudStage;
   private Label fpsLabel;

   private World box2dWorld;
   private ExtendedWorld artemisWorld;

   private Box2DDebugRenderer debugRenderer;

   @Override
   public void show() {
      if (gameStage == null) {
         gameStage = new Stage(Neon.VIRTUAL_WIDTH, Neon.VIRTUAL_HEIGHT, true);
         hudStage = new Stage();
         fpsLabel = new Label(" ", Neon.assets.skin);
         hudStage.addActor(fpsLabel);

         box2dWorld = new World(new Vector2(0, 0), true);
         artemisWorld = new ExtendedWorld(box2dWorld, gameStage);
         artemisWorld.setManager(new GroupManager());

         EntityFactory shipFactory = new EntityFactory(artemisWorld, gameStage);

         artemisWorld.setSystem(new PhysicsSystem(box2dWorld));
         artemisWorld.setSystem(new ControlSystem());
         artemisWorld.setSystem(new RemoveSystem());

         artemisWorld.initialize();

         shipFactory.createPlayerShip(3, 3).addToWorld();
         shipFactory.createChlotz(4, 3).addToWorld();
         shipFactory.createChlotz(5, 3.5f).addToWorld();
         shipFactory.createChlotz(5, 2.5f).addToWorld();

         shipFactory.createTile(1, 4, 0).addToWorld();
         shipFactory.createTile(1, 3, 0).addToWorld();
         shipFactory.createTile(1, 2, 0).addToWorld();

         shipFactory.createTile(1, 1, 10).addToWorld();

         shipFactory.createTile(2, 1, 1).addToWorld();
         shipFactory.createTile(3, 1, 1).addToWorld();
         shipFactory.createTile(4, 1, 1).addToWorld();
         shipFactory.createTile(5, 1, 1).addToWorld();

         shipFactory.createTile(6, 1, 11).addToWorld();
         shipFactory.createTile(6, 2, 2).addToWorld();
         shipFactory.createTile(6, 3, 5).addToWorld();

         shipFactory.createTile(7, 3, 1).addToWorld();
         shipFactory.createTile(8, 3, 4).addToWorld();
         shipFactory.createTile(8, 2, 0).addToWorld();
         shipFactory.createTile(8, 1, 10).addToWorld();

         shipFactory.createTile(9, 1, 1).addToWorld();

         shipFactory.createTile(10, 1, 11).addToWorld();

         shipFactory.createTile(10, 2, 2).addToWorld();
         shipFactory.createTile(10, 3, 2).addToWorld();
         shipFactory.createTile(10, 4, 2).addToWorld();

         shipFactory.createTile(10, 5, 8).addToWorld();

         shipFactory.createTile(2, 5, 3).addToWorld();
         shipFactory.createTile(3, 5, 3).addToWorld();
         shipFactory.createTile(4, 5, 3).addToWorld();
         shipFactory.createTile(5, 5, 3).addToWorld();
         shipFactory.createTile(6, 5, 3).addToWorld();
         shipFactory.createTile(7, 5, 3).addToWorld();
         shipFactory.createTile(8, 5, 3).addToWorld();
         shipFactory.createTile(9, 5, 3).addToWorld();

         shipFactory.createTile(1, 5, 9).addToWorld();

         debugRenderer = new Box2DDebugRenderer();
         // debugRenderer.setDrawAABBs(true);
         debugRenderer.setDrawBodies(true);
         // debugRenderer.setDrawContacts(true);
         debugRenderer.setDrawInactiveBodies(true);
         debugRenderer.setDrawJoints(true);
         debugRenderer.setDrawVelocities(true);
      }
      TiledMap map = Neon.assets.manager.get(Neon.game.nextLevel, TiledMap.class);
      Neon.game.level().load(map);
      Gdx.input.setInputProcessor(hudStage);
   }

   @Override
   public void render(float delta) {
      gameStage.act(delta);
      hudStage.act(delta);

      artemisWorld.setDelta(delta);
      artemisWorld.process();

      fpsLabel.setText(" FPS: " + Gdx.graphics.getFramesPerSecond());

      gameStage.draw();
      hudStage.draw();

      // debugRenderer.render(box2dWorld, gameStage.getCamera().combined);
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
