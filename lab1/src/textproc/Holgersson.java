package textproc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Holgersson {

	public static final String[] REGIONS = { "blekinge", "bohuslän", "dalarna", "dalsland", "gotland", "gästrikland",
			"halland", "hälsingland", "härjedalen", "jämtland", "lappland", "medelpad", "närke", "skåne", "småland",
			"södermanland", "uppland", "värmland", "västerbotten", "västergötland", "västmanland", "ångermanland",
			"öland", "östergötland" };

	public static void main(String[] args) throws FileNotFoundException {

		long t0 = System.nanoTime();

		List<TextProcessor> pList = new ArrayList<TextProcessor>();

		Scanner s = new Scanner(new File("../lab1/nilsholg.txt"));
		s.useDelimiter("(\\s|,|\\.|:|;|!|\\?|'|\\\")+"); // se handledning

		Scanner scan = new Scanner(new File("undantagsord.txt"));

		Set<String> stopwords = new HashSet<String>();
		while (scan.hasNext()) {
			stopwords.add(scan.next());
		}

		scan.close();

		// pList.add(new SingleWordCounter("nils"));
		// pList.add(new SingleWordCounter("norge"));
		// pList.add(new MultiWordCounter(REGIONS));
		pList.add(new GeneralWordCounter(stopwords));

		while (s.hasNext()) {
			String word = s.next().toLowerCase();

			for (TextProcessor w : pList) {
				w.process(word);
			}
		}

		s.close();
		for (TextProcessor w : pList) {
			w.report();
		}
		long t1 = System.nanoTime();

		System.out.println("Exekveringen tog " + (t1 - t0) / 1000000 + " ms");
	}
}