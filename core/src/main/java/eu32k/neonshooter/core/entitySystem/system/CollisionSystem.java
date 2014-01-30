package eu32k.neonshooter.core.entitySystem.system;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;

import eu32k.gdx.artemis.base.Entity;
import eu32k.gdx.artemis.base.managers.GroupManager;
import eu32k.gdx.artemis.base.systems.VoidEntitySystem;
import eu32k.neonshooter.core.Neon;
import eu32k.neonshooter.core.entitySystem.common.Groups;
import eu32k.neonshooter.core.entitySystem.component.DeactivateComponent;
import eu32k.neonshooter.core.entitySystem.factory.EntityFactory;
import eu32k.neonshooter.core.ui.GameOverScreen;

public class CollisionSystem extends VoidEntitySystem implements ContactListener {

   private World box2dWorld;
   private GroupManager group;

   public CollisionSystem(World box2dWorld, EntityFactory factory) {
      this.box2dWorld = box2dWorld;
   }

   @Override
   protected void initialize() {
      box2dWorld.setContactListener(this);
      group = world.getManager(GroupManager.class);
   }

   @Override
   protected void processSystem() {
      // NOP
   }

   private void handleCollision(Entity entityA, Entity entityB) {
      if (is(entityA, Groups.PLAYER_PROJECTILE)) {
         entityA.addComponent(new DeactivateComponent());
         entityA.changedInWorld();
      }
      if (is(entityA, Groups.PLAYER_PROJECTILE) && is(entityB, Groups.ENEMY)) {
         entityB.addComponent(new DeactivateComponent());
         entityB.changedInWorld();
      } else if (is(entityA, Groups.ATTRACTOR) && is(entityB, Groups.ENEMY)) {
         entityB.addComponent(new DeactivateComponent());
         entityB.changedInWorld();
      } else if (is(entityA, Groups.PLAYER) && (is(entityB, Groups.ENEMY) || is(entityB, Groups.ATTRACTOR))) {
         Neon.ui.pushScreen(GameOverScreen.class);
      }
   }

   private boolean is(Entity e, String groupName) {
      return group.getGroups(e) != null && group.isInGroup(e, groupName);
   }

   @Override
   public void beginContact(Contact contact) {
      if (!contact.isTouching()) {
         return;
      }

      // Body bodyA = contact.getFixtureA().getBody();
      // Body bodyB = contact.getFixtureB().getBody();
      Entity entityA = (Entity) contact.getFixtureA().getUserData();
      Entity entityB = (Entity) contact.getFixtureB().getUserData();

      handleCollision(entityA, entityB);
      handleCollision(entityB, entityA);
   }

   @Override
   public void endContact(Contact contact) {
      // NOP
   }

   @Override
   public void preSolve(Contact contact, Manifold oldManifold) {
      // NOP
   }

   @Override
   public void postSolve(Contact contact, ContactImpulse impulse) {
      // NOP
   }
}
