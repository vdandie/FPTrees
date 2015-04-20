
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
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class FPT {

  private FrequentPatternTreeNode root;

  public FPT() {
    root = null;
  }

  public void run() {
    File data_file = new File("dataset.csv");
    ArrayList<int[]> data_set = new ArrayList<>();
    HashMap<String, Integer> unsorted_values = new HashMap<>();

    try (BufferedReader br = new BufferedReader(new FileReader(data_file))) {
      String line;
      
      while ((line = br.readLine()) != null) {
        
        if (!line.startsWith("N")) {
          int[] record = new int[7];
          String[] s = line.split(",");
          
          for (int i = 0; i < s.length; i++) {
            record[i] = Integer.parseInt(s[i]);
            String name = s[i] + " " + i;
            
            if (!unsorted_values.containsKey(name)) {
              unsorted_values.put(name, 1);
            } else {
              unsorted_values.put(name, unsorted_values.get(name) + 1);
            }
          }
          
          data_set.add(record);
        }
      }
    } catch (IOException ex) {
      System.out.println("Error: File(" + data_file.getName() + ") could not be read. ");
    }
    
    Map values = sortByValue(unsorted_values);
    System.out.println(values);
    
    for(int[] record : data_set) {
      // Insert the record into the FPT according to sort
    }
  }
  
  public static Map sortByValue(Map unsortedMap) {
		Map sortedMap = new TreeMap(new ValueComparator(unsortedMap));
		sortedMap.putAll(unsortedMap);
		return sortedMap;
	}
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
}

class ValueComparator implements Comparator {
 
	Map map;
 
	public ValueComparator(Map map) {
		this.map = map;
	}
 
	public int compare(Object keyA, Object keyB) {
		Comparable valueA = (Comparable) map.get(keyA);
		Comparable valueB = (Comparable) map.get(keyB);
		return valueB.compareTo(valueA);
	}
}
