package eu32k.neonshooter.core.model;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import eu32k.neonshooter.core.spawning.SpawnerInfo;

public class ExtendedMap {

   public TiledMap map;
   public List<Line> lines = new ArrayList<Line>();
   public Vector2 playerSpawn = new Vector2();
   public List<SpawnerInfo> enemySpawns = new ArrayList<SpawnerInfo>();
   public List<Vector2> blackHoles = new ArrayList<Vector2>();

   public ExtendedMap(TiledMap map) {
      this.map = map;

      MapLayer layer1 = map.getLayers().get(0);
      float s = 32;
      for (MapObject object : layer1.getObjects()) {
         if (object instanceof PolylineMapObject) {
            PolylineMapObject polylineMapObject = (PolylineMapObject) object;
            float[] vertices = polylineMapObject.getPolyline().getTransformedVertices();
            for (int i = 0; i < vertices.length - 2; i += 2) {
               lines.add(new Line(vertices[i] / s, vertices[i + 1] / s, vertices[i + 2] / s, vertices[i + 3] / s));
            }
         } else if (object instanceof RectangleMapObject) {
            RectangleMapObject rectangleMapObject = (RectangleMapObject) object;
            Rectangle rect = rectangleMapObject.getRectangle();
            lines.add(new Line(rect.x / s, rect.y / s, rect.width / s, rect.y / s));
            lines.add(new Line(rect.x / s, rect.y / s, rect.x / s, rect.height / s));
            lines.add(new Line(rect.width / s, rect.height / s, rect.x / s, rect.height / s));
            lines.add(new Line(rect.width / s, rect.height / s, rect.width / s, rect.y / s));
         }
      }

      MapLayer layer2 = map.getLayers().get(1);
      for (MapObject object : layer2.getObjects()) {
         if (object instanceof EllipseMapObject) {
            Ellipse ellipse = ((EllipseMapObject) object).getEllipse();
            if (object.getName().equals("playerSpawn")) {
               playerSpawn.x = ellipse.x / s + (ellipse.width / s) / 2.0f;
               playerSpawn.y = ellipse.y / s + (ellipse.height / s) / 2.0f;
            } else if (object.getName().equals("enemySpawn")) {
               Vector2 enemySpawn = new Vector2(ellipse.x / s + (ellipse.width / s) / 2.0f, ellipse.y / s + (ellipse.height / s) / 2.0f);
               enemySpawns.add(new SpawnerInfo(enemySpawn, object.getProperties()));
            } else if (object.getName().equals("blackHole")) {
               blackHoles.add(new Vector2(ellipse.x / s + (ellipse.width / s) / 2.0f, ellipse.y / s + (ellipse.height / s) / 2.0f));
            }
         }
      }
   }

   public class Line {
      public Vector2 from;
      public Vector2 to;

      public Line(float fromX, float fromY, float toX, float toY) {
         from = new Vector2(fromX, fromY);
         to = new Vector2(toX, toY);
      }
   }

}
