package eu32k.neonshooter.core.spawning;

import com.badlogic.gdx.maps.MapProperties;

public class PropertyReader {
   public static String get(MapProperties p, String prefix, String name) {
      return get(p, prefix, name, "");
   }

   public static String get(MapProperties p, String prefix, String name, String defaultValue) {
      String fullName = prefix + "." + name;
      return p.get(fullName, defaultValue, String.class);
   }
}
