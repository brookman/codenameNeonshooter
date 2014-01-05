package eu32k.neonshooter.core.entitySystem.system;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import eu32k.gdx.artemis.base.Aspect;
import eu32k.gdx.artemis.base.Entity;
import eu32k.gdx.artemis.base.systems.EntityProcessingSystem;
import eu32k.gdx.artemis.extension.EntityActor;
import eu32k.gdx.artemis.extension.component.ActorComponent;
import eu32k.neonshooter.core.Neon;
import eu32k.neonshooter.core.entitySystem.common.GameBits;
import eu32k.neonshooter.core.entitySystem.common.Mappers;
import eu32k.neonshooter.core.entitySystem.component.WeaponComponent;
import eu32k.neonshooter.core.entitySystem.factory.EntityFactory;

public class WeaponSystem extends EntityProcessingSystem {

   private Vector2 velocity;

   private EntityFactory factory;

   private Stage referenceStage;
   private Vector2 mouse = new Vector2();

   @SuppressWarnings("unchecked")
   public WeaponSystem(EntityFactory factory, Stage referenceStage) {
      super(Aspect.getAspectForAll(WeaponComponent.class, ActorComponent.class));
      this.factory = factory;
      this.referenceStage = referenceStage;
      velocity = new Vector2();
   }

   @Override
   protected void process(Entity e) {
      WeaponComponent weaponComponent = Mappers.weaponMapper.get(e);
      EntityActor actor = Mappers.actorMapper.get(e).actor;

      if (Neon.controls.padRight.len() > 0) {
         weaponComponent.shootRequested = true;
         weaponComponent.targetX = actor.getX() + Neon.controls.padRight.x;
         weaponComponent.targetY = actor.getY() + Neon.controls.padRight.y;
      } else {
         // weaponComponent.shootRequested = false;

         // Hack
         weaponComponent.shootRequested = true;
         mouse.set(Neon.controls.mouseX, Neon.controls.mouseY);
         referenceStage.screenToStageCoordinates(mouse);
         weaponComponent.targetX = mouse.x;
         weaponComponent.targetY = mouse.y;
      }

      if (!weaponComponent.shouldShoot()) {
         return;
      }

      Vector2 stagePosition = actor.getPositionOnStage();

      velocity.set(weaponComponent.targetX - stagePosition.x, weaponComponent.targetY - stagePosition.y);
      velocity.rotate((MathUtils.random() * weaponComponent.precision - weaponComponent.precision / 2.0f) * 360);

      velocity.nor().scl(7.0f);

      Vector2 pos = new Vector2();
      float rot = (weaponComponent.tick % 2 == 0) ? 90 : -90;

      pos.x = actor.getX() + MathUtils.cos((velocity.angle() + rot) * MathUtils.degRad) * 0.1f;
      pos.y = actor.getY() + MathUtils.sin((velocity.angle() + rot) * MathUtils.degRad) * 0.1f;

      factory.createProjectile(pos.x, pos.y, GameBits.PLAYER_BULLET, velocity).addToWorld();

      // shoot
      weaponComponent.shoot();
   }
}
