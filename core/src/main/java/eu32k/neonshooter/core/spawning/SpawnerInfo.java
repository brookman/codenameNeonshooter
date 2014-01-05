package eu32k.neonshooter.core.spawning;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Vector2;

public class SpawnerInfo {
   public Vector2 position;
   public MapProperties properties;

   public SpawnerInfo(Vector2 position, MapProperties properties) {
      this.position = position;
      this.properties = properties;
   }

}
