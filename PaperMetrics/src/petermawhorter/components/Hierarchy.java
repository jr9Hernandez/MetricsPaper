package petermawhorter.components;

import dk.itu.mario.engine.sprites.Enemy;

public class Hierarchy {

  // Estimates of the number of similarities in type makeup that qualify as
  // "high", based on the layout of the type hierarchy.
  public static final int HIGH_AFFINITY = 2;

  public static final int RED_KOOPA = 0;
  public static final int GREEN_KOOPA = 1;
  public static final int GOOMBA = 2;
  public static final int SPIKY = 3;
  public static final int FLOWER = 4;

  public static boolean spriteMappingsAdded = false;

  /*
  public static ComponentType ExtendsUp =
    new ComponentType('Ã¢â€¢â‚¬', ComponentType.Meta);
  public static ComponentType ExtendsDown =
    new ComponentType('Ã¢â€¢ï¿½', ComponentType.Meta);
  public static ComponentType ExtendsLeft =
    new ComponentType('Ã¢â€�Â½', ComponentType.Meta);
  public static ComponentType ExtendsRight =
    new ComponentType('Ã¢â€�Â¾', ComponentType.Meta);
  public static ComponentType EdgeUp =
    new ComponentType('Ã¢â€¢Ë†', ComponentType.Meta);
  public static ComponentType EdgeDown =
    new ComponentType('Ã¢â€¢â€¡', ComponentType.Meta);
  public static ComponentType EdgeLeft =
    new ComponentType('Ã¢â€¢Å ', ComponentType.Meta);
  public static ComponentType EdgeRight =
    new ComponentType('Ã¢â€¢â€°', ComponentType.Meta);
   */
  
  public static ComponentType ExtendsUp = new ComponentType('╀', ComponentType.Meta);
	  public static ComponentType ExtendsDown = new ComponentType('╁', ComponentType.Meta);
	  public static ComponentType ExtendsLeft = new ComponentType('┽', ComponentType.Meta);
	  public static ComponentType ExtendsRight = new ComponentType('┾', ComponentType.Meta);
	  public static ComponentType EdgeUp = new ComponentType('╈', ComponentType.Meta);
	  public static ComponentType EdgeDown = new ComponentType('╇', ComponentType.Meta);
	  public static ComponentType EdgeLeft = new ComponentType('╊', ComponentType.Meta);
	  public static ComponentType EdgeRight = new ComponentType('╉', ComponentType.Meta);

  public static ComponentType Origin = new ComponentType('0');
  public static ComponentType PotentialPosition = new ComponentType('@');
  public static ComponentType Platform = new ComponentType('-');
  public static ComponentType Enemy = new ComponentType('e');
  public static ComponentType SpecialEnemy = new ComponentType('E');
  public static ComponentType Decoration = new ComponentType('z');
  public static ComponentType LevelEdge = new ComponentType('\\');
  public static ComponentType Exit =
    new MarioComponentType('X', (12 + 0 + 4*16), -1);
  /*
  public static ComponentType Coin =
    new MarioComponentType('Ã‚Â¢', (0 + 0 + 2*16), -1); 
  */
  public static ComponentType Coin = new MarioComponentType('¢', (0 + 0 + 2*16), -1);
  

  public static ComponentType BulletEnemy =
    new ComponentType('<', SpecialEnemy);
  public static ComponentType JumpingEnemy =
    new ComponentType('^', SpecialEnemy);
  public static ComponentType EnemySpawn =
    new ComponentType('>', SpecialEnemy);

  /* public static ComponentType Ephemeral = new ComponentType('Ã¢Å’â€¡', Platform); */
  public static ComponentType Ephemeral = new ComponentType('⌇', Platform);

  public static MarioComponentType Ledge =
    new MarioComponentType('_', (4 + 1 + 8*16), -1, Platform);
  public static MarioComponentType LedgeFiller =
    new MarioComponentType(')', (4 + 1 + 9*16), -1);

  public static ComponentType Ground = new ComponentType('=', Platform);
  public static ComponentType Block = new ComponentType('*', Platform);

  public static MarioComponentType NormalGround =
    new MarioComponentType('+', (0 + 1 + 9*16), -1, Ground);
  public static MarioComponentType Pipe =
    new MarioComponentType('[', (8 + 2 + 0*16), -1, Ground);
  public static MarioComponentType SkinnyPipe =
    new MarioComponentType('|', (8 + 0 + 2*16), -1, Ground);

  public static MarioComponentType StackedLogs =
    new MarioComponentType('o', (8 + 1 + 2*16), -1, Ground);

  public static MarioComponentType BulletCannon =
    new MarioComponentType('%', (12 + 2 + 0*16), -1, EnemySpawn);
  public static MarioComponentType BulletTower =
    new MarioComponentType(']', (12 + 2 + 2*16), -1, Ground);

  public static ComponentType QuestionBlock = new ComponentType('Q', Block);
  public static ComponentType BrickBlock = new ComponentType('B', Block);
  public static ComponentType EmptiedQuestionBlock =
    new MarioComponentType('&', (4 + 0 + 0*16), -1, Block);
  // Flip blocks don't work; don't use this:
  public static MarioComponentType FlipBlock =
    new MarioComponentType('/', (4 + 0 + 2*16), -1, Block);
  public static MarioComponentType RockBlock =
    new MarioComponentType('.', (8 + 1 + 0*16), -1, Block);
  public static MarioComponentType WoodBlock =
    new MarioComponentType(';', (12 + 0 + 0*16), -1, Block);
  public static MarioComponentType BlueBlock =
    new MarioComponentType(':', (12 + 0 + 1*16), -1, Block);

  public static MarioComponentType CoinQuestion =
    new MarioComponentType('?', (4 + 1 + 1*16), -1, QuestionBlock);
  /* public static MarioComponentType PowerupQuestion=
    new MarioComponentType('Ã¢â‚¬Â½', (4 + 2 + 1*16), -1, QuestionBlock); */
  public static MarioComponentType PowerupQuestion=
		    new MarioComponentType('‽', (4 + 2 + 1*16), -1, QuestionBlock);

  public static MarioComponentType EmptyBrick =
    new MarioComponentType('#', (0 + 0 + 1*16), -1, BrickBlock);
  /* public static MarioComponentType CoinBrick =
    new MarioComponentType('Ã¢â€šÂ¬', (0 + 1 + 1*16), -1, BrickBlock); */
  public static MarioComponentType CoinBrick =
		    new MarioComponentType('€', (0 + 1 + 1*16), -1, BrickBlock);
  public static MarioComponentType PowerupBrick =
    new MarioComponentType('!', (0 + 2 + 1*16), -1, BrickBlock);
  
  public static MarioComponentType Goomba =
    new MarioComponentType('g', -1, GOOMBA, Enemy);
  public static MarioComponentType GreenKoopa =
    new MarioComponentType('k', -1, GREEN_KOOPA, Enemy);
  public static MarioComponentType RedKoopa =
    new MarioComponentType('r', -1, RED_KOOPA, Enemy);
  public static MarioComponentType SpikeShell =
    new MarioComponentType('s', -1, SPIKY, Enemy);

  public static ComponentType WingedEnemy = new ComponentType('W', Enemy);

  public static MarioComponentType WingedGoomba =
    new MarioComponentType('G', -2, GOOMBA, Enemy);
  public static MarioComponentType WingedGreenKoopa =
    new MarioComponentType('K', -2, GREEN_KOOPA, Enemy);
  public static MarioComponentType WingedRedKoopa =
    new MarioComponentType('R', -2, RED_KOOPA, Enemy);
  public static MarioComponentType WingedSpikeShell =
    new MarioComponentType('S', -2, SPIKY, Enemy);

  // Single bullets don't work; don't use this:
  public static ComponentType BulletBill = new ComponentType('b', BulletEnemy);

  public static MarioComponentType PiranhaFlower =
    new MarioComponentType('f', -1, FLOWER, JumpingEnemy);

  // Decoration filling not implemented yet; don't use these:
  /*public static MarioComponentType ArrowSign =
    new MarioComponentType('Ã¢â€ â€™', (0 + 3 + 4*16), -1, Decoration);*/
  public static MarioComponentType ArrowSign =
		    new MarioComponentType('→', (0 + 3 + 4*16), -1, Decoration);
  public static MarioComponentType Grass =
    new MarioComponentType('`', (0 + 1 + 5*16), -1, Decoration);
  public static MarioComponentType Bush =
    new MarioComponentType(',', (0 + 1 + 6*16), -1, Decoration);
  public static MarioComponentType CloudBush =
    new MarioComponentType('~', (4 + 2 + 4*16), -1, Decoration);

  /**
   * Adds additional mappings from sprite indices to component types. Take a
   * look at mapsheet.png from the resources folder to get a sense of what maps
   * where.
   */
  public static void addSpriteMappings() {
    if (Hierarchy.spriteMappingsAdded) {
      return;
    }
    for (int x = 0; x < 4; ++x) {
      MarioComponentType.knownSpriteIndexes.put(x + 2*16, Coin);
    }
    for (int x = 4; x < 7; ++x) {
      MarioComponentType.knownSpriteIndexes.put(x + 8*16, Ledge);
      MarioComponentType.knownSpriteIndexes.put(x + 11*16, Ledge);
    }
    for (int y = 9; y < 11; ++y) {
      for (int x = 4; x < 7; ++x) {
        MarioComponentType.knownSpriteIndexes.put(x + y*16, LedgeFiller);
      }
    }
    for (int y = 8; y < 12; ++y) {
      MarioComponentType.knownSpriteIndexes.put(4 + 3 + y*16, LedgeFiller);
    }
    for (int x = 0; x < 4; ++x) {
      for (int y = 8; y < 12; ++y) {
        MarioComponentType.knownSpriteIndexes.put(x + y*16, NormalGround);
      }
    }
    for (int x = 8; x < 16; ++x) {
      for (int y = 8; y < 12; ++y) {
        MarioComponentType.knownSpriteIndexes.put(x + y*16, NormalGround);
        if (y == 11) {
          if (x < 11 || (x > 11 && x < 15)) {
            MarioComponentType.knownSpriteIndexes.remove(x + y*16);
          }
        }
      }
    }
    for (int x = 10; x < 12; ++x) {
      for (int y = 0; y < 2; ++y) {
        MarioComponentType.knownSpriteIndexes.put(x + y*16, Pipe);
      }
    }
    for (int y = 1; y < 4; ++y) {
      MarioComponentType.knownSpriteIndexes.put(8 + y*16, SkinnyPipe);
    }
    for (int y = 2; y < 4; ++y) {
      MarioComponentType.knownSpriteIndexes.put(9 + y*16, StackedLogs);
    }
    for (int y = 1; y < 3; ++y) {
      MarioComponentType.knownSpriteIndexes.put(14 + y*16, BulletTower);
    }
    for (int x = 4; x < 8; ++x) {
      MarioComponentType.knownSpriteIndexes.put(x + 2*16, FlipBlock);
    }
    MarioComponentType.knownSpriteIndexes.put(4 + 0 + 1*16, CoinQuestion);
    MarioComponentType.knownSpriteIndexes.put(4 + 3 + 1*16, CoinQuestion);
    MarioComponentType.knownSpriteIndexes.put(0 + 3 + 1*16, EmptyBrick);
    for (int x = 0; x < 3; ++x) {
      MarioComponentType.knownSpriteIndexes.put(x + 5*16, Grass);
    }
    for (int x = 0; x < 3; ++x) {
      MarioComponentType.knownSpriteIndexes.put(x + 6*16, Bush);
    }
    for (int x = 6; x < 12; ++x) {
      for (int y = 4; y < 8; ++y) {
        MarioComponentType.knownSpriteIndexes.put(x + y*16, CloudBush);
        if (x > 10 && y > 6) {
          MarioComponentType.knownSpriteIndexes.remove(x + y*16);
        }
      }
    }

    spriteMappingsAdded = true;
  }
}
