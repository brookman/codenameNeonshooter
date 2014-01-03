package eu32k.neonshooter.core.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.leff.midi.MidiFile;

import eu32k.neonshooter.core.fx.midi.MidiLoader;

public class Assets {
   private static final String TEXTURE_ATLAS = "atlas/atlas.txt";
   public AssetManager manager;
   private TextureAtlas atlas;

   public Skin skin;
   private TmxMapLoader tmxLoader;
   private MidiLoader midiLoader;

   public final SoundSet MUSIC_ACID_RAIN = new SoundSet("atombrot", "acid rain", "music/acid rain.ogg", "music/acid rain.mid");
   public final SoundSet MUSIC_TOO_DAMN_LONG = new SoundSet("0x1d vs terasun vs satelite jack vs atombrot", "this is too damn long", "music/too damn long.ogg", "music/too damn long.mid");

   public void create() {
      createEssentials();
      manager = new AssetManager();
      tmxLoader = new TmxMapLoader(new InternalFileHandleResolver());
      midiLoader = new MidiLoader(new InternalFileHandleResolver());
      queueGameAssets();
   }

   private void createEssentials() {
      skin = new Skin(Gdx.files.internal("skins/uiskin.json"));
   }

   private void queueGameAssets() {
      // Queue sound fx, images for loading etc...

      manager.setLoader(TiledMap.class, tmxLoader);
      manager.setLoader(MidiFile.class, midiLoader);
      manager.load("textures/debug.png", Texture.class);
      manager.load("textures/square.png", Texture.class);
      manager.load("textures/square-filled.png", Texture.class);
      manager.load(TEXTURE_ATLAS, TextureAtlas.class);
      manager.load("models/text.g3dj", Model.class);
   }

   public TextureRegion getTextureRegion(String path) {
      if (atlas == null) {
         if (manager.isLoaded(TEXTURE_ATLAS)) {
            atlas = manager.get(TEXTURE_ATLAS, TextureAtlas.class);
         } else {
            return null;
         }
      }
      return atlas.findRegion(path);
   }

   public boolean complete() {
      return manager.getQueuedAssets() == 0;
   }

   public boolean updateManager() {
      return manager.update();
   }

   public float getProgress() {
      return manager.getProgress();
   }

   public class SoundSet {
      final public String artist;
      final public String title;
      final public String soundFile;
      final public String controlFile;

      public SoundSet(String artist, String title, String sound, String control) {
         this.artist = artist;
         this.title = title;
         this.soundFile = sound;
         this.controlFile = control;
      }
   }
}
