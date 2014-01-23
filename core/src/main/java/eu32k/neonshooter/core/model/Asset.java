package eu32k.neonshooter.core.model;

import com.badlogic.gdx.assets.AssetDescriptor;

import eu32k.neonshooter.core.Neon;

public class Asset<T> extends AssetDescriptor<T> {

   private T cache = null;

   public Asset(String fileName, Class<T> assetType) {
      super(fileName, assetType);
      Neon.assets.manager.load(this);
   }

   public T get() {
      if (cache == null) {
         if (!Neon.assets.manager.isLoaded(fileName)) {
            Neon.assets.manager.finishLoading();
         }
         cache = Neon.assets.manager.get(this);
      }
      return cache;
   }
}
