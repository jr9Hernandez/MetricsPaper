package petermawhorter.components;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import petermawhorter.components.Component;
import petermawhorter.components.ComponentType;

import petermawhorter.components.Hierarchy;

import dk.itu.mario.level.Level;
import dk.itu.mario.engine.sprites.SpriteTemplate;


public class MarioComponentType extends ComponentType {

  public int spriteIndex = -1;
  public int enemyType = -1;

  public static Map<Integer, ComponentType> knownSpriteIndexes =
    new HashMap<Integer, ComponentType>();
  public static Map<Integer, ComponentType> knownEnemyTypes =
    new HashMap<Integer, ComponentType>();

  private void construct(int spriteIndex, int enemyType) {
    this.spriteIndex = spriteIndex;
    this.enemyType = enemyType;
    if (spriteIndex > 0 &&
        !MarioComponentType.knownSpriteIndexes.containsKey(spriteIndex)) {
      MarioComponentType.knownSpriteIndexes.put(spriteIndex, this);
    }
    if (enemyType > 0 &&
        !MarioComponentType.knownEnemyTypes.containsKey(enemyType)) {
      MarioComponentType.knownEnemyTypes.put(enemyType, this);
    }
  }

  public MarioComponentType(
    char repr,
    int spriteIndex,
    int enemyType
  ) {
    super(repr);
    this.construct(spriteIndex, enemyType);
  }

  public MarioComponentType(
    char repr,
    int spriteIndex,
    int enemyType,
    ComponentType parent
  ) {
    super(repr, parent);
    this.construct(spriteIndex, enemyType);
  }

  public MarioComponentType(
    char repr,
    int spriteIndex,
    int enemyType,
    List<ComponentType> parents
  ) {
    super(repr, parents);
    this.construct(spriteIndex, enemyType);
  }

  public static ComponentType mixed(
    ComponentType type,
    List<ComponentType> types
  ) {
    List<ComponentType> newtypes = new ArrayList<ComponentType>(types);
    newtypes.add(type);
    newtypes.add(ComponentType.Mixed);
    if (MarioComponentType.class.isAssignableFrom(type.getClass())) {
      MarioComponentType mtype = (MarioComponentType) type;
      return new MarioComponentType(
        '\'',
        mtype.spriteIndex,
        mtype.enemyType,
        newtypes
      );
    } else {
      return new ComponentType('\'', newtypes);
    }
  }

  public static ComponentType mixed(char type, String types) {
    ComponentType typ = ComponentType.charMap.get(type);
    List<ComponentType> typs = new ArrayList<ComponentType>();
    for (char t : types.toCharArray()) {
      typs.add(ComponentType.charMap.get(t));
    }
    return MarioComponentType.mixed(typ, typs);
  }

  public static ComponentType with(
    ComponentType type,
    ComponentType include
  ) {
    List<ComponentType> newtypes = new ArrayList<ComponentType>(type.parents);
    newtypes.add(type);
    newtypes.add(include);
    newtypes.add(ComponentType.Mixed);
    if (MarioComponentType.class.isAssignableFrom(type.getClass())) {
      MarioComponentType mtype = (MarioComponentType) type;
      return new MarioComponentType(
        '\'',
        mtype.spriteIndex,
        mtype.enemyType,
        newtypes
      );
    } else {
      return new ComponentType('\'', newtypes);
    }
  }

  public static ComponentType without(
    ComponentType type,
    ComponentType exclude
  ) {
    List<ComponentType> newtypes = new ArrayList<ComponentType>(type.parents);
    newtypes.add(type);
    newtypes.add(ComponentType.Mixed);
    newtypes.remove(exclude);
    if (MarioComponentType.class.isAssignableFrom(type.getClass())) {
      MarioComponentType mtype = (MarioComponentType) type;
      return new MarioComponentType(
        '\'',
        mtype.spriteIndex,
        mtype.enemyType,
        newtypes
      );
    } else {
      return new ComponentType('\'', newtypes);
    }
  }

}
