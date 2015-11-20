package petermawhorter.cases;

import java.util.List;
import java.util.ArrayList;

import petermawhorter.Debug;

import petermawhorter.components.Component;

import petermawhorter.cases.Case;

public class AnchoredCase extends Case {

  protected Component anchor = null;
  public Case base = null;

  public AnchoredCase() {
    super();
    this.anchor = null;
    this.base = null;
  }

  public AnchoredCase(Iterable<Component> components) {
    super(components);
    this.anchor = null;
    this.base = null;
    if (Case.class.isAssignableFrom(components.getClass())) {
      this.base = (Case) components;
      for (String t : this.base.tags.keySet()) {
        this.tags.put(t, this.base.tags.get(t));
      }
    }
    if (this.hasAnchors()) {
      this.anchor = this.anchors.get(0);
    }
  }

  public AnchoredCase(Iterable<Component> components, int anchorIndex) {
    super(components);
    this.anchor = null;
    this.base = null;
    if (Case.class.isAssignableFrom(components.getClass())) {
      this.base = (Case) components;
      for (String t : this.base.tags.keySet()) {
        this.tags.put(t, this.base.tags.get(t));
      }
    }
    if (this.anchors.size() > anchorIndex) {
      this.anchor = this.anchors.get(anchorIndex);
    }
  }

  public AnchoredCase(AnchoredCase from) {
    super(from);
    this.anchor = from.anchor;
    this.base = from.base;
    for (String t : from.tags.keySet()) {
      this.tags.put(t, from.tags.get(t));
    }
  }

  @Override
  public void remove(Component c) {
    if (c == this.anchor) {
      this.anchor = null;
    }
    super.remove(c);
  }

  @Override
  public String repr() {
    return super.repr() + this.anchor + "\n";
  }

  public Component getAnchor() {
    return this.anchor;
  }

  public void setAnchor(int anchorIndex) {
    if (this.anchors.size() > anchorIndex) {
      this.anchor = this.anchors.get(anchorIndex);
    } else {
      this.anchor = null;
    }
  }

  public void setAnchor(Component newAnchor) {
    if (this.anchors.contains(newAnchor)) {
      this.anchor = newAnchor;
      return;
    }
    for (Component c : this.anchors) {
      if (c.x == newAnchor.x && c.y == newAnchor.y) {
        this.anchor = c;
        return;
      }
    }
    this.anchor = null;
  }

  public boolean isAnchored() {
    return this.anchor != null;
  }

  public boolean hasAnchors() {
    return this.anchors.size() > 0;
  }

  public void anchorNext() {
    int idx = -1;
    if (!this.hasAnchors()) {
      this.anchor = null;
      return;
    }
    if (!this.isAnchored()) {
      this.anchor = this.anchors.get(0);
      return;
    }
    idx = this.anchors.indexOf(this.anchor) + 1;
    if (idx >= this.anchors.size()) {
      idx = 0;
    }
    this.anchor = this.anchors.get(idx);
  }

  public void anchorRandom() {
    int idx = -1;
    if (!this.hasAnchors()) {
      this.anchor = null;
      return;
    }
    idx = (int) (Math.random() * this.anchors.size());
    this.anchor = this.anchors.get(idx);
  }

  public static List<AnchoredCase> variantsOf(Case initial) {
    List<AnchoredCase> result = new ArrayList<AnchoredCase>();
    AnchoredCase var;
    Debug.debug("Variants", "Initial case:\n" + initial.repr());
    for (int i = 0; i < initial.anchors.size(); ++i) {
      var = new AnchoredCase(initial, i);
      result.add(var);
      Debug.debug("Variants", "Variant case:\n" + var.repr());
    }
    return result;
  }
}
