package petermawhorter;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;

import java.lang.Math;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import java.nio.charset.Charset;

import petermawhorter.cases.AnchoredCase;
import petermawhorter.cases.Case;
import petermawhorter.components.Component;
import petermawhorter.components.ComponentType;
import petermawhorter.components.Hierarchy;

import dk.itu.mario.res.ResourcesManager;

public class Library {
  private Library() {}

  private static final int SIZE_ESTIMATE = 1024;
  private static final int SMALL_SIZE_ESTIMATE = 64;
  private static final int NBEST = 17;
  private static final int BORED_NUMBER = 3;
  private static final double BORED_ADJUST = 0.7;
  private static final double PRECISION_BIAS = 0.2;
  private static final int TOP_BUFFER = 2;

  private static String[] CASE_FILES = { "cases.simple", "cases.various" };
  //private static String[] CASE_FILES = { "cases.simple" };
  //private static String[] CASE_FILES = { "cases.various" };

  private static String[] TAGS = { "precise", "frequency" };

  private static List<Case> cases;
  private static Map<Case, Integer> boredom;

  public static List<Case> readCaseFile(String filename) {
    boolean istag = false;
    double val;
    String[] split;
    String tiles = new String();
    String enemies = new String();
    String line;
    Case caice;
    Map<String, Double> tags;
    List<Case> result = new ArrayList<Case>(Library.SIZE_ESTIMATE);
    BufferedReader reader = new BufferedReader(
      new InputStreamReader(
        ResourcesManager.class.getResourceAsStream("res/" + filename),
        Charset.forName("utf-8")
      )
    );

    try {
      line = reader.readLine();
    } catch (IOException e) {
      System.err.println("Failed to read case file '" + filename + "'.");
      return result;
    }
    tags = new HashMap<String, Double>();
    while (line != null) {
      if (line.equals("")) {
        caice = Case.readCase(tiles, enemies);
        for (String t : tags.keySet()) {
          caice.tag(t, tags.get(t));
        }
        result.add(caice);
        Debug.debug("Parse", "Added case with tags:");
        for (String t : Library.TAGS) {
          Debug.debug("Parse", "'" + t + "': " + caice.value(t));
        }
        tiles = "";
        enemies = "";
        tags = new HashMap<String, Double>();
      } else {
        istag = false;
        for (String t : Library.TAGS) {
          if (line.startsWith(t)) {
            istag = true;
            val = Double.parseDouble(line.substring(t.length() + 1));
            tags.put(t, val);
            Debug.debug("Parse", "Found tag '" + t + "' with value: " + val);
          }
        }
        if (!istag) {
          split = line.split("\\\\");
          tiles += split[0] + "\n";
          enemies += split[1] + "\n";
        }
      }
      try {
        line = reader.readLine();
      } catch (IOException e) {
        System.err.println("Failed to read case file '" + filename + "'.");
        return result;
      }
    }
    if (tiles != "") {
      result.add(Case.readCase(tiles, enemies));
    }

    return result;
  }

  public static void init() {
    // Set up the case array and the boredom map:
    Library.cases = new ArrayList<Case>(Library.SIZE_ESTIMATE);
    Library.boredom = new HashMap<Case, Integer>();

    for (String fn : Library.CASE_FILES) {
      Library.cases.addAll(readCaseFile(fn));
    }

    for (Case caice : Library.cases) {
      Library.boredom.put(caice, 0);
    }
  }

  public static List<Case> getCases() {
    return Library.cases;
  }

  public static void addCase(Case caice) {
    Library.cases.add(caice);
  }

  public static AnchoredCase getCase(
    AnchoredCase query,
    int t,
    int b,
    int l,
    int r
  ) {
    List<AnchoredCase> options = new ArrayList<AnchoredCase>();
    int i, qx, qy;
    AnchoredCase chosen;
    // TODO: Use component filtering?
    for (Case caice : Library.cases) {
      AnchoredCase modified = null;
      for (AnchoredCase anchored : AnchoredCase.variantsOf(caice)) {
        Debug.debug("Query", "Testing case:\n" + anchored.repr());
        modified = Library.compatible(query, anchored);
        if (modified != null) {
          for (Component c : modified) {
            qx = c.x - modified.getAnchor().x + query.getAnchor().x;
            qy = c.y - modified.getAnchor().y + query.getAnchor().y;
            if (qx < l || qx > r || qy < b + Library.TOP_BUFFER || qy > t) {
              Debug.debug(
                "Query",
                "Case violates boundaries:\n" + modified.repr()
              );
              modified = null;
              break;
            }
          }
          if (modified != null) {
            Debug.debug("Query", "Case compatible:\n" + modified.repr());
            options.add(modified);
          }
        } else {
          Debug.debug("Query", "Case incompatible.");
        }
      }
      if (options.size() >= Library.NBEST) {
        break;
      }
    }
    if (options.size() > 0) {
      double weight, totalweight = 0, choice, sofar;
      // Compute the total weight:
      for (AnchoredCase a : options) {
        totalweight += Library.getWeight(a);
      }
      Debug.debug(
        "Weights", "Total weight: " + totalweight + " for " + options.size() +
        " cases."
      );
      // Select a case:
      sofar = 0;
      choice = Math.random();
      chosen = null;
      for (AnchoredCase a : options) {
        sofar += Library.getWeight(a) / totalweight;
        if (sofar > choice) {
          chosen = a;
          break;
        }
      }
      if (chosen == null) {
        chosen = options.get(options.size() - 1);
      }
      if (chosen.base != null) {
        Library.boredom.put(chosen.base, Library.boredom.get(chosen.base) + 1);
      }
      return chosen;
    }
    Debug.debug("Query", "Case query produced no results.");
    return null;
  }

  /**
   * Performs matching and adaption: returns either a case based on the given
   * caice that is compatible with the query, or null if the given case is a
   * lost cause for adaption.
   */
  public static AnchoredCase compatible(
    AnchoredCase query,
    AnchoredCase caice
  ) {
    // TODO: Something about downward ledges and compatibility!
    int ncheck, qx, qy, qox, qoy;
    AnchoredCase result = new AnchoredCase();
    result.base = caice.base;
    for (String t : caice.tagSet()) {
      result.tag(t, caice.value(t));
    }
    Component queryAnchor = query.getAnchor();
    Component caseAnchor = caice.getAnchor();
    Component q = null;
    // Add the case anchor to the result, and anchor there:
    result.add(caseAnchor);
    result.anchorNext();
    Debug.debug("Compatibility", "Checking compatibility.");
    for (Component c : caice) {
      qx = c.x - caseAnchor.x + queryAnchor.x;
      qy = c.y - caseAnchor.y + queryAnchor.y;
      Debug.debug(
        "Compatibility",
        "Checking component " + c + " at (" + qx + ", " + qy + ")."
      );
      ncheck = Library.checkNeighborhood(c, caice, query, qx, qy);
      if (ncheck < 0) {
        Debug.debug("Compatibility", "  Incompatible neighborhood.");
        return null;
      } else if (ncheck > 0) {
        Debug.debug("Compatibility", "  Neighborhood is compatible.");
        result.add(c);
      } else {
        Debug.debug("Compatibility", "  Absorbed by neighborhood.");
      }
    }
    if (result.size() > 1) {
      return result;
    }
    // No new Components would be generated (size 1 is just the anchor)...
    return null;
  }

  private static int checkNeighborhood(
    Component c,
    Case caice,
    Case query,
    int qx,
    int qy
  ){
    // TODO: Conflict with ground that extends towards you...
    int t, b, l, r, cx, cy, score;
    boolean extendsUp, extendsDown, extendsLeft, extendsRight;
    Component avoid;
    Debug.debug(
      "Neighborhood",
      "Checking neighborhood of " + c + " at (" + qx + ", " + qy + ")"
    );
    Set<Component> considering = query.get(qy, qy, qx, qx);
    score = 0;
    for (Component qc : considering) {
      if (qc.matches(c)) {
        Debug.debug("Neighborhood", "  Perfect match!");
        return 0;
      } else {
        Debug.debug("Neighborhood", "  Conflict.");
        score--;
      }
    }
    if (score < 0) {
      Debug.debug("Neighborhood", "  Conflict killed match.");
      return -1;
    }
    Debug.debug("Neighborhood", "  No exact match...");
    t = qy + 1;
    b = qy - 1;
    l = qx - 1;
    r = qx + 1;
    extendsUp = c.isInstance(Hierarchy.ExtendsUp);
    extendsDown = c.isInstance(Hierarchy.ExtendsDown);
    extendsLeft = c.isInstance(Hierarchy.ExtendsLeft);
    extendsRight = c.isInstance(Hierarchy.ExtendsRight);
    if (c.isInstance(Hierarchy.Platform)) {
      // TODO: More stringent type compatibility requirements for platforms?
      Debug.debug("Neighborhood", "  Expanding neighborhood for platform...");
      t = qy + 2;
      b = qy - 2;
      l = qx - 2;
      r = qx + 2;
      // Above:
      Debug.debug("Neighborhood", "  Checking neighbors above...");
      considering = query.get(t, qy + 1, l, r);
      outer:
      for (Component qc : considering) {
        cx = qc.x - qx + c.x;
        cy = qc.y - qy + c.y;
        for (Component oc : caice.get(cy, cy, cx, cx)) {
          if (qc.matchesExactly(oc)) {
            Debug.debug(
              "Neighborhood",
              "  Ignoring matched overlapping neighbor: " + qc
            );
            continue;
          }
        }
        if (qc.isInstance(Hierarchy.Platform)) {
          if (platformIncompatible(c, qx, qy, qc, "up")) {
            Debug.debug("Neighborhood", "    Platform incompatible: " + qc);
            return -1;
          }
        } else if (qc.x == qx && extendsUp) {
          Debug.debug("Neighborhood", "    Conflict with neighbor: " + qc);
          return -1;
        }
      }
      // Below:
      Debug.debug("Neighborhood", "  Checking neighbors below...");
      considering = query.get(qy - 1, b, l, r);
      for (Component qc : considering) {
        cx = qc.x - qx + c.x;
        cy = qc.y - qy + c.y;
        for (Component oc : caice.get(cy, cy, cx, cx)) {
          if (qc.matchesExactly(oc)) {
            Debug.debug(
              "Neighborhood",
              "  Ignoring matched overlapping neighbor: " + qc
            );
            continue;
          }
        }
        if (qc.isInstance(Hierarchy.Platform)) {
          if (platformIncompatible(c, qx, qy, qc, "down")) {
            Debug.debug("Neighborhood", "    Platform incompatible: " + qc);
            return -1;
          }
        } else if (qc.x == qx && extendsDown) {
          Debug.debug("Neighborhood", "    Conflict with neighbor: " + qc);
          return -1;
        }
      }
      // Left:
      Debug.debug("Neighborhood", "  Checking neighbors to the left...");
      considering = query.get(t, b, l, qx - 1);
      for (Component qc : considering) {
        cx = qc.x - qx + c.x;
        cy = qc.y - qy + c.y;
        for (Component oc : caice.get(cy, cy, cx, cx)) {
          if (qc.matchesExactly(oc)) {
            Debug.debug(
              "Neighborhood",
              "  Ignoring matched overlapping neighbor: " + qc
            );
            continue;
          }
        }
        if (qc.isInstance(Hierarchy.Platform)) {
          if (platformIncompatible(c, qx, qy, qc, "left")) {
            Debug.debug("Neighborhood", "    Platform incompatible: " + qc);
            return -1;
          }
        } else if (qc.y == qy && extendsLeft) {
          Debug.debug("Neighborhood", "    Conflict with neighbor: " + qc);
          return -1;
        }
      }
      // Right:
      Debug.debug("Neighborhood", "  Checking neighbors to the right...");
      considering = query.get(t, b, qx + 1, r);
      for (Component qc : considering) {
        cx = qc.x - qx + c.x;
        cy = qc.y - qy + c.y;
        for (Component oc : caice.get(cy, cy, cx, cx)) {
          if (qc.matchesExactly(oc)) {
            Debug.debug(
              "Neighborhood",
              "  Ignoring matched overlapping neighbor: " + qc
            );
            continue;
          }
        }
        if (qc.isInstance(Hierarchy.Platform)) {
          if (platformIncompatible(c, qx, qy, qc, "right")) {
            Debug.debug("Neighborhood", "    Platform incompatible: " + qc);
            return -1;
          }
        } else if (qc.y == qy && extendsRight) {
          Debug.debug("Neighborhood", "    Conflict with neighbor: " + qc);
          return -1;
        }
      }
      Debug.debug("Neighborhood", "  Done checking platform neighbors.");
    } else {
      Debug.debug("Neighborhood", "  Checking all neighbors...");
      considering = query.get(t, b, l, r);
      for (Component qc : considering) {
        cx = qc.x - qx + c.x;
        cy = qc.y - qy + c.y;
        for (Component oc : caice.get(cy, cy, cx, cx)) {
          if (qc.matchesExactly(oc)) {
            Debug.debug(
              "Neighborhood",
              "  Ignoring matched overlapping neighbor: " + qc
            );
            continue;
          }
        }
        if (qc.matches(c)) {
          Debug.debug("Neighborhood", "  Close enough to subsume.");
          return 0;
        }
      }
      Debug.debug("Neighborhood", "  Neighborhood is clear.");
    }
    Debug.debug("Neighborhood", "Neighborhood compatible!");
    return 1;
  }

  private static boolean platformIncompatible(
    Component c,
    int qx, int qy,
    Component qc,
    String dir
  ) {
    int cparallel = 0, corthogonal = 0, qparallel = 0, qorthogonal = 0;
    int forward = 0;
    ComponentType parallelForward = null, parallelBack = null;
    ComponentType orthogonalPositive = null, orthogonalNegative = null;
    if (dir.equals("up")) {
      forward = 1;
      cparallel = qy;
      qparallel = qc.y;
      corthogonal = qx;
      qorthogonal = qc.x;
      parallelForward = Hierarchy.ExtendsUp;
      parallelBack = Hierarchy.ExtendsDown;
      orthogonalPositive = Hierarchy.ExtendsRight;
      orthogonalNegative = Hierarchy.ExtendsLeft;
    } else if (dir.equals("down")) {
      forward = -1;
      cparallel = qy;
      qparallel = qc.y;
      corthogonal = qx;
      qorthogonal = qc.x;
      parallelForward = Hierarchy.ExtendsDown;
      parallelBack = Hierarchy.ExtendsUp;
      orthogonalPositive = Hierarchy.ExtendsRight;
      orthogonalNegative = Hierarchy.ExtendsLeft;
    } else if (dir.equals("left")) {
      forward = -1;
      cparallel = qx;
      qparallel = qc.x;
      corthogonal = qy;
      qorthogonal = qc.y;
      parallelForward = Hierarchy.ExtendsLeft;
      parallelBack = Hierarchy.ExtendsRight;
      orthogonalPositive = Hierarchy.ExtendsUp;
      orthogonalNegative = Hierarchy.ExtendsDown;
    } else if (dir.equals("right")) {
      forward = 1;
      cparallel = qx;
      qparallel = qc.x;
      corthogonal = qy;
      qorthogonal = qc.y;
      parallelForward = Hierarchy.ExtendsRight;
      parallelBack = Hierarchy.ExtendsLeft;
      orthogonalPositive = Hierarchy.ExtendsUp;
      orthogonalNegative = Hierarchy.ExtendsDown;
    }
    if (c.isInstance(parallelForward)) {
      // The component had better reciprocate (or form an L):
      if (qc.isInstance(parallelBack)) {
        Debug.debug("Clearance", "  Extension reciprocated.");
      } else {
        Debug.debug("Clearance", "  Extension in conflict...");
        if (qorthogonal < corthogonal && !c.isInstance(orthogonalNegative)) {
          Debug.debug("Clearance", "   ...possible L...");
          if (corthogonal - qorthogonal > 1) {
            Debug.debug("Clearance", "  Safe broken L.");
          } else if (qc.isInstance(orthogonalPositive)) {
            Debug.debug("Clearance", "  Complete L.");
          } else {
            Debug.debug("Clearance", "  Unsafe broken L.");
            return true;
          }
        } else if (qorthogonal > corthogonal
                   && !c.isInstance(orthogonalPositive)){
          Debug.debug("Clearance", "   ...possible L...");
          if (qorthogonal - corthogonal > 1) {
            Debug.debug("Clearance", "  Safe broken L.");
          } else if (qc.isInstance(orthogonalNegative)) {
            Debug.debug("Clearance", "  Complete L.");
          } else {
            Debug.debug("Clearance", "  Unsafe broken L.");
            return true;
          }
        } else {
          Debug.debug("Clearance", "  No conflict resolution.");
          return true;
        }
      }
    } else {
      // The component had better not extend this direction:
      if (!qc.isInstance(parallelBack)) {
        Debug.debug("Clearance", "  Edge reciprocated...");
        if (qparallel == cparallel + forward
            && Math.abs(qorthogonal - corthogonal) <= 1) {
          Debug.debug("Clearance", "   ...but it's too close!");
          return true;
        } else {
          Debug.debug("Clearance", "   ...at a safe distance.");
        }
      } else {
        Debug.debug("Clearance", "  Edge in conflict...");
        if (qorthogonal < corthogonal
            && !qc.isInstance(orthogonalPositive)) {
          Debug.debug("Clearance", "   ...possible L...");
          if (corthogonal - qorthogonal > 1) {
            Debug.debug("Clearance", "  Safe broken L.");
          } else if (c.isInstance(orthogonalNegative)) {
            Debug.debug("Clearance", "  Complete L.");
          } else {
            Debug.debug("Clearance", "  Unsafe broken L.");
            return true;
          }
        } else if (qorthogonal > corthogonal
                   && !qc.isInstance(orthogonalNegative)) {
          Debug.debug("Clearance", "   ...possible L...");
          if (qorthogonal - corthogonal > 1) {
            Debug.debug("Clearance", "  Safe broken L.");
          } else if (c.isInstance(orthogonalPositive)) {
            Debug.debug("Clearance", "  Complete L.");
          } else {
            Debug.debug("Clearance", "  Unsafe broken L.");
            return true;
          }
        } else {
          Debug.debug("Clearance", "  No conflict resolution.");
          return true;
        }
      }
    }
    return false;
  }

  public static double getWeight(AnchoredCase a) {
    int boredom = 0;
    double weight;
    if (a.base != null) {
      boredom = Library.boredom.get(a.base);
    } else {
      Debug.debug("Weights", "Option without a base:\n" + a.repr());
    }
    weight = a.value("frequency") + 1;
    Debug.debug("Weights", "Frequency: " + weight);
    weight *= Math.pow(Library.BORED_ADJUST, boredom);
    Debug.debug(
      "Weights",
      "Boredom bias: " + Math.pow(Library.BORED_ADJUST, boredom)
    );
    weight *= Math.pow(Library.PRECISION_BIAS, a.value("precise"));
    Debug.debug(
      "Weights",
      "Precision bias: " + Math.pow(Library.PRECISION_BIAS, a.value("precise"))
    );
    Debug.debug("Weights", "Computed weight: " + weight);
    return weight;
  }

  public static void test() {
    for (Case c : Library.cases) {
      Debug.debug("Library", "Case:\n" + c.repr() + "\n\n");
    }
  }
}
