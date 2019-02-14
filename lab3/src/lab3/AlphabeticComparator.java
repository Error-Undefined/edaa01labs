package lab3;

import java.util.Comparator;
import java.util.Map.Entry;

public class AlphabeticComparator implements Comparator<Entry<String, Integer>> {

	@Override
	public int compare(Entry<String, Integer> arg0, Entry<String, Integer> arg1) {
		return arg0.getKey().compareTo(arg1.getKey());
	}

}
