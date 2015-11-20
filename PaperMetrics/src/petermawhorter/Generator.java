package petermawhorter;

import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import java.awt.Point;

import petermawhorter.cases.AnchoredCase;
import petermawhorter.cases.Case;
import petermawhorter.cases.GlobCase;
import petermawhorter.components.Component;
import petermawhorter.components.ComponentType;
import petermawhorter.components.Hierarchy;
import petermawhorter.components.MarioComponentType;

import dk.itu.mario.level.Level;
import dk.itu.mario.engine.sprites.SpriteTemplate;
import dk.itu.mario.MarioInterface.GamePlay;


public class Generator {
  private Generator() {}

  public static final int AUTOEXPAND_LEDGE = 2;
  public static final int AUTOEXPAND_BLOCK = 1;
  public static final int AUTOEXPAND_GROUND = 5;
  public static final int EDGE_BACKOFF = 3;

  public static final String testLevel =
		    "                                                  \n" +
		    "                                                  \n" +
		    "                                                  \n" +
		    "        ;                                         \n" +
		    "                 ‽                                \n" +
		    ".                    ##€         :                \n" +
		    "  ¢  |         o                     ____         \n" +
		    "  ¢  |  &?     o                                  \n" +
		    "       ¢¢¢    ___    ???     €#€!               % \n" +
		    "→                  %                ____      % ] \n" +
		    "           [[      ]                          ] ] \n" +
		    "+++++   ,,,[[     _]_            o          X ] ] \n" +
		    "+++++  +++++++++++++++++     ++++++++++   ++++++++\n" +
		    "+++++  +++++++++++++++++     +++++++++++ +++++++++\n" +
		    "+++++  +++++++++++++++++     +++++++++++++++++++++";
  public static final String testEnemies =
    "                                                  \n" +
    "                                                  \n" +
    "                                                  \n" +
    "                                                  \n" +
    "                                                  \n" +
    "                                                  \n" +
    "                                                  \n" +
    "                K     s           R               \n" +
    "                                                  \n" +
    "                                                  \n" +
    "                                s                 \n" +
    "         r f  gg k                 R              \n" +
    "                                                  \n" +
    "                                                  \n" +
    "                                                  ";
  public static final String startingPlatform =
		    "      \n" +
		    "      \n" +
		    "      \n" +
		    "      \n" +
		    "      \n" +
		    "      \n" +
		    "      \n" +
		    "      \n" +
		    "      \n" +
		    "→     \n" +
		    "      \n" +
		    "++++ @\n" +
		    "++++++\n" +
		    "++++++\n" +
		    "++++++\n";
  // TODO: Add an anchor to the ending platform.
  public static final String endingPlatform =
    "            \n" +
    "            \n" +
    "            \n" +
    "            \n" +
    "            \n" +
    "            \n" +
    "            \n" +
    "            \n" +
    "            \n" +
    "            \n" +
    "            \n" +
    "     X      \n" +
    "++++++++++++\n" +
    "++++++++++++\n" +
    "++++++++++++\n";

  public static void init() {
    Debug.init();
    Hierarchy.addSpriteMappings();
  }

  public static void buildLevel(Level level, Case components) {
    for (Component c : components) {
      Generator.placeComponent(level, c);
    }
  }

  public static void buildTestLevel(Level level) {
    Case testCase = Case.readCase(Generator.testLevel, Generator.testEnemies);
    Generator.buildLevel(level, testCase);
    Generator.fixWorld(level, testCase);
  }

  public static void buildCustomLevel(Level level) {
    Case customCase = Library.readCaseFile("custom.lvl").get(0);
    Generator.buildLevel(level, customCase);
    Generator.fixWorld(level, customCase);
  }

  public static void buildDebugLevel(Level level) {
    GlobCase sofar;
    sofar = new GlobCase(Case.readBlocks(Generator.startingPlatform));
    sofar.anchorRandom();
    outer:
    for (Case caice : Library.getCases()) {
      for (AnchoredCase ac : AnchoredCase.variantsOf(caice)) {
        AnchoredCase result = Library.compatible(sofar, ac);
        Debug.debug("DEBUG", "Level:");
        Debug.debug("DEBUG", sofar.repr());
        Debug.debug("DEBUG", "Case:");
        Debug.debug("DEBUG", ac.repr());
        Debug.debug("DEBUG", "Compatible:");
        if (result != null) {
          Debug.debug("DEBUG", result.repr());
        } else {
          Debug.debug("DEBUG", "<no result>");
        }
        if (!Debug.promptContinue("DEBUG")) {
          break outer;
        }
      }
    }
    //sofar.integrate(caice);
    //sofar.trim(level.getWidth(), level.getHeight());
  }

  public static void buildCaseLevel( Level level, GamePlay trace, int shellQuota, int coinBlockQuota, int gapQuota) {
    GlobCase sofar;
    AnchoredCase query, caice;
    SkillJudge estimate = SkillJudge.judge(trace);
    boolean lasttime = false;
    sofar = new GlobCase(Case.readBlocks(Generator.startingPlatform));
    sofar.anchorRandom();
    for (Component c : Case.readBlocks(Generator.endingPlatform)) {
      sofar.add(new Component(
        c.type,
        c.x + level.getWidth() - Generator.endingPlatform.indexOf('\n'),
        c.y
      ));
    }
    int placed = 0;
    while (true) {
      Debug.debug("Build", "Anchors: " + sofar.anchorsLeft());
      if (!sofar.hasUnusedAnchors()) {
        Debug.debug("Build", "Out of unused anchors!");
        sofar.reset();
        continue;
      }
      sofar.anchorRandom();
      if (sofar.getAnchor().x >
          level.getWidth() - Generator.endingPlatform.indexOf('\n') - 4) {
        break;
      }
      // TODO: Query selection!
      // query = ...
      caice = Library.getCase(
        sofar,
        level.getHeight() - 1,
        0,
        0,
        level.getWidth() - 1
      );
      // TODO: Remove this hack:
      if (caice == null || !caice.isAnchored()) {
        if (sofar.anchorsLeft() < 3) {
          Debug.debug("Build", "No match found, generalizing...");
          int ax = sofar.getAnchor().x;
          int ay = sofar.getAnchor().y;
          ax++;
          while (true) {
            if (sofar.get(ay, ay, ax, ax).size() == 0) {
              sofar.add(new Component(Hierarchy.PotentialPosition, ax, ay));
              lasttime = true;
              break;
            }
            ax++;
          }
          continue;
        } else {
          Debug.debug("Build", "No match found, skipping...");
          sofar.setUsed();
          continue;
        }
      } else {
        lasttime = false;
      }
      sofar.integrate(caice);
      placed++;
      sofar.trim(level.getWidth(), level.getHeight());
      Debug.debug("Build", "Used:\n" + caice.repr());
      Debug.debug("Build", "Level:\n" + sofar.repr());
      /*
      if (!Debug.promptContinue("Build")) {
        break;
      }
      */
    }
    Debug.debug("Build", "Successfully placed " + placed + " cases.");

    Generator.adjustComponents(sofar, estimate, shellQuota, coinBlockQuota);
    Generator.buildLevel(level, sofar);
    Generator.fixWorld(level, sofar);
    Generator.mindGaps(level, gapQuota);
    Generator.meetQuotas(level, shellQuota, coinBlockQuota);
  }

  /**
   * Adjusts the components in the given case so that enemies and powerups are
   * properly distributed.
   */
  public static void adjustComponents(
    Case caice,
    SkillJudge estimate,
    int shellQuota,
    int coinBlockQuota
  ) {
    double enemyProb = 0.5, enemyIncrement = 0.2, enemyFloor = 0;
    double spawnProb = 0, spawnIncrement = 0.1, spawnFloor = 0;
    double powerupProb = 0.2, powerupIncrement = 0.02, powerupFloor = -1;
    double wingProb = 0.3, wingIncrement = 0.1, wingFloor = 0;
    boolean isShelled = false;
    int shells = 0;
    int coinBlocks = 0;

    // Adjustments based on skill:
    if (estimate.walker) {
      System.out.println("Walker.");
      enemyIncrement = 0.1;
      spawnIncrement = 0;
      powerupProb = 0.4;
      powerupIncrement = 0.04;
      powerupFloor = -0.8;
      wingProb = 0.05;
      wingIncrement = 0.03;
      wingFloor = -0.5;
    }

    if (estimate.kicker) {
      System.out.println("Kicker.");
      enemyIncrement += 0.05;
    }

    if (estimate.shapechanger) {
      System.out.println("Shapechanger.");
      powerupIncrement += 0.03;
      powerupFloor += 0.1;
    }

    Set<Component> toremove = new HashSet<Component>();
    for (int x = caice.minx; x < caice.maxx; ++x) {
      Set<Component> here = caice.get(caice.height, 0, x, x);
      for (Component c : here) {
        if (c.isInstance(Hierarchy.Enemy)) {
          if (
            c.isInstance(Hierarchy.GreenKoopa) ||
            c.isInstance(Hierarchy.RedKoopa) ||
            c.isInstance(Hierarchy.SpikeShell)
          ) {
            isShelled = true;
          } else {
            isShelled = false;
          }
          if (c.isInstance(Hierarchy.WingedEnemy)) {
            if (Math.random() > wingProb) {
              toremove.add(c);
              wingProb += wingIncrement;
            } else {
              wingProb = wingFloor;
              if (isShelled) {
                if (shells >= shellQuota) {
                  toremove.add(c);
                } else {
                  shells += 1;
                }
              }
            }
          } else {
            if (Math.random() > enemyProb) {
              toremove.add(c);
              enemyProb += enemyIncrement;
            } else {
              enemyProb = enemyFloor;
              if (isShelled) {
                if (shells >= shellQuota) {
                  toremove.add(c);
                } else {
                  shells += 1;
                }
              }
            }
          }
        } else if (c.isInstance(Hierarchy.EnemySpawn)) {
          if (Math.random() > spawnProb) {
            toremove.add(c);
            spawnProb += spawnIncrement;
          } else {
            spawnProb = spawnFloor;
          }
        } else if (
          c.isInstance(Hierarchy.PowerupQuestion) ||
          c.isInstance(Hierarchy.PowerupBrick)
        ) {
          if (Math.random() > powerupProb) {
            toremove.add(c);
            powerupProb += powerupIncrement;
          } else {
            powerupProb = powerupFloor;
          }
        } else if (
          c.isInstance(Hierarchy.CoinQuestion) ||
          c.isInstance(Hierarchy.CoinBrick)
        ) {
          if (coinBlocks < coinBlockQuota) {
            coinBlocks += 1;
          } else {
            toremove.add(c);
          }
        }
      }
      enemyProb += enemyIncrement;
      wingProb += wingIncrement;
      spawnProb += spawnIncrement;
      powerupProb += powerupIncrement;
    }
    for (Component c : toremove) {
      caice.remove(c);
      if (c.isInstance(Hierarchy.BulletCannon)) {
        for (Component o : caice.get(caice.maxy, c.y, c.x, c.x)) {
          if (o.isInstance(Hierarchy.BulletTower)
           || o.isInstance(Hierarchy.BulletCannon)) {
            caice.remove(o);
          }
        }
      } else if (c.isInstance(Hierarchy.Block)) {
        if (coinBlocks < coinBlockQuota) {
          caice.add(new Component(Hierarchy.CoinQuestion, c.x, c.y));
          coinBlocks += 1;
        } else {
          caice.add(new Component(Hierarchy.EmptyBrick, c.x, c.y));
        }
      }
    }
  }

  // TODO: Use the level data instead of iterating over components!
  public static void fixWorld(Level level, Case components) {
    int t, b, l, r;
    List<Component> subsumed = new ArrayList<Component>();
    Component[][] grid = new Component[level.getWidth()][level.getHeight()];
    for (Component c : components) {
      if (c.x >= 0 && c.x < level.getWidth() &&
          c.y >= 0 && c.y < level.getHeight()) {
        grid[c.x][c.y] = c;
      }
    }
    for (int x = components.maxx + 1; x < level.getWidth(); ++x) {
      for (int y = 0; y < level.getHeight(); ++y) {
        grid[x][y] = new Component(Hierarchy.LevelEdge, x, y);
      }
    }
    for (int x = 0; x < components.minx; ++x) {
      for (int y = 0; y < level.getHeight(); ++y) {
        grid[x][y] = new Component(Hierarchy.LevelEdge, x, y);
      }
    }
    for (int y = components.maxy + 1; y < level.getHeight(); ++y) {
      for (int x = 0; x < level.getWidth(); ++x) {
        grid[x][y] = new Component(Hierarchy.LevelEdge, x, y);
      }
    }
    for (int y = 0; y < components.miny; ++y) {
      for (int x = 0; x < level.getWidth(); ++x) {
        grid[x][y] = new Component(Hierarchy.LevelEdge, x, y);
      }
    }

    // TODO: What would ignoring subsumption look like?
    for (Component c : components) {
      if (subsumed.contains(c) ||
          !c.isInstance(Hierarchy.Platform) ||
          c.x < 0 || c.y < 0) {
        continue;
      }
      /*
      if (!c.isInstance(Hierarchy.Platform) ||
          c.x < 0 || c.y < 0) {
        continue;
      }
      */
      t = c.y;
      b = c.y;
      l = c.x;
      r = c.x;
      if (c.isInstance(Hierarchy.Ledge)) {
        if (c.isInstance(Hierarchy.ExtendsLeft)) {
          l -= AUTOEXPAND_LEDGE;
        }
        if (c.isInstance(Hierarchy.ExtendsRight)) {
          r += AUTOEXPAND_LEDGE;
        }
      } else if (c.isInstance(Hierarchy.Block)) {
        if (c.isInstance(Hierarchy.ExtendsLeft)) {
          l -= AUTOEXPAND_BLOCK;
        }
        if (c.isInstance(Hierarchy.ExtendsRight)) {
          r += AUTOEXPAND_BLOCK;
        }
      } else if (c.isInstance(Hierarchy.Ground)) {
        if (c.isInstance(Hierarchy.ExtendsUp)) {
          t += AUTOEXPAND_GROUND;
        }
        if (c.isInstance(Hierarchy.ExtendsDown)) {
          b -= AUTOEXPAND_GROUND;
        }
        if (c.isInstance(Hierarchy.ExtendsLeft)) {
          l -= AUTOEXPAND_GROUND;
        }
        if (c.isInstance(Hierarchy.ExtendsRight)) {
          r += AUTOEXPAND_GROUND;
        }
      }

      subsumed.addAll(Generator.expandInto(level, grid, c, t, b, l, r));
    }
  }

  private static List<Component> expandInto(
    Level level,
    Component[][] grid,
    Component c,
    int t,
    int b,
    int l,
    int r
  ) {
    t = Math.min(t, level.getHeight() - 1);
    b = Math.max(b, 0);
    l = Math.max(l, 0);
    r = Math.min(r, level.getWidth() - 1);
    List<Component> subsumed = new ArrayList<Component>(8);
    ComponentType typ = Hierarchy.Platform;
    byte fillwith = 0;
    if (c.isInstance(Hierarchy.Ledge)) {
      typ = Hierarchy.Ledge;
      fillwith = (byte) Hierarchy.Ledge.spriteIndex;
    } else if (c.isInstance(Hierarchy.Block)) {
      typ = Hierarchy.Block;
      fillwith = (byte) Hierarchy.EmptyBrick.spriteIndex;
    } else if (c.isInstance(Hierarchy.Ground)) {
      typ = Hierarchy.Ground;
      fillwith = (byte) Hierarchy.NormalGround.spriteIndex;
    }
    if (fillwith < 0) {
      fillwith += 256;
    }
    // First loop: reduce the constraints as much as necessary:
    for (int ox = l; ox <= r; ++ox) {
      for (int oy = b; oy <= t; ++oy) {
        if ((b > oy) || (oy > t) || (l > ox) || (ox > r)) {
          continue;
        }
        Component o = grid[ox][oy];
        if (o != null && o.isInstance(Hierarchy.LevelEdge)) {
          continue;
        } else if (o != null && o.isInstance(typ)) {
          if (o.isInstance(Hierarchy.EdgeLeft)) {
            if ((l < ox) && (ox < c.x)) {
              l = ox;
              assert(l <= c.x);
            } else if (ox > c.x && ox <= r) {
              if (oy > c.y && oy - 1 < t) {
                t = oy - 1;
                assert(t >= c.y);
              } else if (oy < c.y && oy + 1 > b) {
                b = oy + 1;
                assert(b <= c.y);
              } else {
                r = Math.max(c.x, ox - EDGE_BACKOFF);
                assert(r >= c.x);
              }
            }
          }
          if ((b > oy) || (oy > t) || (l > ox) || (ox > r)) {
            continue;
          }
          if (o.isInstance(Hierarchy.EdgeRight)) {
            if (ox > c.x && ox < r) {
              r = ox;
              assert(r >= c.x);
            } else if (ox < c.x && ox >= l) {
              if (oy > c.y && oy - 1 < t) {
                t = oy - 1;
                assert(t >= c.y);
              } else if (oy < c.y && oy + 1 > b) {
                b = oy + 1;
                assert(b <= c.y);
              } else {
                l = Math.min(c.x, ox + EDGE_BACKOFF);
                assert(l <= c.x);
              }
            }
          }
          if ((b > oy) || (oy > t) || (l > ox) || (ox > r)) {
            continue;
          }
          if (o.isInstance(Hierarchy.EdgeDown)) {
            if (oy < c.y && oy > b) {
              b = oy;
              assert(b <= c.y);
            } else if (oy > c.y && oy <= t) {
              if (ox > c.x && ox - 1 < r) {
                r = ox - 1;
                assert(r >= c.x);
              } else if (ox < c.x && oy + 1 > l) {
                l = ox + 1;
                assert(l <= c.x);
              } else {
                t = Math.max(c.y, oy - EDGE_BACKOFF);
                assert(t >= c.y);
              }
            }
          }
          if ((b > oy) || (oy > t) || (l > ox) || (ox > r)) {
            continue;
          }
          if (o.isInstance(Hierarchy.EdgeUp)) {
            if (oy > c.y && oy < t) {
              t = oy;
              assert(t >= c.y);
            } else if (oy < c.y && oy >= b) {
              if (ox > c.x && ox - 1 < r) {
                r = ox - 1;
                assert(r >= c.x);
              } else if (ox < c.x && oy + 1 > l) {
                l = ox + 1;
                assert(l <= c.x);
              } else {
                b = Math.min(c.y, oy + EDGE_BACKOFF);
                assert(b <= c.y);
              }
            }
          }
        } else if (o != null) {
          if ((oy < c.y) && (oy + 1 > b)) {
            b = oy + 1;
            assert(b <= c.y);
          }
          if ((b > oy) || (oy > t) || (l > ox) || (ox > r)) {
            continue;
          }
          if ((oy > c.y) && (oy - 1 < t)) {
            t = oy - 1;
            assert(t >= c.y);
          }
          if ((b > oy) || (oy > t) || (l > ox) || (ox > r)) {
            continue;
          }
          if ((ox < c.x) && (ox + 1 > l)) {
            l = ox + 1;
            assert(l <= c.x);
          }
          if ((b > oy) || (oy > t) || (l > ox) || (ox > r)) {
            continue;
          }
          if ((ox > c.x) && (ox - 1 < r)) {
            r = ox - 1;
            assert(r >= c.x);
          }
          if ((b > oy) || (oy > t) || (l > ox) || (ox > r)) {
            continue;
          }
        }
      }
    }
    if (t != c.y || b != c.y || l != c.x || r != c.x) {
      Debug.debug(
        "Expand",
        "" + c + " expanded into: " + t + ", " + b + ", " + l + ", " + r
      );
    }
    assert(t >= c.y);
    assert(b <= c.y);
    assert(l <= c.x);
    assert(r >= c.x);
    /*
    if ((t - b < 2 || r - l < 2) && c.isInstance(Hierarchy.NormalGround)) {
      Debug.debug("Expand", "DESTROYING: " + c + " :: " + c.type);
      level.setBlock(c.x, c.y, (byte) 0);
      grid[c.x][c.y] = null;
      return subsumed;
    }
    */
    // Second loop: fill in the fully constrained area, and keep track of
    // subsumption.
    for (int ox = l; ox <= r; ++ox) {
      for (int oy = b; oy <= t; ++oy) {
        if (ox == c.x && oy == c.y) {
          continue;
        }
        if (grid[ox][oy] == null ||
            grid[ox][oy].isInstance(Hierarchy.LevelEdge)) {
          level.setBlock(ox, oy, fillwith);
          grid[ox][oy] = new Component(typ, ox, oy);
        } else {
          subsumed.add(grid[ox][oy]);
        }
      }
    }
    return subsumed;
  }

  private static void mindGaps(Level level, int gapQuota) {
    boolean isGap = false;
    boolean inGap = false;
    int nGaps = 0;
    byte fixwith = (byte) Hierarchy.NormalGround.spriteIndex;
    Map<Integer, ComponentType> types = MarioComponentType.knownSpriteIndexes;
    for (int x = 0; x < level.getWidth(); ++x) {
      isGap = true;
      for (int y = 0; y < level.getHeight(); ++y) {
        if (Generator.isSolid(level, x, y)) {
          isGap = false;
          break;
        }
      }
      if (isGap) {
        if (nGaps < gapQuota) {
          if (!inGap) {
            nGaps += 1;
            inGap = true;
          }
        } else {
          level.setBlock(x - 1, level.getHeight() - 1, fixwith);
          level.setBlock(x - 1, level.getHeight() - 2, fixwith);
          level.setBlock(x, level.getHeight() - 1, fixwith);
          level.setBlock(x, level.getHeight() - 2, fixwith);
          level.setBlock(x + 1, level.getHeight() - 1, fixwith);
          level.setBlock(x + 1, level.getHeight() - 2, fixwith);
        }
      } else {
        inGap = false;
      }
    }
    holepunch:
    while (nGaps < gapQuota) {
      boolean[] gaps = { true, true, true, true, true, true, true };
      nGaps++;
      int gx = 12 + (int) (Math.random() * (level.getWidth() - 24));
      int spriteType = 0;
      for (int y = 0; y < level.getHeight(); ++y) {
        for (int ox = 0; ox < 7; ++ox) {
          spriteType = (int) level.getBlock(gx + ox, y);
          if (spriteType < 0) {
            spriteType += 256;
          }
          if (
            types.containsKey(spriteType) &&
            (
              types.get(spriteType).isSubtype(Hierarchy.Ledge) ||
              types.get(spriteType).isSubtype(Hierarchy.Pipe)
            )
          ) {
            continue holepunch;
          }
          if (Generator.isSolid(level, gx + ox, y)) {
            gaps[ox] = false;
          }
        }
      }
      for (int ox = 0; ox < 7; ++ox) {
        if (gaps[ox]) {
          continue holepunch;
        }
      }
      for (int y = 0; y < level.getHeight(); ++y) {
        level.setBlock(gx + 2, y, (byte) 0);
        level.setBlock(gx + 3, y, (byte) 0);
        level.setBlock(gx + 4, y, (byte) 0);
      }
      
    }
  }

  private static void meetQuotas(
    Level level,
    int shellQuota,
    int coinBlockQuota
  ) {
    int nGaps = 0;
    int nShells = 0;
    int nCoinBlocks = 0;
    int nx = 0, ny = 0;
    int koopa = (byte) Hierarchy.RedKoopa.enemyType;
    byte coinBlock = (byte) Hierarchy.CoinQuestion.spriteIndex;
    byte fixwith = (byte) Hierarchy.RockBlock.spriteIndex;
    Map<Integer, ComponentType> types = MarioComponentType.knownSpriteIndexes;
    for (int x = 0; x < level.getWidth(); ++x) {
      for (int y = 0; y < level.getHeight(); ++y) {
        int spriteType = (int) level.getBlock(x, y);
        if (spriteType < 0) {
          spriteType += 256;
        }
        if (
          types.containsKey(spriteType) &&
          (
            types.get(spriteType).isSubtype(Hierarchy.CoinQuestion) ||
            types.get(spriteType).isSubtype(Hierarchy.CoinBrick)
          )
        ) {
          if (nCoinBlocks < coinBlockQuota) {
            nCoinBlocks += 1;
          } else {
            level.setBlock(x, y, fixwith);
          }
        }
        SpriteTemplate tmp = level.getSpriteTemplate(x, y);
        if (tmp != null) {
          int enemyType = tmp.type;
          if (
            enemyType == Hierarchy.RED_KOOPA ||
            enemyType == Hierarchy.GREEN_KOOPA ||
            enemyType == Hierarchy.SPIKY
          ) {
            if (nShells < shellQuota) {
              nShells += 1;
            } else {
              level.setSpriteTemplate(x, y, null);
            }
          }
        }
      }
    }
    while (nShells < shellQuota) {
      nx = (int) (Math.random() * (level.getWidth() - 12));
      for (ny = 0; ny < level.getHeight(); ++ny) {
        if (Generator.isSolid(level, nx, ny)) {
          for (; ny > 0; --ny) {
            if (
              level.getBlock(nx, ny) == 0 &&
              level.getSpriteTemplate(nx, ny) == null
            ) {
              level.setSpriteTemplate(nx, ny, new SpriteTemplate(koopa, false));
              nShells += 1;
              break;
            }
          }
          break;
        }
      }
    }
    while (nCoinBlocks < coinBlockQuota) {
      nx = (int) (Math.random() * (level.getWidth() - 12));
      for (ny = 0; ny < level.getHeight(); ++ny) {
        if (Generator.isSolid(level, nx, ny)) {
          ny -= 4;
          for (; ny > 0; --ny) {
            if (
              level.getBlock(nx, ny) == 0 &&
              level.getSpriteTemplate(nx, ny) == null
            ) {
              level.setBlock(nx, ny, coinBlock);
              nCoinBlocks += 1;
              break;
            }
          }
          break;
        }
      }
    }
  }

  private static boolean isSolid(Level level, int x, int y) {
    int spriteType = (int) level.getBlock(x, y);
    if (spriteType < 0) {
      spriteType += 256;
    }
    Map<Integer, ComponentType> types = MarioComponentType.knownSpriteIndexes;
    if (
      types.containsKey(spriteType) &&
      (
        types.get(spriteType).isSubtype(Hierarchy.Platform) ||
        types.get(spriteType).isSubtype(Hierarchy.EnemySpawn)
      )
    ) {
      return true;
    }
    return false;
  }

  public static void placeComponent(Level level, Component c) {
    if (!MarioComponentType.class.isAssignableFrom(c.type.getClass())) {
      Debug.debug("Place", "Non-Mario component " + c + " not placed.");
      return;
    }
    MarioComponentType mct = (MarioComponentType) c.type;
    if (mct.spriteIndex >= 0) {
      if (level.getBlock(c.x, c.y) != (byte) 0) {
        Debug.debug("Place", "Overwrote block at (" + c.x + ", " + c.y + ").");
      }
      level.setBlock(c.x, c.y, (byte) mct.spriteIndex);
    } else if (mct.enemyType >= 0) {
      if (level.getSpriteTemplate(c.x, c.y) != null) {
        Debug.debug("Place", "Overwrote enemy at (" + c.x + ", " + c.y + ").");
      }
      level.setSpriteTemplate(
        c.x, c.y,
        new SpriteTemplate(
          mct.enemyType,
          mct.spriteIndex == -2
        )
      );
    }
  }
}
