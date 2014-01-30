package eu32k.neonshooter.core.ui;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

import eu32k.neonshooter.core.Neon;
import eu32k.neonshooter.core.fx.SoundSet;
import eu32k.neonshooter.core.model.LevelInfo;
import eu32k.neonshooter.core.model.loading.NeonScreen;

public class LevelSelectScreen extends NeonScreen {

   private TextButton backButton;
   private TextButton startButton;
   private List soundBox;
   private List levelBox;
   private java.util.List<SoundSet> sounds;
   private java.util.List<LevelInfo> levels;

   @Override
   protected void init() {
      Skin skin = Neon.assets.skin;
      Table mainTable = new Table(skin);
      mainTable.setFillParent(true);
      mainTable.pad(20f);

      levels = Neon.levels.arcade();
      Object[] levelsData = levels.toArray();
      levelBox = new List(levelsData, skin);

      ScrollPane levelScrollPane = new ScrollPane(levelBox);

      sounds = Neon.music.arcade();
      Object[] soundSets = sounds.toArray();
      soundBox = new List(soundSets, skin);
      ScrollPane soundScrollPane = new ScrollPane(soundBox);

      Label levelCaption = new Label("Levels", skin, "caption");
      Label soundCaption = new Label("Sound Sets", skin, "caption");

      mainTable.add(levelCaption).align(Align.top | Align.left);
      mainTable.add(soundCaption).align(Align.top | Align.left);

      mainTable.row();

      mainTable.add(levelScrollPane).expand().align(Align.top | Align.left);
      mainTable.add(soundScrollPane).expand().align(Align.top | Align.left);

      backButton = new TextButton("Back", skin, "startScreen");
      backButton.addListener(new InputListener() {
         @Override
         public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            Neon.ui.popScreen();
            return false;
         }
      });

      startButton = new TextButton("Start", skin, "startScreen");
      startButton.addListener(new InputListener() {
         @Override
         public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            start();
            return false;
         }
      });

      mainTable.row();

      mainTable.add(backButton);
      mainTable.add(startButton);

      stage.addActor(mainTable);
   }

   private void start() {
      LevelInfo info = levels.get(levelBox.getSelectedIndex());
      SoundSet sound = sounds.get(soundBox.getSelectedIndex());
      Neon.levels.prepareLevel(info);
      Neon.music.prepareSet(sound);
      Neon.ui.pushScreen(InGameScreen.class);
   }

   @Override
   public boolean keyDown(int key) {
      if (key == Keys.ESCAPE) {
         Neon.ui.popScreen();
      }
      return false;
   }

   @Override
   public void dispose() {
      // NOP
   }

   @Override
   public void initAssets() {
      // NOP
   }

   @Override
   public void reset() {
      // NOP
   }
}
