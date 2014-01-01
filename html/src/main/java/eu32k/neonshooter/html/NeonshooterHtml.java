package eu32k.neonshooter.html;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

import eu32k.neonshooter.core.Neon;

public class NeonshooterHtml extends GwtApplication {
   @Override
   public ApplicationListener getApplicationListener() {
      return new Neon();
   }

   @Override
   public GwtApplicationConfiguration getConfig() {
      GwtApplicationConfiguration config = new GwtApplicationConfiguration(1024, 576);
      config.antialiasing = true;
      config.fps = 60;
      return config;
   }
}
