package petermawhorter;

import java.util.Map;
import java.util.HashMap;

import java.io.IOException;

public class Debug {
  static Map<String, Boolean> domains = null;

  public static void init() {
    Debug.domains = new HashMap<String, Boolean>();
    Debug.domains.put("Parse", false);
    Debug.domains.put("Extract", false);
    Debug.domains.put("Refine", false);
    Debug.domains.put("Variants", false);
    Debug.domains.put("Build", false);
    Debug.domains.put("Query", false);
    Debug.domains.put("Compatibility", false);
    Debug.domains.put("Neighborhood", false);
    Debug.domains.put("Clearance", false);
    Debug.domains.put("Weights", false);
    Debug.domains.put("Library", false);
    Debug.domains.put("Integrate", false);
    Debug.domains.put("Expand", false);
    Debug.domains.put("Place", false);
    Debug.domains.put("DEBUG", false);
  }

  public static boolean domainActive(String domain) {
    if (Debug.domains == null) {
      System.err.println("<debug system not initialized>");
      return false;
    } else {
      if (Debug.domains.containsKey(domain)) {
        return Debug.domains.get(domain);
      } else {
        System.err.println("(unknown domain)");
        return true;
      }
    }
  }

  public static void debug(String domain, String message) {
    if (Debug.domainActive(domain)) {
      System.err.println(domain + ": " + message);
    }
  }

  public static boolean promptContinue(String domain) {
    System.err.print("Continue (y/n)? ");
    try {
      int c = System.in.read();
      System.in.skip(System.in.available());
      if (c == (int) 'n' || c == (int) 'N') {
        return false;
      }
    } catch (IOException e) {
      return false;
    }
    return true;
  }
}
