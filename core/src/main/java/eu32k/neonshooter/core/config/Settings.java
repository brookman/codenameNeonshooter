package eu32k.neonshooter.core.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Settings {

   public float musicVolume = 0.5f;
   public float fxVolume = 0.5f;

   private Preferences preferences;

   public void create() {
      preferences = Gdx.app.getPreferences("eu32k.neonshooter.preferences");
      load();
   }

   public void load() {
      musicVolume = preferences.getFloat("musicVolume", 0.5f);
      fxVolume = preferences.getFloat("fxVolume", 0.5f);
   }

   public void reset() {
      musicVolume = 0.5f;
      fxVolume = 0.5f;
   }

   public void save() {
      preferences.putFloat("musicVolume", musicVolume);
      preferences.putFloat("fxVolume", fxVolume);
      preferences.flush();
   }
}