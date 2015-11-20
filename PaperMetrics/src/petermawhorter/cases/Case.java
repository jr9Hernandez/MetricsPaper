package petermawhorter.cases;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;

import petermawhorter.Debug;

import petermawhorter.components.Hierarchy;
import petermawhorter.components.Component;
import petermawhorter.components.ComponentType;
import petermawhorter.components.MarioComponentType;

import dk.itu.mario.level.Level;
import dk.itu.mario.engine.sprites.SpriteTemplate;

public class Case implements Iterable<Component> {

  protected Set<Component> components;
  protected List<Component> anchors;
  protected Map<String, Double> tags;
  public int minx = Integer.MAX_VALUE, maxx = Integer.MIN_VALUE,
             miny = Integer.MAX_VALUE, maxy = Integer.MIN_VALUE;
  public int height, width;

  public Case() {
    this.components = new HashSet<Component>();
    this.anchors = new ArrayList<Component>(8);
    this.tags = new HashMap<String, Double>();
  }

  public Case(Iterable<Component> components) {
    this.components = new HashSet<Component>();
    this.anchors = new ArrayList<Component>(8);
    this.tags = new HashMap<String, Double>();
    for (Component c : components) {
      this.add(c);
    }
  }

  public void add(Component c) {
    if (c == null) { return; }
    if (c.isInstance(Hierarchy.PotentialPosition)) {
      this.anchors.add(c);
    }
    this.components.add(c);
    if (c.x < this.minx) { this.minx = c.x; }
    if (c.x > this.maxx) { this.maxx = c.x; }
    if (c.y < this.miny) { this.miny = c.y; }
    if (c.y > this.maxy) { this.maxy = c.y; }
    this.width = this.maxx - this.minx + 1;
    this.height = this.maxy - this.miny + 1;
  }

  public void remove(Component c) {
    this.components.remove(c);
    this.anchors.remove(c);
    this.expand();
  }

  public Set<Component> get(int t, int b, int l, int r) {
    Set<Component> result = new HashSet<Component>();
    for (Component c : this.components) {
      if (c.x >= l && c.x <= r && c.y >= b && c.y <= t) {
        result.add(c);
      }
    }
    return result;
  }

  public void tag(String tag, Double value) {
    this.tags.put(tag, value);
  }

  public double value(String tag) {
    if (this.tags.containsKey(tag)) {
      return this.tags.get(tag);
    }
    return 0;
  }

  public Set<String> tagSet() {
    return this.tags.keySet();
  }

  public Iterator<Component> iterator() {
    return this.components.iterator();
  }

  public int size() {
    return this.components.size();
  }

  public String toString() {
    return this.components.toString();
  }

  protected void expand() {
    this.minx = Integer.MAX_VALUE;
    this.maxx = Integer.MIN_VALUE;
    this.miny = Integer.MAX_VALUE;
    this.maxy = Integer.MIN_VALUE;
    for (Component c : this.components) {
      if (c.x < this.minx) { this.minx = c.x; }
      if (c.x > this.maxx) { this.maxx = c.x; }
      if (c.y < this.miny) { this.miny = c.y; }
      if (c.y > this.maxy) { this.maxy = c.y; }
    }
    this.width = this.maxx - this.minx + 1;
    this.height = this.maxy - this.miny + 1;
  }

  public boolean[][] platformGrid() {
    if (this.components.size() < 1) {
      return null;
    }
    boolean[][] result = new boolean[width][height];
    for (int x = 0; x < this.width; ++x) {
      for (int y = 0; y < this.height; ++y) {
        result[x][y] = false;
      }
    }
    for (Component c : this.components) {
      if (c.isInstance(Hierarchy.Platform)
       || c.isInstance(Hierarchy.LevelEdge)) {
        result[c.x - this.minx][c.y - this.miny] = true;
      }
    }
    return result;
  }

  public Component[][] componentGrid() {
    if (this.components.size() < 1) {
      return null;
    }
    Component[][] result = new Component[width][height];
    for (int x = 0; x < this.width; ++x) {
      for (int y = 0; y < this.height; ++y) {
        result[x][y] = null;
      }
    }
    for (Component c : this.components) {
      result[c.x - this.minx][c.y - this.miny] = c;
    }
    return result;
  }

  public String repr() {
    Component[][] grid = this.componentGrid();
    String result = "";
    for (int y = 0; y < this.height; ++y) {
      for (int x = 0; x < this.width; ++x) {
        if (grid[x][y] != null) {
          result += grid[x][y].type.repr;
        } else {
          result += " ";
        }
      }
      result += "\n";
    }
    if (this.tags.size() > 0) {
      result += "Tags:\n";
      for (String t : this.tags.keySet()) {
        result += t + ": " + this.value(t) + "\n";
      }
    }
    return result;
  }

  public void trim(int width, int height) {
    this.trim(height - 1, 0, 0, width - 1);
  }

  public void trim(int t, int b, int l, int r) {
    List<Component> outofbounds = new ArrayList<Component>();
    for (Component c : this.components) {
      if (c.x < l || c.x > r || c.y < b || c.y > t) {
        outofbounds.add(c);
      }
    }
    for (Component c : outofbounds) {
      this.remove(c);
    }
  }

  public static Case extractCase(Level level) {
    Hierarchy.addSpriteMappings();
    Case caice = new Case();
    for (int x = 0; x < level.getWidth(); ++x) {
      for (int y = 0; y < level.getHeight(); ++y) {
        int tile = (int) level.getBlock(x, y);
        if (tile < 0) {
          tile += 256;
        }
        if (MarioComponentType.knownSpriteIndexes.containsKey(tile)) {
          ComponentType type =
            MarioComponentType.knownSpriteIndexes.get(tile);
          caice.add(new Component(type, x, y));
        } else if (tile != 0) {
          Debug.debug("Extract", "Couldn't identify tile: " + tile);
        }
        SpriteTemplate enemy = level.getSpriteTemplate(x, y);
        if (enemy != null &&
            MarioComponentType.knownEnemyTypes.containsKey(enemy.type)) {
          ComponentType type =
            MarioComponentType.knownEnemyTypes.get(enemy.type);
          caice.add(new Component(type, x, y));
        }
      }
    }
    return Case.refineCase(caice);
  }

  public static Case readCase(String blocks, String enemies) {
    int x = 0, y = 0;
    int t = Integer.MIN_VALUE, b = Integer.MAX_VALUE;
    int l = Integer.MAX_VALUE, r = Integer.MIN_VALUE;
    Case caice = new Case();
    for (char c : blocks.toCharArray()) {
      if (c != '\n') {
        if (x < l) { l = x; }
        if (x > r) { r = x; }
        if (y < b) { b = y; }
        if (y > t) { t = y; }
      }
      if (ComponentType.charMap.containsKey(c)) {
        ComponentType type = ComponentType.charMap.get(c);
        caice.add(new Component(type, x, y));
      } else if (c == '\n') {
        y += 1;
        x = 0;
        continue;
      }
      x += 1;
    }
    // Add edges to help during refinement...
    for (int ex = l - 1; ex <= r + 1; ++ex) {
      for (int ey = b - 1; ey <= t + 1; ++ey) {
        if (ex == l - 1
         || ey == b - 1
         || ex == r + 1
         || ey == t + 1) {
          caice.add(new Component(Hierarchy.LevelEdge, ex, ey));
        }
      }
    }
    x = 0;
    y = 0;
    for (char c : enemies.toCharArray()) {
      if (ComponentType.charMap.containsKey(c)) {
        ComponentType type = ComponentType.charMap.get(c);
        caice.add(new Component(type, x, y));
      } else if (c == '\n') {
        y += 1;
        x = 0;
        continue;
      }
      x += 1;
    }
    return Case.refineCase(caice);
  }

  public static Case readBlocks(String blocks) {
    String noenemies = new String();
    for (int i = 0; i < blocks.length(); ++i) {
      if (blocks.charAt(i) == '\n') {
        noenemies += "\n";
      } else {
        noenemies += " ";
      }
    }
    return Case.readCase(blocks, noenemies);
  }

  public static Case refineCase(Case caice) {
    int x = 0, y = 0;
    boolean[][] grid = caice.platformGrid();
    Case result = new Case();

    Debug.debug("Refine", "Refining case:");
    Debug.debug("Refine", caice.repr());
    for (Component c : caice) {
      ComponentType type = null;
      List<ComponentType> types = new ArrayList<ComponentType>();
      Debug.debug("Refine", "Refining component: " + c);
      int cx = c.x - caice.minx;
      int cy = c.y - caice.miny;
      Debug.debug(
        "Refine",
        "  normal/grid position: (" + c.x + ", " + c.y + ")/(" + cx +
          ", " + cy + ")"
      );
      if (grid[cx][cy]) {
        if (cy + 1 >= caice.height || grid[cx][cy+1]) {
          types.add(Hierarchy.ExtendsUp);
        } else {
          types.add(Hierarchy.EdgeUp);
        }
        if (cy - 1 < 0 || grid[cx][cy-1]) {
          types.add(Hierarchy.ExtendsDown);
        } else {
          types.add(Hierarchy.EdgeDown);
        }
        if (cx - 1 < 0 || grid[cx-1][cy]) {
          types.add(Hierarchy.ExtendsLeft);
        } else {
          types.add(Hierarchy.EdgeLeft);
        }
        if (cx + 1 >= caice.width || grid[cx+1][cy]) {
          types.add(Hierarchy.ExtendsRight);
        } else {
          types.add(Hierarchy.EdgeRight);
        }
      }
      if (types.size() > 0) {
        type = MarioComponentType.mixed(c.type, types);
        Debug.debug("Refine", "  result type: " + type);
        c = new Component(type, c.x, c.y);
      }
      if (!c.isInstance(Hierarchy.Ephemeral) &&
          !c.isInstance(Hierarchy.LevelEdge)) {
        result.add(c);
      }
    }
    return result;
  }
}
