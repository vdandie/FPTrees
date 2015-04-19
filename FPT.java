/**
 * The purpose of this program is to replicate the Frequent Pattern Trees algorithm
 * to generate the combinations of highest frequency in the given data set.
 *
 * @author Daniel Swain Jr
 **/

import java.util.HashMap;

public class FPT {

}

class FrequentPatternTreeNode{

  private HashMap<FrequentPatternTreeNode, Integer> children;
  private String name;

  public FrequentPatternTreeNode(HashMap<FrequentPatternTreeNode, Integer> c, int f){
  	this.children = c;
  }

  public HashMap get_children() {
  	return children;
  }

  public void set_new_child(FrequentPatternTreeNode f) {
  	if(!children.containsKey(f)) {
      children.put(f, 0);
    }
  }
}