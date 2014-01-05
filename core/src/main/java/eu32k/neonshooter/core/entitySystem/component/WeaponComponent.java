package eu32k.neonshooter.core.entitySystem.component;

import eu32k.gdx.artemis.base.Component;
import eu32k.gdx.common.Time;
import eu32k.neonshooter.core.Neon;

public class WeaponComponent extends Component {

   private int shootDelay; // In ms

   public boolean shootRequested;
   public long lastShoot;
   public float targetX;
   public float targetY;
   public float precision;
   public int tick;

   public WeaponComponent init(int shootDelay) {
      this.shootDelay = shootDelay;
      shootRequested = false;
      lastShoot = 0;
      targetX = 0;
      targetY = 0;
      tick = 0;
      precision = 0.01f;
      return this;
   }

   public boolean shouldShoot() {
      return shootRequested && Time.getTime() - lastShoot >= shootDelay / Neon.game.timeScale;
   }

   public void shoot() {
      lastShoot = Time.getTime();
      shootRequested = false;
      tick++;
   }
}
