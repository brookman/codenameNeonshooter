package eu32k.neonshooter.core.rendering;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;

public class LineRenderer {

   private Vector2 e = new Vector2();
   private Vector2 n = new Vector2();
   private Vector2 s = new Vector2();
   private Vector2 ne = new Vector2();
   private Vector2 nw = new Vector2();
   private Vector2 sw = new Vector2();
   private Vector2 se = new Vector2();

   private Camera camera;
   private Texture texture;
   private ImmediateModeRenderer20 iRend;

   public LineRenderer(Camera camera, Texture texture) {
      this.camera = camera;
      this.texture = texture;
      iRend = new ImmediateModeRenderer20(false, true, 1);
   }

   public void drawLineInterlolated(float x, float y, float x2, float y2, float thickness, Color color) {
      float sizeX = x2 - x;
      float sizeY = y2 - y;

      int steps = 30;
      float stepSize = 1.0f / steps;

      for (int i = 0; i < steps; i++) {
         float step = i * stepSize;
         float nextStep = (i + 1) * stepSize;

         float stepY = Interpolation.fade.apply(0.0f, 1.0f, step);
         float nextStepY = Interpolation.fade.apply(0.0f, 1.0f, nextStep);

         drawLine(x + step * sizeX, y + stepY * sizeY, x + nextStep * sizeX, y + nextStepY * sizeY, thickness, color);
      }
   }

   public void drawLine(float x, float y, float x2, float y2, float thickness, Color color) {
      e.set(x2 - x, y2 - y).nor().scl(thickness);

      n.set(-e.y, e.x);
      s.set(-n.x, -n.y);
      ne.set(n.x + e.x, n.y + e.y);
      nw.set(n.x - e.x, n.y - e.y);
      sw.set(-ne.x, -ne.y);
      se.set(-nw.x, -nw.y);

      texture.bind();

      iRend.begin(camera.combined, GL20.GL_TRIANGLE_STRIP);

      iRend.texCoord(0, 1);
      iRend.color(color.r, color.g, color.b, color.a);
      iRend.vertex(x + nw.x, y + nw.y, 0);

      iRend.texCoord(0, 0);
      iRend.color(color.r, color.g, color.b, color.a);
      iRend.vertex(x + sw.x, y + sw.y, 0);

      iRend.texCoord(0.5f, 1);
      iRend.color(color.r, color.g, color.b, color.a);
      iRend.vertex(x + n.x, y + n.y, 0);

      iRend.texCoord(0.5f, 0);
      iRend.color(color.r, color.g, color.b, color.a);
      iRend.vertex(x + s.x, y + s.y, 0);

      iRend.texCoord(0.5f, 1);
      iRend.color(color.r, color.g, color.b, color.a);
      iRend.vertex(x2 + n.x, y2 + n.y, 0);

      iRend.texCoord(0.5f, 0);
      iRend.color(color.r, color.g, color.b, color.a);
      iRend.vertex(x2 + s.x, y2 + s.y, 0);

      iRend.texCoord(1, 1);
      iRend.color(color.r, color.g, color.b, color.a);
      iRend.vertex(x2 + ne.x, y2 + ne.y, 0);

      iRend.texCoord(1, 0);
      iRend.color(color.r, color.g, color.b, color.a);
      iRend.vertex(x2 + se.x, y2 + se.y, 0);

      iRend.end();
   }
}