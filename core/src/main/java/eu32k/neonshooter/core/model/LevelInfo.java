package eu32k.neonshooter.core.model;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.JsonValue;

public class LevelInfo implements Serializable {
   public String id;
   public String name;
   public String description;
   public String file;

   public LevelInfo() {
      this("", "", "", "");
   }

   public LevelInfo(String id, String file, String name, String description) {
      this.id = id;
      this.file = file;
      this.name = name;
      this.description = description;
   }

   @Override
   public void write(Json json) {

   }

   @Override
   public void read(Json json, JsonValue jsonData) {
      this.id = jsonData.getString("id");
      this.name = jsonData.getString("name");
      this.description = jsonData.getString("description");
      this.file = jsonData.getString("file");
   }

   @Override
   public String toString() {
      return id + " - " + name;
   }
}
