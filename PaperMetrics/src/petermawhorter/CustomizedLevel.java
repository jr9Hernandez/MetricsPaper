package petermawhorter;

import java.util.Random;
import java.util.List;
import java.util.Map;
import java.util.HashSet;

import petermawhorter.components.ComponentType;
import petermawhorter.components.Hierarchy;
import petermawhorter.components.MarioComponentType;

import dk.itu.mario.MarioInterface.Constraints;
import dk.itu.mario.MarioInterface.GamePlay;
import dk.itu.mario.MarioInterface.LevelInterface;
import dk.itu.mario.engine.sprites.SpriteTemplate;
import dk.itu.mario.engine.sprites.Enemy;
import dk.itu.mario.level.Level;


public class CustomizedLevel extends Level implements LevelInterface, Constraints {

  Random random;

  private int difficulty;
  private int type;

  private boolean hillsAllowed = false;
  private boolean ceilingAllowed = false;

  private GamePlay playerModel;

  public CustomizedLevel(int width, int height, long seed, int difficulty, int type, GamePlay playerMetrics) {
	  
    super(Constraints.levelWidth, height);
    this.playerModel = playerMetrics;
    this.difficulty = difficulty;
    this.type = type;
    if (this.type == LevelInterface.TYPE_OVERGROUND) {
      this.hillsAllowed = true;
    }
    if (this.type == LevelInterface.TYPE_CASTLE ||
        this.type == LevelInterface.TYPE_UNDERGROUND) {
      this.ceilingAllowed = true;
    }
    this.random = new Random(seed);
    Generator.init();
    this.xExit = -1;
    this.yExit = -1;
    this.create(seed, difficulty, type);
  }

  public void create(long seed, int difficulty, int type) {

    Library.init();
    //Generator.buildTestLevel(this);
    //Generator.buildCustomLevel(this);
    Generator.buildCaseLevel(
      this,
      this.playerModel,
      Constraints.turtels,
      Constraints.coinBlocks,
      Constraints.gaps
    );
    //Generator.buildDebugLevel(this);

    // Fixes up the sprites to make ground continuous and have proper
    // edges, etc.
    this.fixWalls();

    Library.test();

  }

  public void setExit(int x, int y) {
    this.xExit = x;
    this.yExit = y;
  }

  private ComponentType getTileComponentType(int x, int y) {
    if (x < 0 || x >= this.width || y < 0 || y >= this.height) {
      return Hierarchy.LevelEdge;
    }
    int spriteType = (int) this.getBlock(x, y);
    if (spriteType < 0) {
      spriteType += 256;
    }
    Map<Integer, ComponentType> types = MarioComponentType.knownSpriteIndexes;
    if (types.containsKey(spriteType)) {
      return types.get(spriteType);
    }
    return null;
  }

  private void fixBlockType(int x, int y) {
    int original = this.getBlock(x, y);
    int blueblock = 0 + 3*4 + 1*16;
    int log = 2 + 2*4 + 2*16;
    if (original < 0) {
      original += 256;
    }
    int result = original;
    if (original == 0) {
      return;
    }
    // Note: Remember that increasing y-value is "up", but is down on the
    // screen.
    Map<Integer, ComponentType> types = MarioComponentType.knownSpriteIndexes;
    if (types.get(original) == Hierarchy.NormalGround) {
      ComponentType up = this.getTileComponentType(x, y + 1);
      ComponentType down = this.getTileComponentType(x, y - 1);
      ComponentType left = this.getTileComponentType(x - 1, y);
      ComponentType right = this.getTileComponentType(x + 1, y);
      ComponentType ul = this.getTileComponentType(x - 1, y + 1);
      ComponentType ur = this.getTileComponentType(x + 1, y + 1);
      ComponentType lr = this.getTileComponentType(x + 1, y - 1);
      ComponentType ll = this.getTileComponentType(x - 1, y - 1);
      boolean u = false, d = false, l = false, r = false;
      if (up != Hierarchy.LevelEdge && up != Hierarchy.NormalGround) {
        u = true;
        result += 16;
      } else if (ul != Hierarchy.LevelEdge && ul != Hierarchy.NormalGround
              && ur != Hierarchy.LevelEdge && ur != Hierarchy.NormalGround) {
        u = true;
        result += 16;
      }
      if (down != Hierarchy.LevelEdge && down != Hierarchy.NormalGround) {
        d = true;
        result -= 16;
      }
      if (left != Hierarchy.LevelEdge && left != Hierarchy.NormalGround) {
        l = true;
        result -= 1;
      }
      if (right != Hierarchy.LevelEdge && right != Hierarchy.NormalGround) {
        r = true;
        result += 1;
      } else if (ur != Hierarchy.LevelEdge && ur != Hierarchy.NormalGround
              && lr != Hierarchy.LevelEdge && lr != Hierarchy.NormalGround) {
        r = true;
        result += 1;
      }
      if (u && d) {
        result = blueblock;
      } else if (l && r) {
        result = 0;
      } else if (down == Hierarchy.LedgeFiller) {
        result += 3*16;
      }
      if (result == original) {
        if (ul != Hierarchy.LevelEdge && ul != Hierarchy.NormalGround) {
          result += 2;
          result += 2*16;
        } else if (ur != Hierarchy.LevelEdge && ur != Hierarchy.NormalGround) {
          result += 2;
          result += 16;
        } else if (lr != Hierarchy.LevelEdge && lr != Hierarchy.NormalGround) {
          result += 2;
        } else if (ll != Hierarchy.LevelEdge && ll != Hierarchy.NormalGround) {
          result += 2;
          result -= 16;
        }
      }
      if (result > 0 && result != blueblock) {
        if (this.type == LevelInterface.TYPE_CASTLE) {
          result += 8;
        } else if (this.type == LevelInterface.TYPE_UNDERGROUND) {
          result += 12;
        }
      }
    } else if (types.get(original) == Hierarchy.Ledge) {
      ComponentType down = this.getTileComponentType(x, y - 1);
      ComponentType left = this.getTileComponentType(x - 1, y);
      ComponentType right = this.getTileComponentType(x + 1, y);
      if (left != Hierarchy.LevelEdge && left != Hierarchy.Ledge &&
          left != Hierarchy.BulletTower) {
        result -= 1;
      }
      if (right != Hierarchy.LevelEdge && right != Hierarchy.Ledge &&
          right != Hierarchy.BulletTower) {
        result += 1;
      }
      byte extend = (byte) (result + 16);
      if (down == Hierarchy.LedgeFiller) {
        result += 3*16;
      }
      if (this.type == LevelInterface.TYPE_CASTLE) {
        result = 12 + 0 + 1*16;
      } else if (this.type == LevelInterface.TYPE_UNDERGROUND) {
        result = 8 + 1 + 0*16;
      } else {
        int scany = y;
        while (scany < this.height) {
          scany += 1;
          ComponentType here = this.getTileComponentType(x, scany);
          if (here == null || here == Hierarchy.Coin
           || (here.isSubtype(Hierarchy.Block) && here != Hierarchy.BlueBlock)){
            this.setBlock(x, scany, extend);
            //System.out.println("setBlock(x, scany, extend): " + extend);
          } else if (here == Hierarchy.Ledge) {
            break;
          }
        }
      }
    } else if (types.get(original) == Hierarchy.Pipe) {
      ComponentType down = this.getTileComponentType(x, y - 1);
      ComponentType right = this.getTileComponentType(x + 1, y);
      if (down == Hierarchy.LevelEdge || down == Hierarchy.Pipe) {
        result += 16;
      }
      if (right != Hierarchy.LevelEdge && right != Hierarchy.Pipe) {
        result += 1;
      }
    } else if (types.get(original) == Hierarchy.SkinnyPipe) {
      ComponentType up = this.getTileComponentType(x, y + 1);
      ComponentType down = this.getTileComponentType(x, y - 1);
      if (up != Hierarchy.LevelEdge && up != Hierarchy.SkinnyPipe) {
        result += 16;
        if (down != Hierarchy.LevelEdge && down != Hierarchy.SkinnyPipe) {
          // DEBUG:
          result = log;
        }
      } else if (down != Hierarchy.LevelEdge && down != Hierarchy.SkinnyPipe) {
        result -= 16;
      }
    } else if (types.get(original) == Hierarchy.StackedLogs) {
      ComponentType up = this.getTileComponentType(x, y + 1);
      ComponentType down = this.getTileComponentType(x, y - 1);
      if (down == null || down == Hierarchy.Coin) {
        this.setBlock(x, y - 1, (byte) (result - 16));
        //System.out.println("this.setBlock(x, y - 1, (byte) (result - 16)): " + (result - 16) );
      } else if (down != Hierarchy.StackedLogs) {
        result = blueblock;
      }
      if (up != Hierarchy.LevelEdge && up != Hierarchy.StackedLogs) {
        result += 16;
      }
    } else if (types.get(original) == Hierarchy.BulletTower) {
      ComponentType down = this.getTileComponentType(x, y - 1);
      if (down == Hierarchy.BulletCannon) {
        result -= 16;
      }
    } else if (types.get(original) == Hierarchy.ArrowSign) {
      ComponentType up = this.getTileComponentType(x, y + 1);
      ComponentType right = this.getTileComponentType(x + 1, y);
      ComponentType ur = this.getTileComponentType(x + 1, y + 1);
      if (up == null && right == null && ur == null) {
        this.setBlock(x, y + 1, (byte) (result + 16));
        //System.out.println("this.setBlock(x, y + 1, (byte) (result + 16)): " + (result + 16));
        this.setBlock(x + 1, y, (byte) (result + 1));
        //System.out.println("this.setBlock(x, y + 1, (byte) (result + 1)): " + (result + 1));
        this.setBlock(x + 1, y + 1, (byte) (result + 1 + 16));
        //System.out.println("this.setBlock(x, y + 1, (byte) (result + 1 + 16)): " + (result + 1 + 16));
      } else {
        result = 0;
      }
    } else if (types.get(original) == Hierarchy.Grass) {
      ComponentType left = this.getTileComponentType(x - 1, y);
      ComponentType right = this.getTileComponentType(x + 1, y);
      if (left != Hierarchy.LevelEdge && left != Hierarchy.Grass) {
        result -= 1;
      }
      if (right != Hierarchy.LevelEdge && right != Hierarchy.Grass) {
        result += 1;
      }
    } else if (types.get(original) == Hierarchy.Bush) {
      ComponentType left = this.getTileComponentType(x - 1, y);
      ComponentType right = this.getTileComponentType(x + 1, y);
      if (left != Hierarchy.LevelEdge && left != Hierarchy.Bush) {
        result -= 1;
      }
      if (right != Hierarchy.LevelEdge && right != Hierarchy.Bush) {
        result += 1;
      }
    } else if (types.get(original) == Hierarchy.Exit) {
      result = (byte) 0;
      this.setExit(x, y + 1);
    }
    this.setBlock(x, y, (byte) result);
    //System.out.println("this.setBlock(x, y, (byte) result): " + result);
  }

  private void fixWalls() {
    for (int x = 0; x < this.width + 1; x++) {
      for (int y = 0; y < this.height + 1; y++) {
        this.fixBlockType(x, y);
      }
    }
    if (this.xExit < 0) {
      this.setExit(10, 10);
    }
  }
}
