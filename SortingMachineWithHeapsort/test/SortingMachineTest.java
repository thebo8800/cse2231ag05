import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Comparator;

import org.junit.Test;

import components.sortingmachine.SortingMachine;

/**
 * JUnit test fixture for {@code SortingMachine<String>}'s constructor and
 * kernel methods.
 *
<<<<<<< HEAD
 * @author Brett Emory, Junbo Chen
=======
 * @author Junbo Chen, Brett Emory
>>>>>>> 30ce4c88b14917811a418b34bbef9d89cc703f53
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
        //Set Up Variables
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green");
        //Call Method
        m.add("green");
        assertEquals(mExpected, m);
    }

    @Test
    public final void addTestEvenLength() {
        //Set Up Variables
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "The",
                "Brown", "Quick", "Fox", "Jumped", "Over", "The", "Lazy");
        SortingMachine<String> m2 = this.createFromArgsRef(ORDER, true, "The",
                "Brown", "Quick", "Fox", "Jumped", "Over", "The", "Lazy",
                "Dawg");
        //Call Method
        m.add("Dawg");
        //Assertions
        assertEquals(m2, m);
    }

    @Test
    public final void testAddToNonEmptyOddLength() {
        //Set Up Variables
        SortingMachine<String> test = this.createFromArgsTest(ORDER, true,
                "test1", "test2", "test3");
        SortingMachine<String> ref = this.createFromArgsRef(ORDER, true,
                "test1", "test2", "test3", "test4");
        //Call Method
        test.add("test4");
        //Assertions
        assertEquals(ref, test);
    }

    @Test
    public final void removeFirstTest() {
        //Set Up Variables
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "A",
                "B", "C", "D", "E", "F", "G", "H", "I");
        SortingMachine<String> m2 = this.createFromArgsRef(ORDER, false, "B",
                "C", "D", "E", "F", "G", "H", "I");
        //Call Method
        m.removeFirst();
        //Assertions
        assertEquals(m2, m);
    }

    @Test
    public final void testChangeToExtractionModeEmpty() {
        //Set Up Variables
        SortingMachine<String> test = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> ref = this.createFromArgsRef(ORDER, true);
        //Call Method
        test.changeToExtractionMode();
        ref.changeToExtractionMode();
        //Assertions
        assertEquals(ref, test);
    }

    @Test
    public final void testChangeToExtractionModeEvenLength() {
        //Set Up Variables
        SortingMachine<String> test = this.createFromArgsTest(ORDER, true,
                "test1", "test2");
        SortingMachine<String> ref = this.createFromArgsRef(ORDER, true,
                "test1", "test2");
        //Call Method
        test.changeToExtractionMode();
        ref.changeToExtractionMode();
        //Assertions
        assertEquals(ref, test);
    }

    @Test
    public final void testChangeToExtractionModeOddLength() {
        //Set Up Variables
        SortingMachine<String> test = this.createFromArgsTest(ORDER, true,
                "test1", "test2", "test3");
        SortingMachine<String> ref = this.createFromArgsRef(ORDER, true,
                "test1", "test2", "test3");
        //Call Method
        test.changeToExtractionMode();
        ref.changeToExtractionMode();
        //Assertions
        assertEquals(ref, test);
    }

    @Test
    public final void testRemoveFirstToEmpty() {
        //Set Up Variables
        SortingMachine<String> test = this.createFromArgsTest(ORDER, false,
                "test1");
        SortingMachine<String> ref = this.createFromArgsRef(ORDER, false,
                "test1");

        //Call Method
        String removedTest = test.removeFirst();
        String removedRef = ref.removeFirst();

        //Assert statements
        assertEquals(removedRef, removedTest);
        assertEquals(ref, test);
    }

    @Test
    public final void testRemoveFirstOddNonEmpty() {
        //Set Up Variables
        SortingMachine<String> test = this.createFromArgsTest(ORDER, false,
                "test1", "test2", "test3");
        SortingMachine<String> ref = this.createFromArgsRef(ORDER, false,
                "test1", "test2", "test3");
        //Call Method
        String removedTest = test.removeFirst();
        String removedRef = ref.removeFirst();
        //Assertions
        assertEquals(removedRef, removedTest);
        assertEquals(ref, test);
    }

    @Test
    public final void testRemoveFirstEvenNonEmpty() {
        ///Set Up Variables
        SortingMachine<String> test = this.createFromArgsTest(ORDER, false,
                "test1", "test2");
        SortingMachine<String> ref = this.createFromArgsRef(ORDER, false,
                "test1", "test2");
        //Call Method
        String removedTest = test.removeFirst();
        String removedRef = ref.removeFirst();
        //Assertions
        assertEquals(removedRef, removedTest);
        assertEquals(ref, test);
    }

    @Test
    public final void testIsInInsertionModeEmptyFalse() {
        //Set Up Variables
        SortingMachine<String> test = this.createFromArgsTest(ORDER, false);
        SortingMachine<String> ref = this.createFromArgsRef(ORDER, false);
        //Call Method
        boolean insertionTest = test.isInInsertionMode();
        boolean insertionRef = ref.isInInsertionMode();
        //Assertions
        assertEquals(insertionRef, insertionTest);
        assertEquals(ref, test);
    }

    @Test
    public final void testIsInInsertionModeEmptyTrue() {
        //Set Up Variables
        SortingMachine<String> test = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> ref = this.createFromArgsRef(ORDER, true);
        //Call Method
        boolean insertionTest = test.isInInsertionMode();
        boolean insertionRef = ref.isInInsertionMode();
        //Assertions
        assertEquals(insertionRef, insertionTest);
        assertEquals(ref, test);
    }

    @Test
    public final void testIsInInsertionModeNonEmptyTrue() {

        //Set Up Variables
        SortingMachine<String> test = this.createFromArgsTest(ORDER, true,
                "test1", "test2");
        SortingMachine<String> ref = this.createFromArgsRef(ORDER, true,
                "test1", "test2");
        //Call Method
        boolean insertionTest = test.isInInsertionMode();
        boolean insertionRef = ref.isInInsertionMode();
        //Assertions
        assertEquals(insertionRef, insertionTest);
        assertEquals(ref, test);
    }

    @Test
    public final void testIsInInsertionModeNonEmptyFalse() {
        //Set Up Variables
        SortingMachine<String> test = this.createFromArgsTest(ORDER, false,
                "test1", "test2");
        SortingMachine<String> ref = this.createFromArgsRef(ORDER, false,
                "test1", "test2");
        //Call Method
        boolean insertionTest = test.isInInsertionMode();
        boolean insertionRef = ref.isInInsertionMode();
        //Assertions
        assertEquals(insertionRef, insertionTest);
        assertEquals(ref, test);
    }

    @Test
    public final void testOrderEmptyInsertion() {
        //Set Up Variables
        SortingMachine<String> test = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> ref = this.createFromArgsRef(ORDER, true);
        //Call Method
        Comparator<String> orderTest = test.order();
        Comparator<String> orderRef = ref.order();
        //Assertions
        assertEquals(orderRef, orderTest);
        assertTrue(orderRef == orderTest);
        assertEquals(ref, test);
    }

    @Test
    public final void testOrderEmptyExtraction() {

        SortingMachine<String> test = this.createFromArgsTest(ORDER, false);
        SortingMachine<String> ref = this.createFromArgsRef(ORDER, false);
        //Call Method
        Comparator<String> orderTest = test.order();
        Comparator<String> orderRef = ref.order();
        //Assertions
        assertEquals(orderRef, orderTest);
        assertTrue(orderRef == orderTest);
        assertEquals(ref, test);
    }

    @Test
    public final void testOrderNonEmptyInsertion() {
        //Set Up Variables
        SortingMachine<String> test = this.createFromArgsTest(ORDER, true,
                "test1", "test2");
        SortingMachine<String> ref = this.createFromArgsRef(ORDER, true,
                "test1", "test2");
        //Call Method
        Comparator<String> orderTest = test.order();
        Comparator<String> orderRef = ref.order();
        //Assertions
        assertEquals(orderRef, orderTest);
        assertTrue(orderRef == orderTest);
        assertEquals(ref, test);
    }

    @Test
    public final void testOrderNonEmptyExtraction() {
        //Set Up Variables
        SortingMachine<String> test = this.createFromArgsTest(ORDER, false,
                "test1", "test2");
        SortingMachine<String> ref = this.createFromArgsRef(ORDER, false,
                "test1", "test2");
        //Call Method
        Comparator<String> orderTest = test.order();
        Comparator<String> orderRef = ref.order();
        //Assertions
        assertEquals(orderRef, orderTest);
        assertTrue(orderRef == orderTest);
        assertEquals(ref, test);
    }

    @Test
    public final void testSizeEmptyInsertion() {
        //Set Up Variables
        SortingMachine<String> test = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> ref = this.createFromArgsRef(ORDER, true);
        //Call Method
        int sizeTest = test.size();
        int sizeRef = ref.size();
        //Assertions
        assertEquals(sizeRef, sizeTest);
        assertEquals(ref, test);
    }

    @Test
    public final void testSizeEmptyExtraction() {
        //Set Up Variables
        SortingMachine<String> test = this.createFromArgsTest(ORDER, false);
        SortingMachine<String> ref = this.createFromArgsRef(ORDER, false);
        //Call Method
        int sizeTest = test.size();
        int sizeRef = ref.size();
        //Assertions
        assertEquals(sizeRef, sizeTest);
        assertEquals(ref, test);
    }

    @Test
    public final void testSizeNonEmptyInsertion() {

        //Set Up Variables
        SortingMachine<String> test = this.createFromArgsTest(ORDER, true,
                "test1", "test2", "test3");
        SortingMachine<String> ref = this.createFromArgsRef(ORDER, true,
                "test1", "test2", "test3");
        //Call Method
        int sizeTest = test.size();
        int sizeRef = ref.size();
        //Assertions
        assertEquals(sizeRef, sizeTest);
        assertEquals(ref, test);
    }

    @Test
    public final void testSizeNonEmptyExtraction() {
        //Set Up Variables
        SortingMachine<String> test = this.createFromArgsTest(ORDER, false,
                "test1", "test2", "test3");
        SortingMachine<String> ref = this.createFromArgsRef(ORDER, false,
                "test1", "test2", "test3");
        //Call Method
        int sizeTest = test.size();
        int sizeRef = ref.size();
        //Assertions
        assertEquals(sizeRef, sizeTest);
        assertEquals(ref, test);
    }

}
