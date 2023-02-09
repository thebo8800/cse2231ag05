import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.map.Map;

/**
 * JUnit test fixture for {@code Map<String, String>}'s constructor and kernel
 * methods.
 *
 * @author Junbo Chen, Brett Emory
 *
 */
public abstract class MapTest {

	/**
	 * Invokes the appropriate {@code Map} constructor for the implementation under
	 * test and returns the result.
	 *
	 * @return the new map
	 * @ensures constructorTest = {}
	 */
	protected abstract Map<String, String> constructorTest();

	/**
	 * Invokes the appropriate {@code Map} constructor for the reference
	 * implementation and returns the result.
	 *
	 * @return the new map
	 * @ensures constructorRef = {}
	 */
	protected abstract Map<String, String> constructorRef();

	/**
	 *
	 * Creates and returns a {@code Map<String, String>} of the implementation under
	 * test type with the given entries.
	 *
	 * @param args the (key, value) pairs for the map
	 * @return the constructed map
	 * @requires
	 * 
	 *           <pre>
	 * [args.length is even]  and
	 * [the 'key' entries in args are unique]
	 *           </pre>
	 * 
	 * @ensures createFromArgsTest = [pairs in args]
	 */
	private Map<String, String> createFromArgsTest(String... args) {
		assert args.length % 2 == 0 : "Violation of: args.length is even";
		Map<String, String> map = this.constructorTest();
		for (int i = 0; i < args.length; i += 2) {
			assert !map.hasKey(args[i]) : "" + "Violation of: the 'key' entries in args are unique";
			map.add(args[i], args[i + 1]);
		}
		return map;
	}

	/**
	 *
	 * Creates and returns a {@code Map<String, String>} of the reference
	 * implementation type with the given entries.
	 *
	 * @param args the (key, value) pairs for the map
	 * @return the constructed map
	 * @requires
	 * 
	 *           <pre>
	 * [args.length is even]  and
	 * [the 'key' entries in args are unique]
	 *           </pre>
	 * 
	 * @ensures createFromArgsRef = [pairs in args]
	 */
	private Map<String, String> createFromArgsRef(String... args) {
		assert args.length % 2 == 0 : "Violation of: args.length is even";
		Map<String, String> map = this.constructorRef();
		for (int i = 0; i < args.length; i += 2) {
			assert !map.hasKey(args[i]) : "" + "Violation of: the 'key' entries in args are unique";
			map.add(args[i], args[i + 1]);
		}
		return map;
	}

	// test cases for constructor, add, remove, removeAny, value, hasKey, and size

	@Test
	public final void testConstructors() {
		Map<String, String> m = this.constructorTest();
		Map<String, String> n = this.constructorRef();

		assertEquals(n, m);
	}

	@Test
	public final void testCreateFromArgs() {
		Map<String, String> m = this.createFromArgsTest("the", "quick", "brown", "fox", "jumped", "over");
		Map<String, String> n = this.createFromArgsRef("the", "quick", "brown", "fox", "jumped", "over");

		assertEquals(n, m);
	}

	@Test
	public final void testCreateFromArgsEmpty() {
		Map<String, String> n = this.createFromArgsRef();
		Map<String, String> m = this.createFromArgsTest();

		assertEquals(n, m);
	}

	@Test
	public final void testAdd() {
		Map<String, String> m = this.createFromArgsTest("the", "quick", "brown", "fox");
		Map<String, String> n = this.createFromArgsRef("the", "quick", "brown", "fox", "jumped", "over");

		m.add("jumped", "over");
		assertEquals(n, m);
	}

	@Test
	public final void testAddFromEmpty() {
		Map<String, String> m = this.constructorTest();
		Map<String, String> n = this.createFromArgsRef("ape", "bonobo");

		m.add("ape", "bonobo");
		assertEquals(n, m);
	}

	@Test
	public final void testRemove() {
		Map<String, String> m = this.createFromArgsTest("the", "quick", "brown", "fox", "jumped", "over");
		Map<String, String> n = this.createFromArgsRef("the", "quick", "brown", "fox");

		m.remove("jumped");
		assertEquals(n, m);

	}

	@Test
	public final void testRemoveToEmpty() {
		Map<String, String> m = this.createFromArgsTest("ape", "bonobo");
		Map<String, String> n = this.constructorRef();

		m.remove("ape");
		assertEquals(n, m);
	}

	@Test
	public final void testRemoveAny() {
		Map<String, String> m = this.createFromArgsTest("the", "quick", "brown", "fox", "jumped", "over");
		Map<String, String> n = this.createFromArgsRef("the", "quick", "brown", "fox");

		m.removeAny();
		assertEquals(n.size(), m.size());
	}

	@Test
	public final void testRemoveAnyToEmpty() {
		Map<String, String> m = this.createFromArgsTest("ape", "bonobo");
		Map<String, String> n = this.constructorRef();

		m.removeAny();
		assertEquals(n, m);
	}

	@Test
	public final void testValue() {
		Map<String, String> m = this.createFromArgsRef("the", "quick", "brown", "fox", "jumped", "over");
		String n = "fox";

		assertEquals(n, m.value("brown"));
	}

	@Test
	public final void testValue2() {
		Map<String, String> m = this.createFromArgsTest("ape", "bonobo");
		String n = "bonobo";

		assertEquals(n, m.value("ape"));
	}

	@Test
	public final void testHasKey() {
		Map<String, String> m = this.createFromArgsRef("the", "quick", "brown", "fox", "jumped", "over");
		boolean n = true;

		assertEquals(n, m.hasKey("brown"));
	}

	@Test
	public final void testHasKey2() {
		Map<String, String> m = this.createFromArgsTest("ape", "bonobo");
		boolean n = false;

		assertEquals(n, m.hasKey("fish"));
	}

	@Test
	public final void testSize() {
		Map<String, String> m = this.createFromArgsTest("the", "quick", "brown", "fox", "jumped", "over");
		Map<String, String> n = this.createFromArgsRef("the", "quick", "brown", "fox", "jumped", "over");

		assertEquals(n.size(), m.size());
	}

	@Test
	public final void testSizeEmpty() {
		Map<String, String> m = this.constructorTest();
		int n = 0;

		assertEquals(n, m.size());
	}

}
