package textproc;

import java.util.*;
import java.util.Map.Entry;

public class WordCountComparator implements Comparator<Map.Entry<String, Integer>> {

	public int compare(Entry<String, Integer> arg0, Entry<String, Integer> arg1) {
		if (arg1.getValue() - arg0.getValue() != 0) {
			return arg1.getValue() - arg0.getValue();
		} else {
			return arg0.getKey().compareToIgnoreCase(arg1.getKey());
		}
	}

}
