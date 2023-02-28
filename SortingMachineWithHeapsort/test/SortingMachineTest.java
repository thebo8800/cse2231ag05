import static org.junit.Assert.assertEquals;

import java.util.Comparator;

import org.junit.Test;

import components.sortingmachine.SortingMachine;

/**
 * JUnit test fixture for {@code SortingMachine<String>}'s constructor and
 * kernel methods.
 *
 * @author Put your name here
 *
 */
public abstract class SortingMachineTest {

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * implementation under test and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorTest = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorTest(
            Comparator<String> order);

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * reference implementation and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorRef = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorRef(
            Comparator<String> order);

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the
     * implementation under test type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsTest = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsTest(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorTest(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the reference
     * implementation type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsRef = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsRef(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorRef(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     * Comparator<String> implementation to be used in all test cases. Compare
     * {@code String}s in lexicographic order.
     */
    private static class StringLT implements Comparator<String> {

        @Override
        public int compare(String s1, String s2) {
            return s1.compareToIgnoreCase(s2);
        }

    }

    /**
     * Comparator instance to be used in all test cases.
     */
    private static final StringLT ORDER = new StringLT();

    /*
     * Sample test cases.
     */

    @Test
    public final void testConstructor() {
        SortingMachine<String> m = this.constructorTest(ORDER);
        SortingMachine<String> mExpected = this.constructorRef(ORDER);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green");
        m.add("green");
        assertEquals(mExpected, m);
    }

    @Test
    public final void addTestEvenLength() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "The",
                "Brown", "Quick", "Fox", "Jumped", "Over", "The", "Lazy");
        SortingMachine<String> m2 = this.createFromArgsRef(ORDER, true, "The",
                "Brown", "Quick", "Fox", "Jumped", "Over", "The", "Lazy",
                "Dawg");

        m.add("Dawg");
        assertEquals(m2, m);
    }

    @Test
    public final void testAddToNonEmptyOddLength() {

        SortingMachine<String> test = this.createFromArgsTest(ORDER, true,
                "test1", "test2", "test3");
        SortingMachine<String> ref = this.createFromArgsRef(ORDER, true,
                "test1", "test2", "test3", "test4");

        test.add("test4");

        assertEquals(ref, test);
    }

    @Test
    public final void removeFirstTest() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "A",
                "B", "C", "D", "E", "F", "G", "H", "I");
        SortingMachine<String> m2 = this.createFromArgsRef(ORDER, false, "B",
                "C", "D", "E", "F", "G", "H", "I");

        m.removeFirst();

        assertEquals(m2, m);
    }

    @Test
    public final void testChangeToExtractionModeEmpty() {

        SortingMachine<String> test = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> ref = this.createFromArgsRef(ORDER, true);

        test.changeToExtractionMode();
        ref.changeToExtractionMode();

        assertEquals(ref, test);
    }

    @Test
    public final void testChangeToExtractionModeEvenLength() {

        SortingMachine<String> test = this.createFromArgsTest(ORDER, true,
                "test1", "test2");
        SortingMachine<String> ref = this.createFromArgsRef(ORDER, true,
                "test1", "test2");

        test.changeToExtractionMode();
        ref.changeToExtractionMode();

        assertEquals(ref, test);
    }

    @Test
    public final void testChangeToExtractionModeOddLength() {

        SortingMachine<String> test = this.createFromArgsTest(ORDER, true,
                "test1", "test2", "test3");
        SortingMachine<String> ref = this.createFromArgsRef(ORDER, true,
                "test1", "test2", "test3");

        test.changeToExtractionMode();
        ref.changeToExtractionMode();

        assertEquals(ref, test);
    }

}
