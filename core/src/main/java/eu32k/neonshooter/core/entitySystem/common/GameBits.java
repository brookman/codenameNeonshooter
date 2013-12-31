package eu32k.neonshooter.core.entitySystem.common;

import eu32k.gdx.common.Bits;

public class GameBits {

   public static final short PLAYER_CATEGORY = 1;
   public static final short PLAYER_BULLET_CATEGORY = 2;
   public static final short ENEMY_CATEGORY = 4;
   public static final short ENEMY_BULLET_CATEGORY = 8;
   public static final short SCENERY_CATEGORY = 16;
   public static final short VOID_CATEGORY = 32;

   public static final short PLAYER_MASK = (short) (ENEMY_CATEGORY | ENEMY_BULLET_CATEGORY | SCENERY_CATEGORY);
   public static final short PLAYER_BULLET_MASK = (short) (ENEMY_CATEGORY | SCENERY_CATEGORY);
   public static final short ENEMY_MASK = (short) (ENEMY_CATEGORY | PLAYER_CATEGORY | PLAYER_BULLET_CATEGORY | SCENERY_CATEGORY);
   public static final short ENEMY_BULLET_MASK = (short) (PLAYER_CATEGORY | SCENERY_CATEGORY);
   public static final short SCENERY_MASK = (short) (PLAYER_CATEGORY | PLAYER_BULLET_CATEGORY | ENEMY_CATEGORY | ENEMY_BULLET_CATEGORY | SCENERY_CATEGORY);
   public static final short VOID_MASK = 0;

   public static final Bits PLAYER = new Bits(PLAYER_CATEGORY, PLAYER_MASK);
   public static final Bits PLAYER_BULLET = new Bits(PLAYER_BULLET_CATEGORY, PLAYER_BULLET_MASK);
   public static final Bits ENEMY = new Bits(ENEMY_CATEGORY, ENEMY_MASK);
   public static final Bits ENEMY_BULLET = new Bits(ENEMY_BULLET_CATEGORY, ENEMY_BULLET_MASK);
   public static final Bits SCENERY = new Bits(SCENERY_CATEGORY, SCENERY_MASK);
   public static final Bits VOID = new Bits(VOID_CATEGORY, VOID_MASK);

}
