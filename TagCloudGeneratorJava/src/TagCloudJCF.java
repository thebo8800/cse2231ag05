import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * Create an HTML tag cloud by utilizing a file specified by the user as input.
 *
 * @author Junbo Chen, Brett Emory
 *
 */
public final class TagCloudJCF {

    /*
     * Private constructor so this utility class cannot be instantiated.
     */
    private TagCloudJCF() {
    }

    /*
     * Arranges the integer getValues of entries in a Map in descending order.
     */
    private static class IntegerSort
            implements Comparator<Map.Entry<String, Integer>> {
        @Override
        public int compare(Map.Entry<String, Integer> o,
                Map.Entry<String, Integer> o2) {
            return o2.getValue().compareTo(o.getValue());
        }
    }

    /*
     * Arranges the String keys of a Map in alphabetical order.
     */
    private static class StringSort
            implements Comparator<Map.Entry<String, Integer>> {
        @Override
        public int compare(Map.Entry<String, Integer> o,
                Map.Entry<String, Integer> o2) {
            return o.getKey().compareTo(o2.getKey());
        }
    }

    //Global string of all the possible separators that can be apart of an input file
    private static final String SEPARATORS = "'., ()-_?\"/!@#$%^&*\t1234567890:"
            + ";[]{}+=~`><";

    /**
     * ***Ripped from 2221*** Returns the first "word" (maximal length string of
     * characters not in {@code separators}) or "separator string" (maximal
     * length string of characters in {@code separators}) in the given
     * {@code text} starting at the given {@code position}.
     *
     * @param text
     *            the {@code String} from which to get the word or separator
     *            string
     * @param position
     *            the starting index
     * @param separators
     *            the {@code Set} of separator characters
     * @return the first word or separator string found in {@code text} starting
     *         at index {@code position}
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
    public static String wordOrSeperator(String text, int position,
            Set<Character> separators) {
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
     * Generates a List of the entries in wordCount sorted by the number of
     * times they appear.
     *
     * @clears wordTally
     *
     * @return a list of the Map.Entrys from wordCount sorted by the number of
     *         appearences
     * @ensures wordCountSort contains all of the Map.Entrys from #wordCount and
     *          for each element in countSort,
     *          wordCountSorted(i)>=wordCountSorted(i+1)
     *
     */
    private static List<Map.Entry<String, Integer>> wordCountSorted(
            Map<String, Integer> wordTally) {
        //List that will be returned with the sorted counts
        List<Map.Entry<String, Integer>> countSort;
        countSort = new ArrayList<Map.Entry<String, Integer>>();

        //Create a new transfered list to work with
        Set<Map.Entry<String, Integer>> entries = wordTally.entrySet();
        Iterator<Map.Entry<String, Integer>> transfer = entries.iterator();

        //Add each entry individually
        while (transfer.hasNext()) {
            Map.Entry<String, Integer> current = transfer.next();
            transfer.remove();
            countSort.add(current);
        }
        //Compare
        Comparator<Map.Entry<String, Integer>> order = new IntegerSort();
        countSort.sort(order);

        //Return
        return countSort;

    }

    /**
     * Reads the input file and will keep count of each unique (case
     * insensitive) word and the number of times it appears. (WordCounts)
     *
     * @replaces wordCounts
     * @requires text.is_open
     * @ensures
     *
     *          <pre>
     *  The map contains words extracted from the input, where each key is a unique word, and its corresponding value represents the frequency of its occurrence in the input.
     *          </pre>
     *
     * @throws IOException
     *             if an I/O exception occurs
     */
    private static void fillMap(Map<String, Integer> wordTallys,
            Set<Character> separators, BufferedReader text) throws IOException {
        wordTallys.clear();
        //Iterate through each individual line
        String curLine = text.readLine();
        while (curLine != null) {
            //using toLowerCase allows us to ignore the capitalization of words
            curLine = curLine.toLowerCase();
            int posInLine = 0;
            //Iterate through the current line to find each separator in the line
            while (posInLine < curLine.length()) {
                String word = wordOrSeperator(curLine, posInLine, separators);
                //Update wordTallys appropriately
                if (!separators.contains(word.charAt(0))) {
                    //If the word is unique ==> add to the map
                    if (!wordTallys.containsKey(word)) {
                        wordTallys.put(word, 1);
                    } else {
                        //Increment
                        int newCount = wordTallys.get(word) + 1;
                        wordTallys.remove(word);
                        wordTallys.put(word, newCount);
                    }
                }
                //Update position in curLine
                posInLine += word.length();

            }
            curLine = text.readLine();
        }

    }

    /**
     * Assigns a font to each word based on its count, using a range of input
     * font sizes.
     *
     * @param count
     *            count of the word that needs a font size
     * @param maxCount
     *            max word count
     * @param minCount
     *            min word count
     * @requires maxCount =< count =< minCount, maxCount > 0, and minCount > 0
     * @ensures wordFontSize as defined in a CSS file, is determined by its
     *          count relative to a specified scale or range
     * @return the appropriate font size for a word that appears a certain
     *         number of times, denoted by "count", is determined.
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
     * Opening tags for the HTML file.
     *
     * @param input
     *            name of the input file to read from
     * @param n
     *            number of wordCount with highest counts
     * @param out
     *            output stream
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
    private static void headerOut(String input, int n, PrintWriter out) {
        assert out != null : "Violation of: output is not null.";
        assert input != null : "Violation of: input is not null.";
        /*
         * Output HTML opening tags/header.
         */
        out.println("<html><head><title>Top " + n + " wordCount in " + input
                + "</title>");
        out.println(
                "<link href=\"http://web.cse.ohio-state.edu/software/2231/web-sw2/assignments/projects/tag-cloud-generator/data/tagcloud.css\" rel=\"stylesheet\" type=\"text/css\">");
        out.println(
                "<link href=\"doc/tagcloud.css\" rel=\"stylesheet\" type=\"text/css\">");
        out.println("</head><body><h2>Top " + n + " wordCount in " + input
                + "</h2>");
        out.println("<hr>");
        out.println("<div class = \"cdiv\">");
        out.println("<p class = \"cbox\">");
    }

    /**
     * Generates HTML text for the top n wordCount with the highest frequency
     * from the countMap, where n is a user-defined getValue; the selected
     * wordCount are sorted alphabetically and presented in descending order
     * based on their occurrence count.
     *
     * @param n
     *            number of highest-count wordCount
     * @param countMap
     *            map of wordCount and counts
     * @param out
     *            HTML file to print n wordCount to
     * @clears countMap
     * @ensures out content = #out content * n wordCount from countMap
     */
    private static void mapOut(int n, Map<String, Integer> wordCounts,
            PrintWriter out) {

        /*
         * Sort words by their tallys and keep the words with the top tallys
         */
        //Words get sorted
        List<Map.Entry<String, Integer>> orderedTallys = wordCountSorted(
                wordCounts);
        List<Map.Entry<String, Integer>> topWords;
        //Variable that holds the top words
        topWords = new ArrayList<Map.Entry<String, Integer>>();
        //Get the words from the ordered list and put them into a new list
        while (orderedTallys.size() > 0 && topWords.size() < n) {
            Map.Entry<String, Integer> topWord = orderedTallys.get(0);
            orderedTallys.remove(0);
            topWords.add(topWord);

        }

        /*
         * Set up to sort words alphabetically.
         */
        Comparator<Map.Entry<String, Integer>> alphabeticalOrder = new StringSort();
        topWords.sort(alphabeticalOrder);

        /*
         * Get smallest and largest topWord counts
         */
        int min = 1, max = 1;
        if (topWords.size() > 0) {
            //Get the word at the last position for the min
            min = topWords.get(topWords.size() - 1).getValue();
            //Get the word at the first position for the max
            max = topWords.get(0).getValue();
        }

        /*
         * Output HTML for the top words
         */
        for (Map.Entry<String, Integer> wordCount : topWords) {
            String fontSize = wordFontSize(wordCount.getValue(), max, min);
            String tag = "<span style=\"cursor:default\" class=\"" + fontSize
                    + "\" title=\"count: " + wordCount.getValue() + "\">"
                    + wordCount.getKey() + "</span>";
            out.println(tag);
        }
    }

    /**
     * Generate HTML tags for the top 'numwordCount' wordCount in the file
     * denoted by the input, and then output them along with the corresponding
     * closing HTML tags.
     *
     * @requires n > 0 and input is open
     * @ensures in.content = #out.content * tags for top n wordCount
     */
    private static void tagCloudOut(PrintWriter out, BufferedReader in, int n) {

        //Get the separators of the entries
        Set<Character> elements = new HashSet<Character>();
        for (int i = 0; i < SEPARATORS.length(); i++) {
            if (!elements.contains(SEPARATORS.charAt(i))) {
                elements.add(SEPARATORS.charAt(i));
            }
        }

        //Get word counts and output
        Map<String, Integer> wordCounts = new HashMap<String, Integer>();
        try {
            fillMap(wordCounts, elements, in);
        } catch (IOException e) {
            System.out.println("Problem reading file and counting words");
        }
        mapOut(n, wordCounts, out);

        // output footer
        out.println("</p>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");

    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(System.in));

        /*
         * Prompt user for input file name and check for errors
         */
        System.out.println("Enter the name of the input file: ");
        String file = "";

        BufferedReader input = null;
        while (input == null) {
            try {
                file = in.readLine();
                input = new BufferedReader(new FileReader(file));
            } catch (IOException e) {
                System.out.println("Try again. File invalid because of " + e);
            }
        }
        String inFile = file;

        /*
         * Prompt user for output file and check for errors
         */
        System.out.print("Enter the name of the output file: ");
        String outFile = "";
        PrintWriter out = null;
        while (out == null) {
            try {
                outFile = in.readLine();
                out = new PrintWriter(
                        new BufferedWriter(new FileWriter(outFile)));
            } catch (IOException e) {
                System.out.println("Try again. File invalid because of " + e);

            }
        }

        // Get the number of words to output and check for errors
        System.out.print("Enter number of words to be included in tag cloud: ");
        int numWords = 0;
        try {
            numWords = Integer.parseInt(in.readLine());
        } catch (NumberFormatException e) {
            System.err.println("Error: number is in incorrect format");
        } catch (IOException e) {
            System.err.println("Error: cannot read input");
        }
        if (numWords < 0) {
            numWords = 0;
        }

        //Output the header tags
        headerOut(inFile, numWords, out);

        //Output tags for top words and closing tags
        tagCloudOut(out, input, numWords);

        //Close streams
        out.close();
        try {
            in.close();
        } catch (IOException e) {
            System.err.println("Error closing file");
        }

    }
}
