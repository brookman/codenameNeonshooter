package eu32k.neonshooter.core.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;

public class AdvancedShader extends ShaderProgram {

   private boolean clear = true;

   private Vector2 resolutionTemp = new Vector2();

   public AdvancedShader(String vertexShader, String fragmentShader) {
      super(vertexShader, fragmentShader);
   }

   public void renderToScreeQuad(Vector2 resolution) {
      renderToQuad(null, false, resolution);
   }

   public void renderToQuad(FrameBuffer frameBuffer) {
      resolutionTemp.set(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
      renderToQuad(frameBuffer, true, resolutionTemp);
   }

   public void renderToQuad(FrameBuffer frameBuffer, boolean flip, Vector2 resolution) {
      if (hasUniform("date")) {
         setUniformf("date", TimeUtil.getTime());

      }
      if (hasUniform("resolution")) {
         setUniformf("resolution", resolution);
      }

      if (frameBuffer != null) {
         frameBuffer.begin();
         if (clear) {
            Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
         }
      }
      if (flip) {
         PrimitivesFactory.QUAD_FLIPPED.render(this, GL20.GL_TRIANGLE_FAN);
      } else {
         PrimitivesFactory.QUAD_NORMAL.render(this, GL20.GL_TRIANGLE_FAN);
      }

      if (frameBuffer != null) {
         frameBuffer.end();
      }
      end();
   }

   public boolean isClear() {
      return clear;
   }

   public void setClear(boolean clear) {
      this.clear = clear;
   }
}
