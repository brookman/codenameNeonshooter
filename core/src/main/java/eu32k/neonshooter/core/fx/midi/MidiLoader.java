package eu32k.neonshooter.core.fx.midi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.leff.midi.MidiFile;

public class MidiLoader extends SynchronousAssetLoader<MidiFile, MidiLoader.MidiTrackParameter> {

   public MidiLoader(FileHandleResolver resolver) {
      super(resolver);
   }

   @Override
   public MidiFile load(AssetManager assetManager, String fileName, FileHandle file, MidiTrackParameter parameter) {
      try {
         return new MidiFile(file.read());
      } catch (Exception ex) {
         Gdx.app.error("MidiLoader", "Could not load midi file.");
         Gdx.app.error("MidiLoader", ex.getMessage());
         return null;
      }
   }

   @Override
   public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, MidiTrackParameter parameter) {
      return null;
   }

   static public class MidiTrackParameter extends AssetLoaderParameters<MidiFile> {
   }

}
