package eu32k.neonshooter.core.config;

public class Config {

   public int resolutionX;
   public int resolutionY;
   public float gameSpeed = 1.0f;
   public float delta = 0.0f;

   public void create() {
   }

   public void setResolution(int resolutionX, int resolutionY) {
      this.resolutionX = resolutionX;
      this.resolutionY = resolutionY;
   }
}
