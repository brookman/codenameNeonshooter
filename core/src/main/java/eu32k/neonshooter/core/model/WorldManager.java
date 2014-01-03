package eu32k.neonshooter.core.model;

public interface WorldManager {
   void clearWorld();

   void clearEntities();

   void addShip(float x, float y, boolean isPlayer);

   void shoot(GameEntity source);
}
