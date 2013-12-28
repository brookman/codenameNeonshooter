package eu32k.neonshooter.html;

import eu32k.neonshooter.core.Neon;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

public class NeonshooterHtml extends GwtApplication {
	@Override
	public ApplicationListener getApplicationListener () {
		return new Neon();
	}
	
	@Override
	public GwtApplicationConfiguration getConfig () {
		return new GwtApplicationConfiguration(480, 320);
	}
}
