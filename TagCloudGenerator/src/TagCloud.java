import java.util.Comparator;

import components.map.Map;
import components.map.Map.Pair;
import components.map.Map1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.sortingmachine.SortingMachine;
import components.sortingmachine.SortingMachine2;

/*
 * Create an HTML tag cloud by utilizing a file specified by the user as input.
 *
 * @author Junbo Chen, Brett Emory
 *
 */
public final class TagCloud {

	/*
	 * Private constructor so this utility class cannot be instantiated.
	 */
	private TagCloud() {
	}

	/*
	 * Arranges the integer values of pairs in a Map in descending order.
	 */
	private static class IntegerSort implements Comparator<Map.Pair<String, Integer>> {
		@Override
		public int compare(Map.Pair<String, Integer> o, Map.Pair<String, Integer> o2) {
			return o2.value().compareTo(o.value());
		}
	}

	/*
	 * Arranges the String keys of a Map in alphabetical order.
	 */
	private static class StringSort implements Comparator<Map.Pair<String, Integer>> {
		@Override
		public int compare(Map.Pair<String, Integer> o, Map.Pair<String, Integer> o2) {
			return o.key().compareTo(o2.key());
		}
	}

	/**
	 * Adds the separators into a set from {@code String} (the separators) into
	 * {@code Set}.
	 *
	 * @param s the given {@code String}
	 * @return set of characters consisting of chars in s
	 * @ensures elements = chars in s
	 *
	 **/
	private static Set<Character> generateSetofSeparators(String s) {
		/*
		 * Set to hold separators. Iterate through the string of separators and add each
		 * unique one to the set.
		 */
		Set<Character> separator = new Set1L<Character>();
		for (int i = 0; i < s.length(); i++) {
			if (!separator.contains(s.charAt(i))) {
				separator.add(s.charAt(i));
			}
		}
		return separator;
	}

	/**
	 * ***Ripped from 2221*** Returns the first "word" (maximal length string of
	 * characters not in {@code separators}) or "separator string" (maximal length
	 * string of characters in {@code separators}) in the given {@code text}
	 * starting at the given {@code position}.
	 *
	 * @param text       the {@code String} from which to get the word or separator
	 *                   string
	 * @param position   the starting index
	 * @param separators the {@code Set} of separator characters
	 * @return the first word or separator string found in {@code text} starting at
	 *         index {@code position}
	 * @requires 0 <= position < |text|
	 * @ensures
	 * 
	 *          <pre>
	 * separators =
	 *   text[position, position + |separators|)  and
	 * if entries(text[position, position + 1)) intersection separators = {}
	 * then
	 *   entries(separators) intersection separators = {}  and
	 *   (position + |separators| = |text|  or
	 *    entries(text[position, position + |separators| + 1))
	 *      intersection separators /= {})
	 * else
	 *   entries(separators) is subset of separators  and
	 *   (position + |separators| = |text|  or
	 *    entries(text[position, position + |separator| + 1))
	 *      is not subset of separators)
	 *          </pre>
	 */
	public static String wordSeparator(String text, int position, Set<Character> separators) {
		assert text != null : "Violation of: text is not null";
		assert separators != null : "Violation of: separators is not null";
		assert 0 <= position : "Violation of: 0 <= position";
		assert position < text.length() : "Violation of: position < |text|";

		// Initialize variables
		String s = "";
		int i = position;
		// Conditional statement to check char at position is in separators set
		if (separators.contains(text.charAt(i))) {
			while (i < text.length() && separators.contains(text.charAt(i))) {
				s += text.charAt(i);
				i++;
			}
		} else {
			while (i < text.length() && !separators.contains(text.charAt(i))) {
				s = s + text.charAt(i);
				i++;
			}
		}
		return s;

	}

	/**
	 * Scans an input file, identifies words based on specified separators, and
	 * creates a map of each word with its count.
	 *
	 * @param mapCount  map that holds words and counts
	 * @param separator set of characters that can separate words
	 * @param in        input file to be counted
	 * @replaces countMap
	 * @ensures
	 * 
	 *          <pre>
	 *  The map contains words extracted from the input, where each key is a unique word, and its corresponding value represents the frequency of its occurrence in the input.
	 *          </pre>
	 * 
	 */
	private static void mapFill(Map<String, Integer> mapCount, Set<Character> separator, SimpleReader in) {
		/*
		 * Clear existing map.
		 */
		mapCount.clear();
		while (in.atEOS() == false) {
			String s = in.nextLine().toLowerCase();
			int pos = 0;
			while (pos < s.length()) {
				String s2 = wordSeparator(s, pos, separator);
				if (!separator.contains(s2.charAt(0))) {
					if (!mapCount.hasKey(s2)) {
						mapCount.add(s2, 1);
					} else {
						int newCount = mapCount.value(s2) + 1;
						mapCount.remove(s2);
						mapCount.add(s2, newCount);
					}
				}
				/*
				 * Update position.
				 */
				pos += s2.length();
			}
		}
	}

	/**
	 * Assigns a font to each word based on its count, using a range of input font
	 * sizes.
	 *
	 * @param count    count of the word that needs a font size
	 * @param maxCount max word count
	 * @param minCount min word count
	 * @requires maxCount =< count =< minCount, maxCount > 0, and minCount > 0
	 * @ensures wordFontSize as defined in a CSS file, is determined by its count
	 *          relative to a specified scale or range
	 * @return the appropriate font size for a word that appears a certain number of
	 *         times, denoted by "count", is determined.
	 */

	private static String wordFontSize(int count, int maxCount, int minCount) {
		/*
		 * Interval.
		 */
		final int maxFont = 48;
		final int minFont = 11;

		// Font size.
		int font = maxFont - minFont;
		font *= (count - minCount);
		font /= (maxCount - minCount);
		font += minFont;
		return "f" + font;
	}

	/**
	 * Generates HTML text for the top n words with the highest frequency from the
	 * countMap, where n is a user-defined value; the selected words are sorted
	 * alphabetically and presented in descending order based on their occurrence
	 * count.
	 * 
	 * @param n        number of highest-count words
	 * @param countMap map of words and counts
	 * @param out      HTML file to print n words to
	 * @clears countMap
	 * @ensures out content = #out content * n words from countMap
	 */
	private static void mapOut(int n, Map<String, Integer> countMap, SimpleWriter out) {
		/*
		 * Sort words numerically by count.
		 */
		Comparator<Pair<String, Integer>> count = new IntegerSort();
		SortingMachine<Map.Pair<String, Integer>> sortCount;
		sortCount = new SortingMachine2<Map.Pair<String, Integer>>(count);
		while (countMap.size() > 0) {
			sortCount.add(countMap.removeAny());
		}
		sortCount.changeToExtractionMode();
		/*
		 * Set up to sort words alphabetically.
		 */
		Comparator<Pair<String, Integer>> alphabeticalOrder = new StringSort();
		SortingMachine<Map.Pair<String, Integer>> sortLetter = new SortingMachine2<Map.Pair<String, Integer>>(
				alphabeticalOrder);
		/*
		 * Retrieve largest count.
		 */
		int highCount = 0;
		if (sortCount.size() > 0) {
			Map.Pair<String, Integer> maxPair = sortCount.removeFirst();
			highCount = maxPair.value();
			sortLetter.add(maxPair);
		}
		/*
		 * Transfer between sorts.
		 */
		int topCounter = 0;
		while (topCounter < n && sortCount.size() > 1) {
			Map.Pair<String, Integer> wordCount = sortCount.removeFirst();
			sortLetter.add(wordCount);
			topCounter++;
		}
		/*
		 * Retrieve smallest count.
		 */
		int lowCount = 0;
		if (sortCount.size() > 0) {
			Map.Pair<String, Integer> minPair = sortCount.removeFirst();
			lowCount = minPair.value();
			sortLetter.add(minPair);
		}
		sortLetter.changeToExtractionMode();
		/*
		 * Get font size for each word and output result.
		 */
		while (sortLetter.size() > 0) {
			Map.Pair<String, Integer> countWord = sortLetter.removeFirst();
			String fontSize = wordFontSize(countWord.value(), highCount, lowCount);
			String tag = "<span style=\"cursor:default\" class=\"" + fontSize + "\" title=\"count: " + countWord.value()
					+ "\">" + countWord.key() + "</span>";
			out.println(tag);
		}
	}

	/**
	 * Opening tags for the HTML file.
	 *
	 * @param input name of the input file to read from
	 * @param n     number of words with highest counts
	 * @param out   output stream
	 * @updates {@code output}
	 * @requires
	 * 
	 *           <pre>
	 * {@code output is open and input is not null}
	 * </pre>
	 * 
	 * @ensures
	 * 
	 *          <pre>
	 * {@code out content = #out content * tags}
	 * </pre>
	 */
	private static void headerOut(String input, int n, SimpleWriter out) {
		assert out != null : "Violation of: output is not null.";
		assert out.isOpen() : "Violation of: output is open.";
		assert input != null : "Violation of: input is not null.";
		/*
		 * Output HTML opening tags/header.
		 */
		out.println("<html><head><title>Top " + n + " words in " + input + "</title>");
		out.println(
				"<link href=\"http://web.cse.ohio-state.edu/software/2231/web-sw2/assignments/projects/tag-cloud-generator/data/tagcloud.css\" rel=\"stylesheet\" type=\"text/css\">");
		out.println("<link href=\"doc/tagcloud.css\" rel=\"stylesheet\" type=\"text/css\">");
		out.println("</head><body><h2>Top " + n + " words in " + input + "</h2>");
		out.println("<hr>");
		out.println("<div class = \"cdiv\">");
		out.println("<p class = \"cbox\">");
	}

	/*
	 * String for the list of possible separators.
	 */
	private static final String SEPARATORS = ". ,:;'{}[]|/<>?!`~1234567890@#$%^&*()-_=+\" ";

	/**
	 * Generate HTML tags for the top 'numWords' words in the file denoted by the
	 * input, and then output them along with the corresponding closing HTML tags.
	 *
	 * @param out HTML output file
	 * @param in  input file
	 * @param n   number of words
	 * @requires n > 0 and input is open
	 * @ensures in.content = #out.content * tags for top n words
	 */
	private static void tagCloudOut(SimpleWriter out, SimpleReader in, int n) {

		/*
		 * A map is used to store each word and its corresponding count.
		 */
		Map<String, Integer> countMap = new Map1L<String, Integer>();
		/*
		 * The program populates the map with words and their counts, and sorts the
		 * entries alphabetically by the word key while maintaining the count value.
		 */
		mapFill(countMap, generateSetofSeparators(SEPARATORS), in);
		mapOut(n, countMap, out);
	}

	/**
	 * Closing tags for the HTML file.
	 *
	 * @param out output stream
	 * @updates {@code output}
	 * @requires
	 * 
	 *           <pre>
	 * {@code output is open}
	 * </pre>
	 * 
	 * @ensures
	 * 
	 *          <pre>
	 * {@code output content = #output content * [the closing tags]}
	 * </pre>
	 */
	private static void footerOut(SimpleWriter out) {
		/*
		 * Output HTML closing tags/footer.
		 */
		out.println("</p></div>");
		out.println("</body></html>");
	}

	/**
	 * Main method.
	 *
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		SimpleReader in = new SimpleReader1L();
		SimpleWriter out = new SimpleWriter1L();
		/*
		 * Prompt user for file name and output file name(s)
		 */
		out.print("Input file name (.txt): ");
		String inputFile = in.nextLine();
		out.print("Output file name (.html): ");
		String outputFile = in.nextLine();
		out.print("Number of words to be in the tag cloud: ");
		int n = in.nextInteger();

		/*
		 * Writer for the HTML output file.
		 */
		SimpleWriter out2 = new SimpleWriter1L(outputFile);
		headerOut(inputFile, n, out2);
		/*
		 * Reader for processing the input file and generating tag cloud.
		 */
		SimpleReader in2 = new SimpleReader1L(inputFile);
		/*
		 * Call methods to generate tag cloud.
		 */
		tagCloudOut(out2, in2, n);
		footerOut(out2);
		/*
		 * Close readers and writers.
		 */
		in2.close();
		out2.close();
		in.close();
		out.close();
	}
}