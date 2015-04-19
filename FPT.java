
/**
 * The purpose of this program is to replicate the Frequent Pattern Trees
 * algorithm to generate the combinations of highest frequency in the given data
 * set.
 *
 * @author Daniel Swain Jr
 *
 */

import java.util.HashMap;

public class FPT {

}

class FrequentPatternTreeNode {

  private HashMap<FrequentPatternTreeNode, Integer> children;
  private String name;

  public FrequentPatternTreeNode(HashMap<FrequentPatternTreeNode, Integer> c) {
    children = c;
  }

  public HashMap get_children() {
    return children;
  }

  public int get_frequency() {
    int sum = 0;
    sum = children.entrySet().stream().map((child) -> child.getValue()).reduce(sum, Integer::sum);
    return sum;
  }
  
  public String get_name() {
    return name;
  }
  
  public void set_name(String n) {
    name = n;
  }

  public void set_new_child(FrequentPatternTreeNode f) {
    if (!children.containsKey(f)) {
      children.put(f, 0);
    } else {
      System.out.println("Error: 'Key (" + f.get_name() + ") already exists in node (" + get_name() + ").' ");
    }
  }
  
  public void increment_child_frequency(FrequentPatternTreeNode f) {
    if(children.containsKey(f)) {
      children.put(f, children.get(f) + 1);
    } else {
      System.out.println("Error: 'Key (" + f.get_name() + ") in node (" + get_name() + ") does not exist.' ");
    }
  }
}
