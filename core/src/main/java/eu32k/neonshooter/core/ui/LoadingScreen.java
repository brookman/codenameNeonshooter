package eu32k.neonshooter.core.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.esotericsoftware.tablelayout.BaseTableLayout;

import eu32k.neonshooter.core.Neon;
import eu32k.neonshooter.core.model.loading.NeonScreen;
import eu32k.neonshooter.core.utils.AdvancedShader;
import eu32k.neonshooter.core.utils.ShaderTools;

public class LoadingScreen extends NeonScreen {

   private NeonScreen targetScreen;

   private AdvancedShader shader;
   private Label percentageLabel;

   private float progress = 0.0f;

   public LoadingScreen() {
      load(this);
      initialize();
   }

   @Override
   protected void init() {
      shader = ShaderTools.getShader("hexastuff");
      Table outerTable = new Table(Neon.assets.skin);
      outerTable.setFillParent(true);
      stage.addActor(outerTable);

      Table innerTable = new Table(Neon.assets.skin);
      outerTable.add(innerTable);

      innerTable.add(new Label("Loading", Neon.assets.skin)).row();

      percentageLabel = new Label("", Neon.assets.skin);
      innerTable.add(percentageLabel).align(BaseTableLayout.RIGHT);
   }

   public void load(NeonScreen targetScreen) {
      this.targetScreen = targetScreen;
      progress = 0.0f;
      targetScreen.initAssets();
      if (targetScreen != this) {
         Neon.instance.setScreen(this);
      }
      if (Neon.assets.updateManager()) {
         finish();
      }
   }

   private void finish() {
      progress = 1.0f;
      targetScreen.initialize();
      Neon.instance.setScreen(targetScreen);
   }

   @Override
   public void render(float delta) {
      Neon.assets.updateManager();
      if (progress < Neon.assets.getProgress()) {
         progress += delta * 0.5f;
      }

      if (progress >= 1.0f) {
         finish();
         return;
      }

      // if (percentageLabel != null) {
      // int progress = Math.round(Neon.assets.getProgress() * 100f);
      // percentageLabel.setText(progress + "%");
      // super.render(delta);
      // }
      // super.render(delta);

      shader.begin();
      shader.setUniformf("loaded", progress);
      shader.renderToScreeQuad(new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
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
