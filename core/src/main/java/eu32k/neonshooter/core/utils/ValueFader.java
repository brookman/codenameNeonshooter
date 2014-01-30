package eu32k.neonshooter.core.utils;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;

public class ValueFader {

   private float speed;
   private float from;
   private float to;
   private Interpolation interpolation;

   private float alpha;
   private float value;

   public ValueFader(float speed, float from, float to, Interpolation interpolation) {
      super();
      this.speed = speed;
      this.from = from;
      this.to = to;
      value = from;
      this.interpolation = interpolation;
   }

   public ValueFader(float speed) {
      this(speed, 0, 0, Interpolation.linear);
   }

   public void setFade(float from, float to) {
      this.from = from;
      this.to = to;
      value = from;
      alpha = 0;
   }

   public float update(float delta) {
      alpha = MathUtils.clamp(alpha + delta * speed, 0, 1);
      value = interpolation.apply(from, to, alpha);
      return value;
   }

   public float getValue() {
      return value;
   }

   public float getSpeed() {
      return speed;
   }

   public void setSpeed(float speed) {
      this.speed = speed;
   }

   public float getFrom() {
      return from;
   }

   public float getTo() {
      return to;
   }

   public void fadeTo(float to) {
      if (this.to != to) {
         setFade(value, to);
      }
   }

   public Interpolation getInterpolation() {
      return interpolation;
   }

   public void setInterpolation(Interpolation interpolation) {
      this.interpolation = interpolation;
   }
}
