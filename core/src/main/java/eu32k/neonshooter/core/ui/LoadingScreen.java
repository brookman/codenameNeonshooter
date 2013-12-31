package eu32k.neonshooter.core.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.esotericsoftware.tablelayout.BaseTableLayout;

import eu32k.neonshooter.core.Neon;

public class LoadingScreen implements Screen {

   public Class<?> targetScreen;

   private Label percentageLabel;
   private Stage stage;

   public LoadingScreen(Class<?> targetScreen) {
      this.targetScreen = targetScreen;

   }

   @Override
   public void render(float delta) {
      if (Neon.assets.updateManager()) {
         Neon.ui.showScreen(targetScreen);
         return;
      }

      if (stage == null) {
         stage = new Stage();

         Table outerTable = new Table(Neon.assets.skin);
         outerTable.setFillParent(true);
         stage.addActor(outerTable);

         Table innerTable = new Table(Neon.assets.skin);
         outerTable.add(innerTable);

         innerTable.add(new Label("Loading", Neon.assets.skin)).row();

         percentageLabel = new Label("", Neon.assets.skin);
         innerTable.add(percentageLabel).align(BaseTableLayout.RIGHT);
      }
      int progress = Math.round(Neon.assets.getProgress() * 100f);
      percentageLabel.setText(progress + "%");
      stage.act(delta);
      stage.draw();
   }

   @Override
   public void resize(int width, int height) {

   }

   @Override
   public void show() {
      Gdx.input.setInputProcessor(stage);
      Gdx.app.log("LoadingScreen", "Show");
   }

   @Override
   public void hide() {

   }

   @Override
   public void pause() {

   }

   @Override
   public void resume() {

   }

   @Override
   public void dispose() {
      // TODO Auto-generated method stub

   }

}
