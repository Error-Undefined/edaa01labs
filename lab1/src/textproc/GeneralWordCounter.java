package textproc;

import java.util.*;

public class GeneralWordCounter implements TextProcessor {

	private Set<String> stopwords;
	private Map<String, Integer> m = new TreeMap<String, Integer>();

	public GeneralWordCounter(Set<String> stopwords) {
		this.stopwords = stopwords;
	}

	public void process(String w) {
		if (!m.containsKey(w) && !stopwords.contains(w)) {
			m.put(w, 1);
		} else if (m.containsKey(w)) {
			m.replace(w, m.get(w) + 1);
		}
	}

	public void report() {

		Set<Map.Entry<String, Integer>> wordSet = m.entrySet();
		List<Map.Entry<String, Integer>> wordList = new ArrayList<>(wordSet);

		wordList.sort(new WordCountComparator()); // också som lambdauttryck:
		//(w1, w2) -> w2.getValue() - w1.getValue() != 0 ? w2.getValue() - w1.getValue()
		//: w1.getKey().compareTo(w2.getKey())

		// for (int i = 0; i < 15; i++) {
		// System.out.println(wordList.get(i).getKey() + ": " +
		// wordList.get(i).getValue());
		// }

	}

	public Set<Map.Entry<String, Integer>> getWords() {
		return m.entrySet();
	}

}
