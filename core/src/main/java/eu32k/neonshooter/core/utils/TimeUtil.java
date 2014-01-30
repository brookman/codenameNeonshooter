package eu32k.neonshooter.core.utils;

public class TimeUtil {

   private static long startTime = System.nanoTime();

   public static float getTime() {
      long difference = System.nanoTime() - startTime;
      double seconds = difference / 1000000000.0;
      return (float) seconds;
   }
}
