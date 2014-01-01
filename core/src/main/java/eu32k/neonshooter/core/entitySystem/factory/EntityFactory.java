package eu32k.neonshooter.core.entitySystem.factory;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.scenes.scene2d.Stage;

import eu32k.gdx.artemis.base.Entity;
import eu32k.gdx.artemis.extension.ExtendedWorld;
import eu32k.gdx.artemis.extension.component.PhysicsComponent;
import eu32k.gdx.artemis.extension.component.TextureRegionComponent;
import eu32k.gdx.artemis.extension.factory.Factory;
import eu32k.gdx.common.Bits;
import eu32k.gdx.common.PhysicsModel;
import eu32k.neonshooter.core.Neon;
import eu32k.neonshooter.core.entitySystem.common.GameBits;
import eu32k.neonshooter.core.entitySystem.component.ControllableComponent;
import eu32k.neonshooter.core.entitySystem.component.WeaponComponent;

public class EntityFactory extends Factory {

   public EntityFactory(ExtendedWorld world, Stage stage) {
      super(world, stage);
   }

   public Entity createTile(float x, float y, int type) {
      Entity e = createActorEntity(x, y, 1, 1, 0, null);

      float rot0 = 0;
      float rot90 = MathUtils.PI / 2.0f;
      float rot180 = 2.0f * rot90;
      float rot270 = 3.0f * rot90;

      String texture = "";
      String model = "";
      float rotation = 0;

      if (type == 0) {
         texture = "tile_v";
         model = "Tile1";
         rotation = rot0;
      } else if (type == 1) {
         texture = "tile_v";
         model = "Tile1";
         rotation = rot90;
      } else if (type == 2) {
         texture = "tile_v";
         model = "Tile1";
         rotation = rot180;
      } else if (type == 3) {
         texture = "tile_v";
         model = "Tile1";
         rotation = rot270;
      } else if (type == 4) {
         texture = "tile_c";
         model = "Tile2";
         rotation = rot0;
      } else if (type == 5) {
         texture = "tile_c";
         model = "Tile2";
         rotation = rot90;
      } else if (type == 6) {
         texture = "tile_c";
         model = "Tile2";
         rotation = rot180;
      } else if (type == 7) {
         texture = "tile_c";
         model = "Tile2";
         rotation = rot270;
      } else if (type == 8) {
         texture = "tile_c";
         model = "Tile3";
         rotation = rot0;
      } else if (type == 9) {
         texture = "tile_c";
         model = "Tile3";
         rotation = rot90;
      } else if (type == 10) {
         texture = "tile_c";
         model = "Tile3";
         rotation = rot180;
      } else if (type == 11) {
         texture = "tile_c";
         model = "Tile3";
         rotation = rot270;
      }

      e.addComponent(get(TextureRegionComponent.class).init(Neon.assets.getTextureRegion(texture)));

      PhysicsModel square = new PhysicsModel(world.box2dWorld, e, "models.json", model, 0.0f, 0.0f, 0.5f, GameBits.SCENERY, false, 1.0f);
      square.getBody().setType(BodyType.StaticBody);
      PhysicsComponent pc = get(PhysicsComponent.class).init(square.getBody());
      pc.activate(new Vector2(x, y), rotation, new Vector2(0, 0));
      e.addComponent(pc);

      return e;
   }

   public Entity createChlotz(float x, float y) {
      Entity e = createActorEntity(x, y, 1, 1, 0, null);

      e.addComponent(get(TextureRegionComponent.class).init(Neon.assets.getTextureRegion("square")));

      PhysicsModel square = new PhysicsModel(world.box2dWorld, e, "models.json", "Square1", 1.0f, 1.0f, 0.5f, GameBits.SCENERY, false, 1.0f);
      PhysicsComponent pc = get(PhysicsComponent.class).init(square.getBody());
      pc.activate(new Vector2(x, y), 0, new Vector2(0, 0));
      e.addComponent(pc);

      return e;
   }

   public Entity createShip(float x, float y, Bits bits) {
      Entity e = createActorEntity(x, y, 1, 1, 0, null);

      e.addComponent(get(TextureRegionComponent.class).init(Neon.assets.getTextureRegion("ship")));

      PhysicsModel shipModel = new PhysicsModel(world.box2dWorld, e, "models.json", "Ship1", 2.0f, 1.0f, 0.0f, bits, false, 1.0f);
      shipModel.getBody().setLinearDamping(3.0f);
      PhysicsComponent pc = get(PhysicsComponent.class).init(shipModel.getBody());
      pc.activate(new Vector2(x, y), 0, new Vector2(0, 0));
      e.addComponent(pc);

      e.addComponent(get(WeaponComponent.class).init(100));

      return e;
   }

   public Entity createProjectile(float x, float y, Bits bits, float direction) {
      Entity e = createActorEntity(x, y, 1, 1, 0, null);

      e.addComponent(get(TextureRegionComponent.class).init(Neon.assets.getTextureRegion("ship")));

      PhysicsModel shipModel = new PhysicsModel(world.box2dWorld, e, "models.json", "Ship1", 2.0f, 1.0f, 0.0f, bits, false, 1.0f);
      shipModel.getBody().setLinearDamping(3.0f);
      PhysicsComponent pc = get(PhysicsComponent.class).init(shipModel.getBody());
      pc.activate(new Vector2(x, y), 0, new Vector2(0, 0));
      e.addComponent(pc);

      e.addComponent(get(WeaponComponent.class).init(100));

      return e;
   }

   public Entity createPlayerShip(float x, float y) {
      Entity e = createShip(x, y, GameBits.PLAYER);
      e.addComponent(get(ControllableComponent.class));
      return e;
   }

}
