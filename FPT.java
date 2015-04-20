
/**
 * The purpose of this program is to replicate the Frequent Pattern Trees
 * algorithm to generate the combinations of highest frequency in the given data
 * set.
 *
 * @author Daniel Swain Jr
 *
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class FPT {

  private FrequentPatternTreeNode root;
  private HashMap<String, LinkedList<FrequentPatternTreeNode>> node_link = new HashMap<>();
  private ArrayList<String> combinations = new ArrayList<>();
  private ArrayList<String> tree = new ArrayList<>();

  public FPT() {
    root = null;
  }

  public void run(int threshold) {
    File data_file = new File("dataset.csv");
    ArrayList<int[]> data_set = new ArrayList<>();
    HashMap<String, Integer> values = new HashMap<>();

    try (BufferedReader br = new BufferedReader(new FileReader(data_file))) {
      String line;

      while ((line = br.readLine()) != null) {

        if (!line.startsWith("N")) {
          int[] record = new int[7];
          String[] s = line.split(",");

          if (s.length == 0) {
            break;
          }

          for (int i = 0; i < s.length; i++) {
            record[i] = Integer.parseInt(s[i]);
            String name = s[i] + " " + i;

            if (!values.containsKey(name)) {
            	node_link.put(name, new LinkedList<>());
              values.put(name, 1);
            } else {
              values.put(name, values.get(name) + 1);
            }
          }

          data_set.add(record);
        }
      }
    } catch (IOException ex) {
      System.out.println("Error: File(" + data_file.getName() + ") could not be read. ");
    }

    for (int[] record : data_set) {
      String[] keys = new String[record.length];

      for (int i = 0; i < record.length; i++) {
        keys[i] = record[i] + " " + i;
      }

      for (int i = 0; i < record.length; i++) {
        for (int j = i + 1; j < record.length - 1; j++) {
          if (values.get(keys[i]) < values.get(keys[j])) {
            String temp = keys[i];
            keys[i] = keys[j];
            keys[j] = temp;
          }
        }
      }
      insert(keys);
    }
    
    for(Map.Entry<String, LinkedList<FrequentPatternTreeNode>> entry : node_link.entrySet()) {
      initialize_node_link(entry.getKey(), root);
    }
    
    print(root, "", threshold, "");

    for (String s : combinations) {
      System.out.println(s);
    }

    for (String s : tree) {
      System.out.println(s);
    }
  }

  private void initialize_node_link(String name, FrequentPatternTreeNode F){
  	if (F.get_children() != null) {
      for (Map.Entry<FrequentPatternTreeNode, Integer> entry : F.get_children().entrySet()) {
        if (entry.getKey().get_name().equals(name)) {
        	node_link.get(name).add(entry.getKey());
        }
        initialize_node_link(name, entry.getKey());
      }
    }
  }

  private void insert(String[] record) {
    if (root == null) {
      root = new FrequentPatternTreeNode(null);
      root.set_name("");
      insert(root, record);
    } else {
      insert(root, record);
    }
  }

  private void insert(FrequentPatternTreeNode F, String[] S) {
    String[] substr;

    if (S.length > 1) {
      substr = Arrays.copyOfRange(S, 1, S.length);
    } else {
      substr = new String[]{S[0]};
    }

    if (F.get_children() == null) {
      FrequentPatternTreeNode newNode = new FrequentPatternTreeNode(null);
      newNode.set_name(S[0]);
      F.set_new_child(newNode);

      if (substr.length > 1) {
        insert(newNode, substr);
      }
    } else {
      FrequentPatternTreeNode lookup = F.find_node(S[0]);

      if (lookup != null) {
        F.increment_child_frequency(lookup);
      } else {
        lookup = new FrequentPatternTreeNode(null);
        lookup.set_name(S[0]);
        F.set_new_child(lookup);
      }

      if (substr.length > 1) {
        insert(lookup, substr);
      }
    }
  }

  private void print(FrequentPatternTreeNode F, String tabs, int threshold, String combo) {
    if (F.get_children() != null) {
      for (Map.Entry<FrequentPatternTreeNode, Integer> entry : F.get_children().entrySet()) {
        if (entry.getValue() >= threshold) {
          tree.add(tabs + "[" + entry.getKey().get_name() + "]:" + entry.getValue());
          if (combo.length() > 6) { // Size of a string for a single combination
            combinations.add("[ " + combo + " (" + entry.getKey().get_name() + "): " + entry.getValue() + " ]");
          }
          print(entry.getKey(), tabs + "\t", threshold, combo + " (" + entry.getKey().get_name() + ") ");
        }
      }
    }
  }
}

class FrequentPatternTreeNode {

  private HashMap<FrequentPatternTreeNode, Integer> children;
  private String name;

  public FrequentPatternTreeNode(HashMap<FrequentPatternTreeNode, Integer> c) {
    children = c;
  }

  public HashMap<FrequentPatternTreeNode, Integer> get_children() {
    return children;
  }

  public String get_name() {
    return name;
  }

  public void set_name(String n) {
    name = n;
  }

  public void set_new_child(FrequentPatternTreeNode f) {
    if (children == null) {
      children = new HashMap<>();
    }

    if (!children.containsKey(f)) {
      children.put(f, 1);
    } else {
      System.out.println("Error: 'Key (" + f.get_name() + ") already exists in node (" + get_name() + ").' ");
    }
  }

  public void increment_child_frequency(FrequentPatternTreeNode f) {
    if (children.containsKey(f)) {
      children.put(f, children.get(f) + 1);
    } else {
      System.out.println("Error: 'Key (" + f.get_name() + ") in node (" + get_name() + ") does not exist.' ");
    }
  }

  public FrequentPatternTreeNode find_node(String name) {
    for (Map.Entry<FrequentPatternTreeNode, Integer> entry : children.entrySet()) {
      if (entry.getKey().get_name().equals(name)) {
        return entry.getKey();
      }
    }
    return null;
  }
}
