package eu32k.neonshooter.core.entitySystem.common;

import eu32k.gdx.artemis.base.ComponentMapper;
import eu32k.gdx.artemis.extension.ExtendedWorld;
import eu32k.gdx.artemis.extension.component.ActorComponent;
import eu32k.gdx.artemis.extension.component.PhysicsComponent;
import eu32k.neonshooter.core.entitySystem.component.WeaponComponent;

public class Mappers {

   public static ComponentMapper<ActorComponent> actorMapper;
   public static ComponentMapper<PhysicsComponent> physicsMapper;
   public static ComponentMapper<WeaponComponent> weaponMapper;

   public static void init(ExtendedWorld world) {
      actorMapper = world.getMapper(ActorComponent.class);
      physicsMapper = world.getMapper(PhysicsComponent.class);
      weaponMapper = world.getMapper(WeaponComponent.class);
   }
}
