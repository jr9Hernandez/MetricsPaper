package petermawhorter.components;

import java.lang.Math;
import java.util.List;
import java.util.ArrayList;

import petermawhorter.components.ComponentType;

public class Component {

  public ComponentType type = null;

  public int x = -1;
  public int y = -1;

  public Component(
    ComponentType type,
    int x, int y
  ) {
    this.type = type;
    this.x = x;
    this.y = y;
  }

  public Component(
    char type,
    int x, int y
  ) {
    this(ComponentType.charMap.get(type), x, y);
  }

  public Component(
    List<ComponentType> types,
    int x, int y
  ) {
    this(new ComponentType('\b', types), x, y);
  }

  public Component(
    String types,
    int x, int y
  ) {
    this(new ComponentType('\b'), x, y);
    for (char c : types.toCharArray()) {
      this.type.parents.add(ComponentType.charMap.get(type));
    }
  }

  public boolean isInstance(ComponentType type) {
    return this.type.isSubtype(type);
  }

  public String toString() {
    if (this.type != null) {
      return "" + this.type.repr + "(" + this.x + ", " + this.y + ")";
    } else {
      return "a component with a null type!";
    }
  }

  public boolean matches(Component other) {
    return this.type.matches(other.type);
  }

  public boolean matchesExactly(Component other) {
    return this.type.matchesExactly(other.type);
  }
}
