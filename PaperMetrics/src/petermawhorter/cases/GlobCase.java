package petermawhorter.cases;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

import petermawhorter.Debug;
import petermawhorter.components.Component;
import petermawhorter.components.Hierarchy;

public class GlobCase extends AnchoredCase {

  protected Set<Component> usedAnchors;

  public GlobCase() {
    super();
    this.usedAnchors = new HashSet<Component>();
  }

  public GlobCase(Iterable<Component> components) {
    super(components);
    this.usedAnchors = new HashSet<Component>();
  }

  public GlobCase(Iterable<Component> components, int anchorIndex) {
    super(components, anchorIndex);
    this.usedAnchors = new HashSet<Component>();
  }

  public GlobCase(AnchoredCase from) {
    super(from);
    this.usedAnchors = new HashSet<Component>();
  }

  @Override
  public void remove(Component c) {
    if (this.usedAnchors.contains(c)) {
      this.usedAnchors.remove(c);
    }
    super.remove(c);
  }

  @Override
  public void anchorNext() {
    int idx = -1, orig = -1;
    List<Component> filtered = new ArrayList<Component>(this.anchors);
    filtered.removeAll(this.usedAnchors);
    if (filtered.size() < 1) {
      this.anchor = null;
      return;
    }
    if (!this.isAnchored()) {
      this.anchor = filtered.get(0);
      return;
    }
    idx = this.anchors.indexOf(this.anchor);
    orig = idx;
    do {
      ++idx;
      if (idx >= this.anchors.size()) {
        idx = 0;
      }
      this.anchor = this.anchors.get(idx);
    } while (this.usedAnchors.contains(this.anchor) && idx != orig);
    if (idx == orig) {
      this.anchor = null;
    }
  }

  @Override
  public void anchorRandom() {
    List<Component> filtered = new ArrayList<Component>(this.anchors);
    filtered.removeAll(this.usedAnchors);
    if (filtered.size() < 1) {
      this.anchor = null;
      return;
    }
    this.anchor = filtered.get((int) (Math.random() * filtered.size()));
  }

  public void reset() {
    this.usedAnchors.clear();
  }

  public int anchorsLeft() {
    return this.anchors.size() - this.usedAnchors.size();
  }

  public boolean hasUnusedAnchors() {
    for (Component c : this.anchors) {
      if (!this.usedAnchors.contains(c)) {
        return true;
      }
    }
    return false;
  }

  public boolean isStale() {
    return this.isAnchored() && this.usedAnchors.contains(this.anchor);
  }

  public void setUsed() {
    this.usedAnchors.add(this.anchor);
  }

  public void integrate(AnchoredCase other) {
    int tx, ty;
    Component translated;
    Component[][] grid = this.componentGrid();
    assert(this.isAnchored());
    assert(other.isAnchored());
    for (Component c : other) {
      if (c == other.anchor) {
        continue;
      }
      tx = c.x - other.anchor.x + this.anchor.x;
      ty = c.y - other.anchor.y + this.anchor.y;
      translated = new Component(c.type, tx, ty);
      if (tx >= 0 && tx < grid.length && ty >= 0 && ty < grid[0].length) {
        if (grid[tx][ty] != null &&
            (grid[tx][ty].isInstance(Hierarchy.Platform) ||
             grid[tx][ty].isInstance(Hierarchy.Exit))) {
          Debug.debug(
            "Integrate",
            "Dropping conflicting component during merge: " + c
          );
          continue;
        } else if (grid[tx][ty] != null &&
                   c.isInstance(Hierarchy.PotentialPosition) &&
                   grid[tx][ty].isInstance(Hierarchy.PotentialPosition)) {
          Debug.debug(
            "Integrate",
            "Dropping redundant component during merge: " + c
          );
          continue;
        }
      }
      this.add(translated);
    }
    // TODO: Relax/change this?
    this.usedAnchors.add(this.anchor);
  }
}
