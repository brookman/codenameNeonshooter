package eu32k.neonshooter.core.rendering;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

import eu32k.neonshooter.core.entitySystem.common.GameBits;

public class BasicMapRenderer implements MapRenderer {

   private World box2dWorld;
   private LineRenderer lineRenderer;

   private Camera camera;

   private List<Vector2> from = new ArrayList<Vector2>();
   private List<Vector2> to = new ArrayList<Vector2>();

   public BasicMapRenderer(TiledMap map, Texture texture, World box2dWorld) {
      this.box2dWorld = box2dWorld;
      lineRenderer = new LineRenderer(texture);

      MapLayer layer = map.getLayers().get(0);
      float s = 32;
      for (MapObject object : layer.getObjects()) {
         if (object instanceof PolylineMapObject) {
            PolylineMapObject polylineMapObject = (PolylineMapObject) object;
            float[] vertices = polylineMapObject.getPolyline().getTransformedVertices();
            for (int i = 0; i < vertices.length - 2; i += 2) {
               from.add(new Vector2(vertices[i] / s, vertices[i + 1] / s));
               to.add(new Vector2(vertices[i + 2] / s, vertices[i + 3] / s));
            }
         } else if (object instanceof RectangleMapObject) {
            RectangleMapObject rectangleMapObject = (RectangleMapObject) object;
            Rectangle rect = rectangleMapObject.getRectangle();

            from.add(new Vector2(rect.x / s, rect.y / s));
            to.add(new Vector2(rect.width / s, rect.y / s));

            from.add(new Vector2(rect.x / s, rect.y / s));
            to.add(new Vector2(rect.x / s, rect.height / s));

            from.add(new Vector2(rect.width / s, rect.height / s));
            to.add(new Vector2(rect.x / s, rect.height / s));

            from.add(new Vector2(rect.width / s, rect.height / s));
            to.add(new Vector2(rect.width / s, rect.y / s));
         }
      }

      for (int i = 0; i < from.size(); i++) {
         createEdge(from.get(i).x, from.get(i).y, to.get(i).x, to.get(i).y);
      }
   }

   private void createEdge(float x1, float y1, float x2, float y2) {
      BodyDef bodyDef = new BodyDef();
      bodyDef.type = BodyType.StaticBody;

      Body body = box2dWorld.createBody(bodyDef);

      EdgeShape edge = new EdgeShape();
      edge.set(x1, y1, x2, y2);
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
      render(0.04f, Color.WHITE);
   }

   public void render(float thickness, Color color) {
      lineRenderer.setCamera(camera);
      for (int i = 0; i < from.size(); i++) {
         lineRenderer.drawLine(from.get(i).x, from.get(i).y, to.get(i).x, to.get(i).y, thickness, color);
      }
   }

   @Override
   public void render(int[] layers) {
      // Not implemented
   }
}
