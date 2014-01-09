package eu32k.neonshooter.core.entitySystem.factory;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Pool;

import eu32k.gdx.artemis.base.Entity;
import eu32k.gdx.artemis.base.managers.GroupManager;
import eu32k.gdx.artemis.extension.ExtendedWorld;
import eu32k.gdx.artemis.extension.component.CameraTargetComponent;
import eu32k.gdx.artemis.extension.component.PhysicsComponent;
import eu32k.gdx.artemis.extension.component.TextureRegionComponent;
import eu32k.gdx.artemis.extension.factory.Factory;
import eu32k.gdx.common.Bits;
import eu32k.gdx.common.PhysicsModel;
import eu32k.neonshooter.core.Neon;
import eu32k.neonshooter.core.entitySystem.common.GameBits;
import eu32k.neonshooter.core.entitySystem.common.Groups;
import eu32k.neonshooter.core.entitySystem.common.Mappers;
import eu32k.neonshooter.core.entitySystem.common.TempVector;
import eu32k.neonshooter.core.entitySystem.component.ControllableComponent;
import eu32k.neonshooter.core.entitySystem.component.EnemyComponent;
import eu32k.neonshooter.core.entitySystem.component.PoolableComponent;
import eu32k.neonshooter.core.entitySystem.component.PositionComponent;
import eu32k.neonshooter.core.entitySystem.component.SpawnerComponent;
import eu32k.neonshooter.core.entitySystem.component.WeaponComponent;
import eu32k.neonshooter.core.spawning.SpawnerInfo;
import eu32k.neonshooter.core.spawning.spawner.IntervalSpawner;
import eu32k.neonshooter.core.spawning.spawner.SimpleSpawner;
import eu32k.neonshooter.core.spawning.spawner.Spawner;
import eu32k.neonshooter.core.spawning.trigger.NoteOffTrigger;
import eu32k.neonshooter.core.spawning.trigger.NoteOnTrigger;
import eu32k.neonshooter.core.spawning.trigger.NotePlayingTrigger;
import eu32k.neonshooter.core.spawning.trigger.Trigger;

public class EntityFactory extends Factory {

   private Map<String, Class<?>> spawnerMapping;

   public EntityFactory(ExtendedWorld world, Stage stage) {
      super(world, stage);
      spawnerMapping = new HashMap<String, Class<?>>();
      spawnerMapping.put("NoteOffTrigger", NoteOffTrigger.class);
      spawnerMapping.put("NoteOnTrigger", NoteOnTrigger.class);
      spawnerMapping.put("NotePlayingTrigger", NotePlayingTrigger.class);
      spawnerMapping.put("SimpleSpawner", SimpleSpawner.class);
      spawnerMapping.put("IntervalSpawner", IntervalSpawner.class);
   }

   // public Entity createBox(float x, float y) {
   // Entity e = createActorEntity(x, y, 1, 1, 0, null);
   //
   // e.addComponent(get(TextureRegionComponent.class).init(Neon.assets.getTextureRegion("square")));
   //
   // PhysicsModel square = new PhysicsModel(world.box2dWorld, e, "models.json",
   // "Square1", 1.0f, 1.0f, 0.5f, GameBits.SCENERY, false, 1.0f);
   // PhysicsComponent pc = get(PhysicsComponent.class).init(square.getBody());
   // pc.activate(new Vector2(x, y), 0, new Vector2(0, 0));
   // e.addComponent(pc);
   //
   // return e;
   // }

   public Entity createShip(float x, float y, Bits bits) {
      Entity e = createActorEntity(x, y, 0.5f, 0.5f, 0, null);

      e.addComponent(get(TextureRegionComponent.class).init(Neon.assets.getTextureRegion("ship")));

      CircleShape shape = new CircleShape();
      shape.setRadius(0.12f);

      PhysicsModel shipModel = new PhysicsModel(world.box2dWorld, e, shape, 2.0f, 0.0f, 0.0f, bits, false, 0.5f);
      shipModel.getBody().setLinearDamping(3.0f);
      PhysicsComponent pc = get(PhysicsComponent.class).init(shipModel.getBody());
      pc.activate(new Vector2(x, y), 0, new Vector2(0, 0));
      e.addComponent(pc);

      return e;
   }

   public Pool<Entity> bulletPool = new Pool<Entity>() {

      @Override
      protected Entity newObject() {
         Entity e = createActorEntity(0, 0, 0.3f, 0.5f, 0, null);

         e.addComponent(get(TextureRegionComponent.class).init(Neon.assets.getTextureRegion("projectile")));

         CircleShape shape = new CircleShape();
         shape.setRadius(0.04f);

         PhysicsModel projectile = new PhysicsModel(world.box2dWorld, e, shape, 1.0f, 0.0f, 0.0f, GameBits.PLAYER_BULLET, true, 0.2f);
         projectile.getBody().setLinearDamping(0.0f);
         PhysicsComponent pc = get(PhysicsComponent.class).init(projectile.getBody());
         pc.activate(new Vector2(0, 0), 0, new Vector2(0, 0));
         e.addComponent(pc);

         e.addComponent(new PoolableComponent<Entity>().init(this));

         world.getManager(GroupManager.class).add(e, Groups.PLAYER_PROJECTILE);
         e.addToWorld();
         return e;
      }

   };
   private TempVector temp = new TempVector();
   private TempVector temp2 = new TempVector();

   public Entity createProjectile(float x, float y, Bits bits, Vector2 velocity) {
      Entity e = bulletPool.obtain();
      e.enable();

      Actor actor = Mappers.actorMapper.get(e).actor;
      actor.setVisible(true);
      actor.setPosition(x, y);
      actor.setRotation(velocity.angle());
      Mappers.physicsMapper.get(e).activate(temp.s(x, y), velocity.angle() * MathUtils.degRad, velocity);

      actor.getActions().clear();
      actor.addAction(Actions.alpha(0f));
      actor.act(1f);
      actor.addAction(Actions.alpha(1f, 0.05f / Neon.game.timeScale));

      e.changedInWorld();
      return e;
   }

   public Pool<Entity> enemyPool = new Pool<Entity>() {

      @Override
      protected Entity newObject() {
         Entity e = createActorEntity(0, 0, 0.5f, 0.5f, 0, null);

         e.addComponent(get(TextureRegionComponent.class).init(Neon.assets.getTextureRegion("square")));

         CircleShape shape = new CircleShape();
         shape.setRadius(0.15f);

         PhysicsModel shipModel = new PhysicsModel(world.box2dWorld, e, shape, 2.0f, 0.0f, 0.0f, GameBits.ENEMY, false, 0.5f);

         PhysicsComponent pc = get(PhysicsComponent.class).init(shipModel.getBody());
         pc.activate(new Vector2(0, 0), 0, new Vector2(0, 0));
         e.addComponent(pc);

         e.addComponent(get(EnemyComponent.class));

         e.addComponent(new PoolableComponent<Entity>().init(this));

         world.getManager(GroupManager.class).add(e, Groups.ENEMY);
         e.addToWorld();
         return e;
      }

   };

   public Entity createEnemyShip(float x, float y) {
      Entity e = enemyPool.obtain();
      e.enable();

      Actor actor = Mappers.actorMapper.get(e).actor;
      actor.setVisible(true);
      actor.setPosition(x, y);
      Mappers.physicsMapper.get(e).activate(temp.s(x, y), 0, temp2.s(0, 0));

      e.changedInWorld();
      return e;
   }

   public Entity createPlayerShip(float x, float y) {
      Entity e = createShip(x, y, GameBits.PLAYER);
      e.addComponent(get(ControllableComponent.class));
      e.addComponent(get(WeaponComponent.class).init(100));
      e.addComponent(get(CameraTargetComponent.class).init(false));
      world.getManager(GroupManager.class).add(e, Groups.PLAYER);
      return e;
   }

   public Entity createSpawner(SpawnerInfo info) {
      Entity e = world.createEntity();
      e.addComponent(get(PositionComponent.class).init(info.position.x, info.position.y));
      SpawnerComponent spawnerComponent = null;
      try {
         spawnerComponent = getSpawnerComponent(info);
      } catch (Exception ex) {
         NoteOnTrigger trigger = new NoteOnTrigger().init("C", Neon.music.getControlTracks().beatTrack);
         SimpleSpawner spawner = new SimpleSpawner();
         spawnerComponent = get(SpawnerComponent.class).init(trigger, spawner);
      }
      e.addComponent(spawnerComponent);
      return e;
   }

   private SpawnerComponent getSpawnerComponent(SpawnerInfo info) {
      MapProperties properties = info.properties;
      String triggerConfig = properties.get("Trigger", String.class);
      String triggerConfigSplit[] = triggerConfig.split(";");
      Class<?> triggerClass = spawnerMapping.get(triggerConfigSplit[0] + "Trigger");
      String spawnerConfig = properties.get("Spawner", String.class);
      String spawnerConfigSplit[] = spawnerConfig.split(";");
      Class<?> spawnerClass = spawnerMapping.get(spawnerConfigSplit[0] + "Spawner");
      Trigger trigger = null;
      Spawner spawner = null;
      trigger = (Trigger) get(triggerClass);
      trigger.init(properties, triggerConfigSplit[1]);

      spawner = (Spawner) get(spawnerClass);
      spawner.init(properties, spawnerConfigSplit[1]);
      SpawnerComponent spawnerComponent = get(SpawnerComponent.class).init(trigger, spawner);
      return spawnerComponent;
   }
}
