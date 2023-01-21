import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.queue.Queue;

/**
 * JUnit test fixture for {@code Queue<String>}'s constructor and kernel methods
 * (plus Standard methods and front secondary method).
 *
 * @author Paolo Bucci
 *
 */
public abstract class QueueTest {

    /**
     * Invokes the appropriate {@code Queue} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new queue
     * @ensures constructorTest = <>
     */
    protected abstract Queue<String> constructorTest();

    /**
     * Invokes the appropriate {@code Queue} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new queue
     * @ensures constructorRef = <>
     */
    protected abstract Queue<String> constructorRef();

    /**
     *
     * Creates and returns a {@code Queue<String>} of the implementation under
     * test type with the given entries.
     *
     * @param args
     *            the entries for the queue
     * @return the constructed queue
     * @ensures createFromArgsTest = [entries in args]
     */
    private Queue<String> createFromArgsTest(String... args) {
        Queue<String> queue = this.constructorTest();
        for (String s : args) {
            queue.enqueue(s);
        }
        return queue;
    }

    /**
     *
     * Creates and returns a {@code Queue<String>} of the reference
     * implementation type with the given entries.
     *
     * @param args
     *            the entries for the queue
     * @return the constructed queue
     * @ensures createFromArgsRef = [entries in args]
     */
    private Queue<String> createFromArgsRef(String... args) {
        Queue<String> queue = this.constructorRef();
        for (String s : args) {
            queue.enqueue(s);
        }
        return queue;
    }

    /*
     * Test cases for constructors
     */

    @Test
    public final void testNoArgumentConstructor() {
        /*
         * Set up variables and call method under test
         */
        Queue<String> q = this.constructorTest();
        Queue<String> qExpected = this.constructorRef();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(qExpected, q);
    }

    /*
     * Test cases for kernel methods
     */

    @Test
    public final void testEnqueueEmpty() {
        /*
         * Set up variables
         */
        Queue<String> q = this.createFromArgsTest();
        Queue<String> qExpected = this.createFromArgsRef("red");
        /*
         * Call method under test
         */
        q.enqueue("red");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(qExpected, q);
    }

    @Test
    public final void testEnqueueNonEmptyOne() {
        /*
         * Set up variables
         */
        Queue<String> q = this.createFromArgsTest("red");
        Queue<String> qExpected = this.createFromArgsRef("red", "blue");
        /*
         * Call method under test
         */
        q.enqueue("blue");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(qExpected, q);
    }

    @Test
    public final void testEnqueueNonEmptyMoreThanOne() {
        /*
         * Set up variables
         */
        Queue<String> q = this.createFromArgsTest("red", "blue", "green");
        Queue<String> qExpected = this.createFromArgsRef("red", "blue",
                "green", "yellow");
        /*
         * Call method under test
         */
        q.enqueue("yellow");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(qExpected, q);
    }

    @Test
    public final void testDequeueLeavingEmpty() {
        /*
         * Set up variables
         */
        Queue<String> q = this.createFromArgsTest("red");
        Queue<String> qExpected = this.createFromArgsRef();
        /*
         * Call method under test
         */
        String x = q.dequeue();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(qExpected, q);
        assertEquals("red", x);
    }

    @Test
    public final void testDequeueLeavingNonEmptyOne() {
        /*
         * Set up variables
         */
        Queue<String> q = this.createFromArgsTest("red", "blue");
        Queue<String> qExpected = this.createFromArgsRef("blue");
        /*
         * Call method under test
         */
        String x = q.dequeue();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(qExpected, q);
        assertEquals("red", x);
    }

    @Test
    public final void testDequeueLeavingNonEmptyMoreThanOne() {
        /*
         * Set up variables
         */
        Queue<String> q = this.createFromArgsTest("red", "green", "blue");
        Queue<String> qExpected = this.createFromArgsRef("green", "blue");
        /*
         * Call method under test
         */
        String x = q.dequeue();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(qExpected, q);
        assertEquals("red", x);
    }

    @Test
    public final void testLengthEmpty() {
        /*
         * Set up variables
         */
        Queue<String> q = this.createFromArgsTest();
        Queue<String> qExpected = this.createFromArgsRef();
        /*
         * Call method under test
         */
        int i = q.length();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(qExpected, q);
        assertEquals(0, i);
    }

    @Test
    public final void testLengthNonEmptyOne() {
        /*
         * Set up variables
         */
        Queue<String> q = this.createFromArgsTest("red");
        Queue<String> qExpected = this.createFromArgsRef("red");
        /*
         * Call method under test
         */
        int i = q.length();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(qExpected, q);
        assertEquals(1, i);
    }

    @Test
    public final void testLengthNonEmptyMoreThanOne() {
        /*
         * Set up variables
         */
        Queue<String> q = this.createFromArgsTest("red", "green", "blue");
        Queue<String> qExpected = this
                .createFromArgsRef("red", "green", "blue");
        /*
         * Call method under test
         */
        int i = q.length();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(qExpected, q);
        assertEquals(3, i);
    }

    /*
     * Test cases for Standard methods
     */

    @Test
    public final void testNewInstanceEmpty() {
        /*
         * Set up variables
         */
        Queue<String> q1 = this.createFromArgsTest();
        Queue<String> q1Expected = this.createFromArgsRef();
        Queue<String> q2Expected = this.createFromArgsRef();
        /*
         * Call method under test
         */
        Queue<String> q2 = q1.newInstance();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(q1Expected, q1);
        assertEquals(q2Expected, q2);
    }

    @Test
    public final void testNewInstanceNonEmptyOne() {
        /*
         * Set up variables
         */
        Queue<String> q1 = this.createFromArgsTest("red");
        Queue<String> q1Expected = this.createFromArgsRef("red");
        Queue<String> q2Expected = this.createFromArgsRef();
        /*
         * Call method under test
         */
        Queue<String> q2 = q1.newInstance();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(q1Expected, q1);
        assertEquals(q2Expected, q2);
    }

    @Test
    public final void testNewInstanceNonEmptyMoreThanOne() {
        /*
         * Set up variables
         */
        Queue<String> q1 = this.createFromArgsTest("red", "green", "blue");
        Queue<String> q1Expected = this.createFromArgsRef("red", "green",
                "blue");
        Queue<String> q2Expected = this.createFromArgsRef();
        /*
         * Call method under test
         */
        Queue<String> q2 = q1.newInstance();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(q1Expected, q1);
        assertEquals(q2Expected, q2);
    }

    @Test
    public final void testClearEmpty() {
        /*
         * Set up variables
         */
        Queue<String> q = this.createFromArgsTest();
        Queue<String> qExpected = this.createFromArgsRef();
        /*
         * Call method under test
         */
        q.clear();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(qExpected, q);
    }

    @Test
    public final void testClearNonEmptyOne() {
        /*
         * Set up variables
         */
        Queue<String> q = this.createFromArgsTest("red");
        Queue<String> qExpected = this.createFromArgsRef();
        /*
         * Call method under test
         */
        q.clear();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(qExpected, q);
    }

    @Test
    public final void testClearNonEmptyMoreThanOne() {
        /*
         * Set up variables
         */
        Queue<String> q = this.createFromArgsTest("red", "green", "blue");
        Queue<String> qExpected = this.createFromArgsRef();
        /*
         * Call method under test
         */
        q.clear();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(qExpected, q);
    }

    @Test
    public final void testTransferFromEmptyEmpty() {
        /*
         * Set up variables
         */
        Queue<String> q1 = this.createFromArgsTest();
        Queue<String> q1Expected = this.createFromArgsRef();
        Queue<String> q2 = this.createFromArgsTest();
        Queue<String> q2Expected = this.createFromArgsRef();
        /*
         * Call method under test
         */
        q1.transferFrom(q2);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(q1Expected, q1);
        assertEquals(q2Expected, q2);
    }

    @Test
    public final void testTransferFromEmptyNonEmpty() {
        /*
         * Set up variables
         */
        Queue<String> q1 = this.createFromArgsTest();
        Queue<String> q1Expected = this.createFromArgsRef("red", "green",
                "blue");
        Queue<String> q2 = this.createFromArgsTest("red", "green", "blue");
        Queue<String> q2Expected = this.createFromArgsRef();
        /*
         * Call method under test
         */
        q1.transferFrom(q2);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(q1Expected, q1);
        assertEquals(q2Expected, q2);
    }

    @Test
    public final void testTransferFromNonEmptyEmpty() {
        /*
         * Set up variables
         */
        Queue<String> q1 = this.createFromArgsTest("red", "green", "blue");
        Queue<String> q1Expected = this.createFromArgsRef();
        Queue<String> q2 = this.createFromArgsTest();
        Queue<String> q2Expected = this.createFromArgsRef();
        /*
         * Call method under test
         */
        q1.transferFrom(q2);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(q1Expected, q1);
        assertEquals(q2Expected, q2);
    }

    @Test
    public final void testTransferFromNonEmptyNonEmpty() {
        /*
         * Set up variables
         */
        Queue<String> q1 = this.createFromArgsTest("red", "green", "blue");
        Queue<String> q1Expected = this.createFromArgsRef("yellow", "orange");
        Queue<String> q2 = this.createFromArgsTest("yellow", "orange");
        Queue<String> q2Expected = this.createFromArgsRef();
        /*
         * Call method under test
         */
        q1.transferFrom(q2);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(q1Expected, q1);
        assertEquals(q2Expected, q2);
    }

    /*
     * Test cases for other methods
     */

    @Test
    public final void testFrontNonEmptyOne() {
        /*
         * Set up variables
         */
        Queue<String> q = this.createFromArgsTest("red");
        Queue<String> qExpected = this.createFromArgsRef("red");
        /*
         * Call method under test
         */
        String x = q.front();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(qExpected, q);
        assertEquals("red", x);
    }

    @Test
    public final void testFrontNonEmptyMoreThanOne() {
        /*
         * Set up variables
         */
        Queue<String> q = this.createFromArgsTest("red", "green", "blue");
        Queue<String> qExpected = this
                .createFromArgsRef("red", "green", "blue");
        /*
         * Call method under test
         */
        String x = q.front();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(qExpected, q);
        assertEquals("red", x);
    }

}
