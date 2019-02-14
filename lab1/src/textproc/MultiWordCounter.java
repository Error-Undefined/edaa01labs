package textproc;

import java.util.HashMap;
import java.util.Map;

public class MultiWordCounter implements TextProcessor {

	private Map<String, Integer> m = new HashMap<String, Integer>();

	public MultiWordCounter(String[] s) {
		for (int i = 0; i < s.length; i++) {
			m.put(s[i], 0);
		}
	}

	public void process(String w) {
		if (m.containsKey(w)) {
			m.replace(w, m.get(w) + 1);
		}
	}

	public void report() {
		for (String k : m.keySet()) {
			System.out.println(k + ": " + m.get(k));
		}
	}

}
