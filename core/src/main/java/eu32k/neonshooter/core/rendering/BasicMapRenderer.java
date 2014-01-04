package eu32k.neonshooter.core.rendering;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

import eu32k.neonshooter.core.Neon;
import eu32k.neonshooter.core.entitySystem.common.GameBits;
import eu32k.neonshooter.core.model.ExtendedMap;
import eu32k.neonshooter.core.model.ExtendedMap.Line;

public class BasicMapRenderer implements MapRenderer {

   private World box2dWorld;
   private LineRenderer lineRenderer;

   private ExtendedMap map;

   private Camera camera;

   public BasicMapRenderer(ExtendedMap map, Texture texture, World box2dWorld) {
      this.map = map;
      this.box2dWorld = box2dWorld;
      lineRenderer = new LineRenderer(texture);

      for (Line line : map.lines) {
         createEdge(line);
      }
   }

   private void createEdge(Line line) {
      BodyDef bodyDef = new BodyDef();
      bodyDef.type = BodyType.StaticBody;

      Body body = box2dWorld.createBody(bodyDef);

      EdgeShape edge = new EdgeShape();
      edge.set(line.from.x, line.from.y, line.to.x, line.to.y);
      Fixture fixture = body.createFixture(edge, 0f);
      Filter filter = fixture.getFilterData();
      filter.categoryBits = GameBits.SCENERY.category;
      filter.maskBits = GameBits.SCENERY.mask;
      fixture.setFilterData(filter);
   }

   @Override
   public void setView(OrthographicCamera camera) {
      this.camera = camera;
   }

   @Override
   public void setView(Matrix4 projectionMatrix, float viewboundsX, float viewboundsY, float viewboundsWidth, float viewboundsHeight) {
      // Not implemented
   }

   @Override
   public void render() {
      Color color = new Color(Neon.fx.primaryColor);
      color.r = 1.0f - color.r * 0.5f;
      color.g = 1.0f - color.g * 0.5f;
      color.b = 1.0f - color.b * 0.5f;

      render(0.04f, color);
   }

   public void render(float thickness, Color color) {
      lineRenderer.begin(camera.combined);
      for (Line line : map.lines) {
         lineRenderer.drawLine(line.from.x, line.from.y, line.to.x, line.to.y, thickness, color);
      }
      lineRenderer.end();
   }

   @Override
   public void render(int[] layers) {
      // Not implemented
   }
}
