package eu32k.neonshooter.core.logic;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Actor;

import eu32k.neonshooter.core.model.Enemy;
import eu32k.neonshooter.core.model.GameEntity;
import eu32k.neonshooter.core.model.Player;
import eu32k.neonshooter.core.model.Projectile;

public class EntityManager {
   public Player player;
   public List<GameEntity> entities;
   public List<Enemy> enemies;
   public List<Projectile> playerProjectiles;
   public List<Projectile> enemyProjectiles;

   public EntityManager() {
      entities = new ArrayList<GameEntity>();
      enemies = new ArrayList<Enemy>();
      playerProjectiles = new ArrayList<Projectile>();
      enemyProjectiles = new ArrayList<Projectile>();
   }

   public void clear() {
      player = null;
      entities.clear();
      enemies.clear();
      playerProjectiles.clear();
      enemyProjectiles.clear();
   }

   public void add(Actor actor) {
      if (actor instanceof GameEntity) {
         add((GameEntity) actor);
      }
   }

   public void add(GameEntity entity) {
      entities.add(entity);
      if (entity instanceof Enemy) {
         enemies.add((Enemy) entity);
      } else if (entity instanceof Projectile) {
         Projectile projectile = (Projectile) entity;
         if (projectile.shotByPlayer) {
            playerProjectiles.add(projectile);
         } else {
            enemyProjectiles.add(projectile);
         }
      } else if (entity instanceof Player) {
         // HACK: This might lead into problems, if multiple player objects
         // are present. if so, refactor
         // to use player list
         player = (Player) entity;
      }
   }

   public void remove(Actor actor) {
      if (actor instanceof GameEntity) {
         remove((GameEntity) actor);
      }
   }

   public void removeEntity(GameEntity entity) {
      entities.remove(entity);
      if (entity instanceof Enemy) {
         enemies.remove(entity);
      } else if (entity instanceof Projectile) {
         Projectile projectile = (Projectile) entity;
         if (projectile.shotByPlayer) {
            playerProjectiles.remove(projectile);
         } else {
            enemyProjectiles.remove(projectile);
         }
      } else if (entity instanceof Player && entity.equals(player)) {
         player = null;
      }
   }
}
