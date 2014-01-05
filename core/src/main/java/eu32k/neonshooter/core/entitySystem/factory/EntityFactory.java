package eu32k.neonshooter.core.entitySystem.factory;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import eu32k.gdx.artemis.base.Entity;
import eu32k.gdx.artemis.base.managers.GroupManager;
import eu32k.gdx.artemis.extension.ExtendedWorld;
import eu32k.gdx.artemis.extension.component.ActorComponent;
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
import eu32k.neonshooter.core.entitySystem.component.ControllableComponent;
import eu32k.neonshooter.core.entitySystem.component.EnemyComponent;
import eu32k.neonshooter.core.entitySystem.component.PositionComponent;
import eu32k.neonshooter.core.entitySystem.component.SpawnerComponent;
import eu32k.neonshooter.core.entitySystem.component.WeaponComponent;
import eu32k.neonshooter.core.spawning.SpawnerInfo;
import eu32k.neonshooter.core.spawning.spawner.SimpleSpawner;
import eu32k.neonshooter.core.spawning.spawner.Spawner;
import eu32k.neonshooter.core.spawning.trigger.NoteOnTrigger;
import eu32k.neonshooter.core.spawning.trigger.Trigger;

public class EntityFactory extends Factory {

   public EntityFactory(ExtendedWorld world, Stage stage) {
      super(world, stage);
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

      PhysicsModel shipModel = new PhysicsModel(world.box2dWorld, e, "models.json", "Ship1", 2.0f, 0.0f, 0.0f, bits, false, 0.5f);
      shipModel.getBody().setLinearDamping(3.0f);
      PhysicsComponent pc = get(PhysicsComponent.class).init(shipModel.getBody());
      pc.activate(new Vector2(x, y), 0, new Vector2(0, 0));
      e.addComponent(pc);

      return e;
   }

   public Entity createProjectile(float x, float y, Bits bits, Vector2 velocity) {
      Entity e = createActorEntity(x, y, 0.1f, 0.3f, velocity.angle(), null);

      e.addComponent(get(TextureRegionComponent.class).init(Neon.assets.getTextureRegion("projectile")));

      PhysicsModel projectile = new PhysicsModel(world.box2dWorld, e, "models.json", "Projectile", 0.3f, 1.0f, 0.0f, bits, false, 0.2f);
      PhysicsComponent pc = get(PhysicsComponent.class).init(projectile.getBody());
      pc.activate(new Vector2(x, y), velocity.angle() * MathUtils.degRad, velocity);
      e.addComponent(pc);
      ActorComponent actor = Mappers.actorMapper.get(e);
      actor.actor.addAction(Actions.alpha(0f));
      actor.actor.act(1f);
      actor.actor.addAction(Actions.alpha(1f, 0.05f / Neon.game.timeScale));

      world.getManager(GroupManager.class).add(e, Groups.PLAYER_PROJECTILE);
      return e;
   }

   public Entity createEnemyShip(float x, float y) {
      Entity e = createActorEntity(x, y, 0.5f, 0.5f, 0, null);

      e.addComponent(get(TextureRegionComponent.class).init(Neon.assets.getTextureRegion("square")));

      PhysicsModel shipModel = new PhysicsModel(world.box2dWorld, e, "models.json", "Square1", 2.0f, 0.0f, 0.0f, GameBits.ENEMY, false, 0.5f);
      PhysicsComponent pc = get(PhysicsComponent.class).init(shipModel.getBody());
      pc.activate(new Vector2(x, y), 0, new Vector2(0, 0));
      e.addComponent(pc);

      e.addComponent(get(EnemyComponent.class));
      world.getManager(GroupManager.class).add(e, Groups.ENEMY);
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

      Trigger trigger = new NoteOnTrigger().init("C", Neon.music.getControlTracks().beatTrack);
      // Trigger trigger = new NotePlayingTrigger().init("F#",
      // Neon.music.getControlTracks().leadTrack);
      Spawner spawner = new SimpleSpawner();
      // Spawner spawner = new IntervalSpawner().init(1f);
      e.addComponent(get(SpawnerComponent.class).init(trigger, spawner));
      return e;
   }
}
