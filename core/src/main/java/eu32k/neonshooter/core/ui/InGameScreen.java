package eu32k.neonshooter.core.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.leff.midi.MidiFile;

import eu32k.gdx.artemis.base.managers.GroupManager;
import eu32k.gdx.artemis.extension.ExtendedWorld;
import eu32k.gdx.artemis.extension.system.CameraSystem;
import eu32k.gdx.artemis.extension.system.PhysicsSystem;
import eu32k.gdx.artemis.extension.system.RemoveSystem;
import eu32k.neonshooter.core.Neon;
import eu32k.neonshooter.core.entitySystem.common.GameBits;
import eu32k.neonshooter.core.entitySystem.common.Mappers;
import eu32k.neonshooter.core.entitySystem.factory.EntityFactory;
import eu32k.neonshooter.core.entitySystem.system.ControlSystem;
import eu32k.neonshooter.core.entitySystem.system.FxSystem;
import eu32k.neonshooter.core.entitySystem.system.WeaponSystem;
import eu32k.neonshooter.core.fx.midi.ControlTracks;
import eu32k.neonshooter.core.fx.midi.ControlTracksDisplay;
import eu32k.neonshooter.core.rendering.LineRenderer;

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
   private Music music;

   private ControlTracks controlTracks;
   private ControlTracksDisplay midiDisplay;
   private Sound sound;
   private long soundId;
   private LineRenderer lineRenderer;

   public InGameScreen() {
      controlTracks = new ControlTracks();
   }

   @Override
   public void show() {
      if (gameStage == null) {
         gameStage = new Stage(Neon.VIRTUAL_WIDTH, Neon.VIRTUAL_HEIGHT, true);

         createHud();

         box2dWorld = new World(new Vector2(0, 0), true);
         artemisWorld = new ExtendedWorld(box2dWorld, gameStage);
         artemisWorld.setManager(new GroupManager());

         EntityFactory factory = new EntityFactory(artemisWorld, gameStage);

         artemisWorld.setSystem(new WeaponSystem(factory));
         artemisWorld.setSystem(new PhysicsSystem(box2dWorld));
         artemisWorld.setSystem(new ControlSystem());
         artemisWorld.setSystem(new RemoveSystem());
         artemisWorld.setSystem(new FxSystem());
         artemisWorld.setSystem(new CameraSystem(gameStage.getCamera()));

         artemisWorld.initialize();
         Mappers.init(artemisWorld);

         factory.createPlayerShip(3, 3).addToWorld();

         for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 12; x++) {
               factory.createChlotz(4f + x / 3f * 2, 3.5f + y / 3f * 2).addToWorld();
            }
         }

         TiledMap map = Neon.assets.manager.get("levels/line.tmx", TiledMap.class);
         MapLayer layer = map.getLayers().get(0);
         float s = 32;
         for (MapObject object : layer.getObjects()) {
            if (object instanceof PolylineMapObject) {
               PolylineMapObject polylineMapObject = (PolylineMapObject) object;
               float[] vertices = polylineMapObject.getPolyline().getTransformedVertices();
               for (int i = 0; i < vertices.length - 2; i += 2) {
                  createEdge(vertices[i] / s, vertices[i + 1] / s, vertices[i + 2] / s, vertices[i + 3] / s);
               }
            } else if (object instanceof RectangleMapObject) {
               RectangleMapObject rectangleMapObject = (RectangleMapObject) object;
               Rectangle rect = rectangleMapObject.getRectangle();

               createEdge(rect.x / s, rect.y / s, rect.width / s, rect.y / s);
               createEdge(rect.x / s, rect.y / s, rect.x / s, rect.height / s);
               createEdge(rect.width / s, rect.height / s, rect.x / s, rect.height / s);
               createEdge(rect.width / s, rect.height / s, rect.width / s, rect.y / s);
            }
         }

         debugRenderer = new Box2DDebugRenderer();
         // debugRenderer.setDrawAABBs(true);
         debugRenderer.setDrawBodies(true);
         // debugRenderer.setDrawContacts(true);
         debugRenderer.setDrawInactiveBodies(true);
         debugRenderer.setDrawJoints(true);
         debugRenderer.setDrawVelocities(true);

         lineRenderer = new LineRenderer(gameStage.getCamera(), Neon.assets.manager.get("textures/line.png", Texture.class));
      }

      Gdx.input.setInputProcessor(hudStage);
      if (this.music != null) {
         this.music.stop();
      }

      // this.music = Neon.assets.manager.get("music/acid rain.ogg",
      // Music.class);
      // this.sound = Neon.assets.manager.get("music/too damn long.ogg",
      // Sound.class);
      // MidiFile mid = Neon.assets.manager.get("music/too damn long.mid",
      // MidiFile.class);
      this.sound = Neon.assets.manager.get(Neon.game.soundFile, Sound.class);
      MidiFile mid = Neon.assets.manager.get(Neon.game.controlFile, MidiFile.class);
      controlTracks.load(mid);
      midiDisplay.setState(controlTracks);
      controlTracks.play();
      soundId = sound.play(0.5f);
      Neon.game.timeScale = 1f;
      // music.play();
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
      midiDisplay = new ControlTracksDisplay(null);
      hudStage.addActor(midiDisplay);
   }

   @Override
   public void render(float delta) {
      float scale = Neon.game.timeScale;
      float lastScale = scale;
      float target = Neon.game.targetTimeScale;
      // if (Neon.controls.comma.isDown) {
      // scale *= 0.9f;
      // Gdx.app.log("InGameScreen", "Sound scale set to " +
      // Neon.game.timeScale);
      // }
      // if (Neon.controls.period.isDown) {
      // Neon.game.timeScale /= 0.9f;
      // sound.setPitch(soundId, Neon.game.timeScale);
      // Gdx.app.log("InGameScreen", "Sound scale set to " +
      // Neon.game.timeScale);
      // }
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

      TiledMap map = Neon.assets.manager.get("levels/line.tmx", TiledMap.class);
      MapLayer layer = map.getLayers().get(0);
      float s = 32;
      for (MapObject object : layer.getObjects()) {
         if (object instanceof PolylineMapObject) {
            PolylineMapObject polylineMapObject = (PolylineMapObject) object;
            float[] vertices = polylineMapObject.getPolyline().getTransformedVertices();
            for (int i = 0; i < vertices.length - 2; i += 2) {
               lineRenderer.drawLine(vertices[i] / s, vertices[i + 1] / s, vertices[i + 2] / s, vertices[i + 3] / s, 0.04f, Color.ORANGE);
            }
         } else if (object instanceof RectangleMapObject) {
            RectangleMapObject rectangleMapObject = (RectangleMapObject) object;
            Rectangle rect = rectangleMapObject.getRectangle();

            lineRenderer.drawLine(rect.x / s, rect.y / s, rect.width / s, rect.y / s, 0.04f, Color.ORANGE);
            lineRenderer.drawLine(rect.x / s, rect.y / s, rect.x / s, rect.height / s, 0.04f, Color.ORANGE);
            lineRenderer.drawLine(rect.width / s, rect.height / s, rect.x / s, rect.height / s, 0.04f, Color.ORANGE);
            lineRenderer.drawLine(rect.width / s, rect.height / s, rect.width / s, rect.y / s, 0.04f, Color.ORANGE);
         }
      }

      gameStage.draw();
      hudStage.draw();

      controlTracks.update(scaledDelta);

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
         sound.setPitch(soundId, Neon.game.timeScale);
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

   private void createEdge(float x1, float y1, float x2, float y2) {
      BodyDef bodyDef = new BodyDef();
      bodyDef.type = BodyType.StaticBody;

      Body body = box2dWorld.createBody(bodyDef);

      EdgeShape edge = new EdgeShape();
      edge.set(x1, y1, x2, y2);
      Fixture fixture = body.createFixture(edge, 0f);
      Filter filter = fixture.getFilterData();
      filter.categoryBits = GameBits.SCENERY.category;
      filter.maskBits = GameBits.SCENERY.mask;
      fixture.setFilterData(filter);
   }
}
