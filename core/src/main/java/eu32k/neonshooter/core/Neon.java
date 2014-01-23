package eu32k.neonshooter.core;

import java.util.Random;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Scaling;

import eu32k.neonshooter.core.config.Config;
import eu32k.neonshooter.core.config.Settings;
import eu32k.neonshooter.core.fx.FxManager;
import eu32k.neonshooter.core.fx.MusicManager;
import eu32k.neonshooter.core.input.Controls;
import eu32k.neonshooter.core.model.Assets;
import eu32k.neonshooter.core.model.GameState;
import eu32k.neonshooter.core.model.LevelManager;
import eu32k.neonshooter.core.ui.IntroScreen;
import eu32k.neonshooter.core.ui.Ui;

public class Neon extends Game {

   public static Neon instance;
   public static Controls controls;
   public static Ui ui;
   public static Config config;
   public static Settings settings;
   public static Assets assets;
   public static GameState game;
   public static Rectangle viewport;
   public static FxManager fx;
   public static MusicManager music;
   public static Random random;
   public static LevelManager levels;

   public static final float VIRTUAL_WIDTH = 10.24f;
   public static final float VIRTUAL_HEIGHT = 5.76f;

   public Neon() {
      Neon.config = new Config();
   }

   @Override
   public void create() {
      viewport = new Rectangle();
      resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

      Neon.instance = this;
      Neon.random = new Random();
      Neon.settings = new Settings();
      Neon.assets = new Assets();
      Neon.fx = new FxManager();
      Neon.ui = new Ui();
      Neon.controls = new Controls();
      Neon.game = new GameState();
      Neon.music = new MusicManager();
      Neon.levels = new LevelManager();

      Neon.config.create();
      Neon.settings.create();
      Neon.assets.create();
      Neon.fx.create();
      Neon.music.create();
      Neon.ui.create();
      Neon.controls.create();
      Neon.levels.create();
      Neon.game.create();

      Neon.ui.showScreen(IntroScreen.class);
   }

   @Override
   public void render() {
      Neon.controls.update();

      Gdx.graphics.getGL20().glClearColor(0, 0, 0, 1);
      Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

      Gdx.gl.glEnable(GL20.GL_BLEND);
      Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
      super.render();
   }

   @Override
   public void resize(int width, int height) {
      Vector2 size = Scaling.fit.apply(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, width, height);
      viewport.setPosition((width - size.x) / 2, (height - size.y) / 2);
      viewport.setSize(size.x, size.y);
      Gdx.gl.glViewport((int) viewport.x, (int) viewport.y, (int) viewport.width, (int) viewport.height);

      Neon.config.setResolution(width, height);
      super.resize(width, height);
   }
}
