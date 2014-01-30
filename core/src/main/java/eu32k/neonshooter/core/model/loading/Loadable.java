package eu32k.neonshooter.core.model.loading;


public interface Loadable {
   public void initAssets();

   public void initialize();

   public void reset();

   public void dispose();
}
