package textproc;

public class SingleWordCounter implements TextProcessor {
	
	private String word;
	private int n;

	public SingleWordCounter(String word) {
		this.word = word;
		n = 0;
	}

	public void process(String w) {
		if (w.equals(word)) { // '==' jämför referenser, vi måste använda equals. "a" och "a" blir olika String-objekt.
			n++;
		}
	}

	public void report() {
		System.out.println(word + ": " + n);
	}

}
