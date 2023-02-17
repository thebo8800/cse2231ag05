import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.set.Set;

/**
 * JUnit test fixture for {@code Set<String>}'s constructor and kernel methods.
 *
 * @author Put your name here
 *
 */
public abstract class SetTest {

    /**
     * Invokes the appropriate {@code Set} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new set
     * @ensures constructorTest = {}
     */
    protected abstract Set<String> constructorTest();

    /**
     * Invokes the appropriate {@code Set} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new set
     * @ensures constructorRef = {}
     */
    protected abstract Set<String> constructorRef();

    /**
     * Creates and returns a {@code Set<String>} of the implementation under
     * test type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsTest = [entries in args]
     */
    private Set<String> createFromArgsTest(String... args) {
        Set<String> set = this.constructorTest();
        for (String s : args) {
            assert !set.contains(
                    s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    /**
     * Creates and returns a {@code Set<String>} of the reference implementation
     * type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsRef = [entries in args]
     */
    private Set<String> createFromArgsRef(String... args) {
        Set<String> set = this.constructorRef();
        for (String s : args) {
            assert !set.contains(
                    s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    /**
     * Tests default constructor: boundary case, only possible case.
     */
    @Test
    public final void testDefaultConstructor() {
        /*
         * Set up variables and call method under test
         */
        Set<String> s = this.constructorTest();
        Set<String> sExpected = this.constructorRef();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
    }

    /**
     * Tests add, boundary case: size of this = 0.
     */
    @Test
    public final void testAddToEmpty() {
        /*
         * Set up variables
         */
        Set<String> test = this.createFromArgsTest();
        Set<String> ref = this.createFromArgsRef("ghi");
        /*
         * Call method under test
         */
        test.add("ghi");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(ref, test);

    }

    /**
     * Tests add, routine case: size of this =1.
     */
    @Test
    public final void testAddSizeOne() {
        /*
         * Set up variables
         */
        Set<String> test = this.createFromArgsTest("abc");
        Set<String> ref = this.createFromArgsRef("abc", "ghi");
        /*
         * Call method under test
         */
        test.add("ghi");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(ref, test);

    }

    /**
     * Tests add, routine case: size of this >1.
     */
    @Test
    public final void testAddSizeMultiple() {
        /*
         * Set up variables
         */
        Set<String> test = this.createFromArgsTest("abc", "efg", "hij");
        Set<String> ref = this.createFromArgsRef("abc", "efg", "hij", "ghi");
        /*
         * Call method under test
         */
        test.add("ghi");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(ref, test);

    }

    /**
     * Tests remove, boundary case: size of this =1.
     */
    @Test
    public final void testRemoveToEmpty() {
        /*
         * Set up variables
         */
        Set<String> test = this.createFromArgsTest("ghi");
        Set<String> ref = this.createFromArgsRef();
        /*
         * Call method under test
         */
        String removed = test.remove("ghi");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(ref, test);
        assertEquals("ghi", removed);

    }

    /**
     * Tests remove, routine case: size of this >1.
     */
    @Test
    public final void testRemoveSizeMultiple() {
        /*
         * Set up variables
         */
        Set<String> test = this.createFromArgsTest("ghi", "abc");
        Set<String> ref = this.createFromArgsRef("abc");
        /*
         * Call method under test
         */
        String removed = test.remove("ghi");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(ref, test);
        assertEquals("ghi", removed);

    }

    /**
     * Tests contains, boundary case: size of this =0.
     */
    @Test
    public final void testContainsEmpty() {
        /*
         * Set up variables
         */
        Set<String> test = this.createFromArgsTest();
        Set<String> ref = this.createFromArgsRef();
        /*
         * Call method under test
         */
        boolean testContains = test.contains("abc");
        boolean refContains = test.contains("abc");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(ref, test);
        assertEquals(refContains, testContains);

    }

    /**
     * Tests contains, routine case: size of this >0 (true).
     */
    @Test
    public final void testContainsNonEmptyTrue() {
        /*
         * Set up variables
         */
        Set<String> test = this.createFromArgsTest("abc", "efg", "hij");
        Set<String> ref = this.createFromArgsRef("abc", "efg", "hij");
        /*
         * Call method under test
         */
        boolean testContains = test.contains("abc");
        boolean refContains = test.contains("abc");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(ref, test);
        assertEquals(refContains, testContains);

    }

    /**
     * Tests contains, routine case: size of this >0 (false).
     */
    @Test
    public final void testContainsNonEmptyFalse() {
        /*
         * Set up variables
         */
        Set<String> test = this.createFromArgsTest("abc", "efg", "hij");
        Set<String> ref = this.createFromArgsRef("abc", "efg", "hij");
        /*
         * Call method under test
         */
        boolean testContains = test.contains("xyz");
        boolean refContains = test.contains("xyz");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(ref, test);
        assertEquals(refContains, testContains);

    }

    /**
     * Tests contains, routine case: size of this =1 (false).
     */
    @Test
    public final void testContainsSizeOneFalse() {
        /*
         * Set up variables
         */
        Set<String> test = this.createFromArgsTest("abc");
        Set<String> ref = this.createFromArgsRef("abc");
        /*
         * Call method under test
         */
        boolean testContains = test.contains("xyz");
        boolean refContains = test.contains("xyz");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(ref, test);
        assertEquals(refContains, testContains);

    }

    /**
     * Tests contains, routine case: size of this =1 (true).
     */
    @Test
    public final void testContainsSizeOneTrue() {
        /*
         * Set up variables
         */
        Set<String> test = this.createFromArgsTest("xyz");
        Set<String> ref = this.createFromArgsRef("xyz");
        /*
         * Call method under test
         */
        boolean testContains = test.contains("xyz");
        boolean refContains = test.contains("xyz");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(ref, test);
        assertEquals(refContains, testContains);

    }

    /**
     * Tests size, boundary case: size=0.
     */
    @Test
    public final void testSizeEmpty() {
        /*
         * Set up variables
         */
        Set<String> test = this.createFromArgsTest();
        Set<String> ref = this.createFromArgsRef();
        /*
         * Call method under test
         */
        int testSize = test.size();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(ref, test);
        assertEquals(0, testSize);

    }

    /**
     * Tests size, routine case: size=1.
     */
    @Test
    public final void testSizeOne() {
        /*
         * Set up variables
         */
        Set<String> test = this.createFromArgsTest("abc");
        Set<String> ref = this.createFromArgsRef("abc");
        /*
         * Call method under test
         */
        int testSize = test.size();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(ref, test);
        assertEquals(1, testSize);

    }

    /**
     * Tests size, routine case: size>1.
     */
    @Test
    public final void testSizeMultiple() {
        /*
         * Set up variables
         */
        int num = 2 + 2;
        Set<String> test = this.createFromArgsTest("abc", "efg", "hij",
                "lmnop");
        Set<String> ref = this.createFromArgsRef("abc", "efg", "hij", "lmnop");
        /*
         * Call method under test
         */
        int testSize = test.size();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(ref, test);
        assertEquals(num, testSize);

    }

}
