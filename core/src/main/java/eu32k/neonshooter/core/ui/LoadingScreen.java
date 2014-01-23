package eu32k.neonshooter.core.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.esotericsoftware.tablelayout.BaseTableLayout;

import eu32k.neonshooter.core.Neon;
import eu32k.neonshooter.core.model.LoadableScreen;

public class LoadingScreen extends LoadableScreen {

   private LoadableScreen targetScreen;

   private Label percentageLabel;

   public LoadingScreen() {
      load(this);
      initialize();
   }

   @Override
   protected void init() {
      Table outerTable = new Table(Neon.assets.skin);
      outerTable.setFillParent(true);
      stage.addActor(outerTable);

      Table innerTable = new Table(Neon.assets.skin);
      outerTable.add(innerTable);

      innerTable.add(new Label("Loading", Neon.assets.skin)).row();

      percentageLabel = new Label("", Neon.assets.skin);
      innerTable.add(percentageLabel).align(BaseTableLayout.RIGHT);
   }

   public void load(LoadableScreen targetScreen) {
      this.targetScreen = targetScreen;
      targetScreen.initAssets();
      if (targetScreen != this) {
         Neon.instance.setScreen(this);
      }
   }

   @Override
   public void render(float delta) {
      if (Neon.assets.updateManager()) {
         targetScreen.initialize();
         Neon.instance.setScreen(targetScreen);
         return;
      }

      if (percentageLabel != null) {
         int progress = Math.round(Neon.assets.getProgress() * 100f);
         percentageLabel.setText(progress + "%");
         super.render(delta);
      }
   }

   @Override
   public void dispose() {
      //NOP
   }

   @Override
   public void initAssets() {
      //NOP
   }

   @Override
   public void reset() {
      //NOP
   }
}
