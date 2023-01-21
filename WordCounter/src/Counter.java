import java.util.Comparator;
import java.util.Iterator;

import components.map.Map;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Parses a file into the number of occurrences of a word into an HTML file.
 *
 * @author Junbo Chen
 *
 */
public final class Counter {
    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private Counter() {

    }

    /**
     * Sets up the file with the appropriate opening tags for HTML.
     *
     * @param out
     *            -> output stream
     *
     * @param fileName
     *            -> name of file
     *
     * @updates out
     */
    public static void openTags(SimpleWriter out, String fileName) {
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Words Counted in " + fileName + "</title>");
        out.println("</head>");
        out.println("<h1>Words Counted in " + fileName + "</h1>");
        out.println("<body><table border = 3>");
        out.println("<tr><th>Word:</th><th>Count:</th></tr>");
    }

    /**
     * Iterates through each line of the file, then updates a queue with all
     * words and a map with each word and the count.
     *
     * @param read
     *            -> input stream
     * @param sorted
     *            -> queue of sorted words found in the text
     * @param data
     *            -> map of words and their appearance count
     *
     * @updates sorted, data
     *
     */
    public static void readFile(SimpleReader read, Queue<String> sorted,
            Map<String, Integer> data) {

        String line, nextWord;
        //set which contains the separator characters (in this case ' ,-')
        Set<Character> s = new Set1L<>();
        s.add(' ');
        s.add(',');
        s.add('-');

        /*
         * loop to iterate through entire line and add the words to queue/map
         * when meeting a separator (in this case ' ,-')
         */
        while (!read.atEOS()) {
            line = read.nextLine();
            for (int i = 0; i < line.length(); i += nextWord.length()) {
                nextWord = wordSeparator(line, i, s);
                //method which adds words to the queue/map
                updateQueueMap(nextWord, data, s, sorted);
            }
        }
    }

    /**
     * Update the Queue (as we iterate through it) and use it to update the Map
     * with each term and number of occurrences.
     *
     * @param nextWord
     *            -> nextWord found in the line
     * @param data
     *            -> map of sorted and counted words
     * @param s
     *            -> set of characters (in this case ' ,-') which are considered
     *            separators
     * @param sorted
     *            -> queue of sorted words in alphabetical order found in the
     *            file
     * @updates sorted, data
     *
     */
    public static void updateQueueMap(String nextWord,
            Map<String, Integer> data, Set<Character> s, Queue<String> sorted) {
        /*
         * conditional statement to check if the map has said contained word (in
         * this case nextWord)
         */
        if (!data.hasKey(nextWord)) {
            /*
             * set contains the nextWords first letter then adds said word to
             * the map with value q and puts said word into the queue sorted
             */
            if (!s.contains(nextWord.charAt(0))) {
                data.add(nextWord, 1);
                sorted.enqueue(nextWord);
            }
            /*
             * if the map already contains said word simply update the map
             * value; not necessary to touch the queue since it already exists
             */
        } else {
            data.replaceValue(nextWord, (data.value(nextWord) + 1));
        }
    }

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
     * @ensures <pre>
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
     * </pre>
     */
    public static String wordSeparator(String text, int position,
            Set<Character> separators) {
        assert text != null : "Violation of: text is not null";
        assert separators != null : "Violation of: separators is not null";
        assert 0 <= position : "Violation of: 0 <= position";
        assert position < text.length() : "Violation of: position < |text|";

        //Initialize variables
        String newString = "";
        int i = position;
        //Conditional statement to check char at position is in separators set
        if (separators.contains(text.charAt(i))) {
            while (i < text.length() && separators.contains(text.charAt(i))) {
                newString += text.charAt(i);
                i++;
            }
        } else {
            while (i < text.length() && !separators.contains(text.charAt(i))) {
                newString = newString + text.charAt(i);
                i++;
            }
        }
        return newString;

    }

    /**
     * Outputs the sorted Queue of words and their frequencies from the Map.
     *
     * @param out
     *            -> output stream
     * @param sorted
     *            -> queue which has the sorted words in order
     * @param data
     *            -> map which contains words and their frequency
     *
     * @updates sorted, data
     */
    public static void rowOut(SimpleWriter out, Queue<String> sorted,
            Map<String, Integer> data) {
        String word;
        //loop which outputs the words in a valid HTML way
        for (Iterator<String> check = sorted.iterator(); check.hasNext();) {
            word = check.next();
            out.println("<tr>");
            out.println("<td>" + word + "</td>");
            out.print("<td>" + data.value(word) + "</td>");
            out.println("</tr>");
        }
    }

    /**
     * ***Ripped from 2221*** Compare {@code String}s in lexicographic order,
     * ignoring case. Reference:
     *
     *
     * @return words from input file in lexicographic order.
     */
    private static class StringLT implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            return o1.compareToIgnoreCase(o2);
        }
    }

    /**
     * Closes out file with closing HTML tags.
     *
     * @param out
     *            -> output stream
     * @updates out
     */
    public static void closeTags(SimpleWriter out) {
        out.println("</table>");
        out.println("</body>");
        out.println("</html>");
    }

    /**
     * Main method.
     *
     * @param args
     *            command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        //prompt user and setup to read file
        out.print("Name of file to parse: ");
        String fileName = in.nextLine();
        SimpleReader read = new SimpleReader1L(fileName);

        //prompt user and setup to write to file
        out.print("Name of output file: ");
        String fileNaem = in.nextLine();
        SimpleWriter write = new SimpleWriter1L(fileNaem);

        openTags(write, fileName);

        Queue<String> sort = new Queue1L<>();
        Map<String, Integer> data = new Map1L<>();
        readFile(read, sort, data);

        Comparator<String> s = new StringLT();
        //sort the queue so its in alphabetical order
        sort.sort(s);

        rowOut(write, sort, data);
        closeTags(write);

        //close
        read.close();
        in.close();
        out.close();
    }
}
