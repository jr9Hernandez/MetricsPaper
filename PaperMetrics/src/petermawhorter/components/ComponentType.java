package petermawhorter.components;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;

public class ComponentType {

  public List<ComponentType> parents = null;

  public char repr;

  public static Map<Character, ComponentType> charMap =
    new HashMap<Character, ComponentType>();

  //public static final ComponentType Meta = new ComponentType('Ã¢â€¢Â¬');
  public static final ComponentType Meta = new ComponentType('╬');
  public static final ComponentType Mixed = new ComponentType('\''); 

  public ComponentType(char repr) {
    this.repr = repr;
    this.parents = new ArrayList<ComponentType>();
    ComponentType.charMap.put(repr, this);
  }

  public ComponentType(char repr, ComponentType parent) {
    this.repr = repr;
    this.parents = new ArrayList<ComponentType>();
    this.parents.add(parent);
    ComponentType.charMap.put(repr, this);
  }

  public ComponentType(char repr, List<ComponentType> parents) {
    this.repr = repr;
    this.parents = parents;
    ComponentType.charMap.put(repr, this);
  }

  public ComponentType(
    char repr,
    List<ComponentType> parents,
    List<ComponentType> children
  ) {
    this.repr = repr;
    this.parents = parents;
    for (ComponentType c : children) {
      if (!c.parents.contains(this)) {
        c.parents.add(this);
      }
    }
    ComponentType.charMap.put(repr, this);
  }

  public String toString() {
    List<ComponentType> ts = this.types();
    char[] b = new char[ts.size()];
    int i = 0;
    for (ComponentType t : ts) {
      b[i++] = t.repr;
    }
    return new String(b);
  }

  /**
   * Returns a list of unique ComponentTypes that are ancestors of this
   * ComponentType, plus this ComponentType itself. The list is roughly ordered
   * from least to most specific, with the current ComponentType at the end.
   */
  public List<ComponentType> types() {
    List<ComponentType> result = new ArrayList<ComponentType>();
    for (ComponentType c : this.parents) {
      for (ComponentType typ : c.types()) {
        if (!result.contains(typ)) {
          result.add(typ);
        }
      }
    }
    result.add(this);
    return result;
  }

  public boolean isSubtype(ComponentType ancestor) {
    if (this == ancestor) {
      return true;
    }
    for (ComponentType type : this.parents) {
      if (type.isSubtype(ancestor)) {
        return true;
      }
    }
    return false;
  }

  public boolean matches(ComponentType other) {
    Set<ComponentType> mytypes = new HashSet<ComponentType>(this.types());
    Set<ComponentType> othertypes = new HashSet<ComponentType>(other.types());
    Set<ComponentType> incommon = new HashSet<ComponentType>(mytypes);
    Set<ComponentType> differing = new HashSet<ComponentType>(mytypes);
    incommon.retainAll(othertypes);
    differing.addAll(othertypes);
    differing.removeAll(incommon);
    for (ComponentType ct : differing) {
      if (!ct.isSubtype(ComponentType.Meta)
       && !ct.isSubtype(ComponentType.Mixed)) {
        return false;
      }
    }
    if (incommon.size() > 0) {
      return true;
    }
    return false;
  }

  public boolean matchesExactly(ComponentType other) {
    return this.toString().equals(other.toString());
  }
}
