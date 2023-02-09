import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.naturalnumber.NaturalNumber;

/**
 * JUnit test fixture for {@code NaturalNumber}'s constructors and kernel
 * methods.
 *
 * @author Brett Emory, Junbo Chen
 *
 */
public abstract class NaturalNumberTest {

	/**
	 * Invokes the appropriate {@code NaturalNumber} constructor for the
	 * implementation under test and returns the result.
	 *
	 * @return the new number
	 * @ensures constructorTest = 0
	 */
	protected abstract NaturalNumber constructorTest();

	/**
	 * Invokes the appropriate {@code NaturalNumber} constructor for the
	 * implementation under test and returns the result.
	 *
	 * @param i {@code int} to initialize from
	 * @return the new number
	 * @requires i >= 0
	 * @ensures constructorTest =
	 */
	protected abstract NaturalNumber constructorTest(int i);

	/**
	 * Invokes the appropriate {@code NaturalNumber} constructor for the
	 * implementation under test and returns the result.
	 *
	 * @param s {@code String} to initialize from
	 * @return the new number
	 * @requires there exists n: NATURAL (s = TO_STRING(n))
	 * @ensures s = TO_STRING(constructorTest)
	 */
	protected abstract NaturalNumber constructorTest(String s);

	/**
	 * Invokes the appropriate {@code NaturalNumber} constructor for the
	 * implementation under test and returns the result.
	 *
	 * @param n {@code NaturalNumber} to initialize from
	 * @return the new number
	 * @ensures constructorTest = n
	 */
	protected abstract NaturalNumber constructorTest(NaturalNumber n);

	/**
	 * Invokes the appropriate {@code NaturalNumber} constructor for the reference
	 * implementation and returns the result.
	 *
	 * @return the new number
	 * @ensures constructorRef = 0
	 */
	protected abstract NaturalNumber constructorRef();

	/**
	 * Invokes the appropriate {@code NaturalNumber} constructor for the reference
	 * implementation and returns the result.
	 *
	 * @param i {@code int} to initialize from
	 * @return the new number
	 * @requires i >= 0
	 * @ensures constructorRef = i
	 */
	protected abstract NaturalNumber constructorRef(int i);

	/**
	 * Invokes the appropriate {@code NaturalNumber} constructor for the reference
	 * implementation and returns the result.
	 *
	 * @param s {@code String} to initialize from
	 * @return the new number
	 * @requires there exists n: NATURAL (s = TO_STRING(n))
	 * @ensures s = TO_STRING(constructorRef)
	 */
	protected abstract NaturalNumber constructorRef(String s);

	/**
	 * Invokes the appropriate {@code NaturalNumber} constructor for the reference
	 * implementation and returns the result.
	 *
	 * @param n {@code NaturalNumber} to initialize from
	 * @return the new number
	 * @ensures constructorRef = n
	 */
	protected abstract NaturalNumber constructorRef(NaturalNumber n);

	@Test
	public final void testDefault() {

		// Set Up Variables:
		NaturalNumber m = this.constructorTest();
		NaturalNumber n = this.constructorRef();

		// Check Postconditions
		assertEquals(m, n);
	}

	// Checks constructor: NaturalNumber | Smallest Value
	@Test
	public final void testConstructorIntZero() {

		NaturalNumber n = this.constructorTest(0);
		NaturalNumber m = this.constructorRef(0);

		// Check Postconditions
		assertEquals(n, m);
	}

	// Checks constructor: String | Smallest Value
	@Test
	public final void testConstructorStringZero() {

		NaturalNumber n = this.constructorTest("0");
		NaturalNumber m = this.constructorRef("0");

		// Check Postconditions
		assertEquals(n, m);
	}

	// Checks constructor: NaturalNumber | Smallest Value
	@Test
	public final void testConstructorNaturalNumberZero() {

		// Set Up Variables:
		NaturalNumber l = this.constructorRef();
		NaturalNumber n = this.constructorRef();
		NaturalNumber m = this.constructorTest(l);

		// Check Postconditions
		assertEquals(n, m);
		assertEquals(n, l);
	}

	@Test
	public final void testConstructorIntNumber() {

		// Set Up Variables:
		NaturalNumber n = this.constructorTest(69420);
		NaturalNumber m = this.constructorRef(69420);

		// Check Postconditions
		assertEquals(n, m);
	}

	// Checks constructor: int | Multiple Zeros
	@Test
	public final void testConstructorIntMultipleZeros() {

		// Set Up Variables:
		NaturalNumber test = this.constructorTest(10020030);
		NaturalNumber ref = this.constructorRef(10020030);

		// Check Postconditions
		assertEquals(ref, test);
	}

	@Test
	public final void testConstructorStringNumber() {

		// Set Up Variables:
		NaturalNumber n = this.constructorTest("69420");
		NaturalNumber m = this.constructorRef("69420");

		// Check Postconditions
		assertEquals(n, m);

	}

	@Test
	public final void testConstructorStringMultipleZeros() {

		// Set Up Variables:
		NaturalNumber test = this.constructorTest("10020030");
		NaturalNumber ref = this.constructorRef("10020030");

		// Check Postconditions
		assertEquals(ref, test);
	}

	// Checks constructor: NaturalNumber | Routine Case
	@Test
	public final void testConstructorNaturalNumberNumber() {

		// Set Up Variables:
		NaturalNumber l = this.constructorTest("69420");
		NaturalNumber m = this.constructorRef("69420");
		NaturalNumber n = this.constructorRef(l);

		// Check Postconditions
		assertEquals(n, m);
		assertEquals(n, l);
	}

	// Checks constructor: NaturalNumber | Multiple Zeros
	@Test
	public final void testConstructorNaturalNumberMultipleZeros() {

		// Set Up Variables:
		NaturalNumber ref = this.constructorTest("10020030");
		NaturalNumber preserve = this.constructorRef("10020030");
		NaturalNumber test = this.constructorTest(ref);

		// Check Postconditions
		assertEquals(test, preserve);
		assertEquals(test, ref);
	}

	// Checks constructor: int | Largest possible int

	@Test
	public final void testIntConstructorIntMaxValue() {

		// Set Up Variables:
		NaturalNumber test = this.constructorTest(Integer.MAX_VALUE);
		NaturalNumber ref = this.constructorRef(Integer.MAX_VALUE);

		// Check postconditions
		assertEquals(ref, test);
	}

	@Test
	public final void testIntConstructorSingleDigitEven() {

		// Set Up Variables:
		NaturalNumber test = this.constructorTest(4);
		NaturalNumber ref = this.constructorRef(4);

		// Check postconditions
		assertEquals(ref, test);
	}

	// Checks constructor: NaturalNumber | odd number
	@Test
	public final void testNaturalNumberConstructorEven() {

		// Set Up Variables:
		NaturalNumber ref = this.constructorRef(6);
		NaturalNumber preserve = this.constructorRef(6);
		NaturalNumber test = this.constructorTest(ref);

		// Check postconditions
		assertEquals(ref, test);
		assertEquals(ref, preserve);
	}

	// Checks constructor: Int | Tests a single odd digit
	@Test
	public final void testIntConstructorSingleDigitOdd() {

		// Set Up Variables:
		NaturalNumber test = this.constructorTest(7);
		NaturalNumber ref = this.constructorRef(7);

		assertEquals(ref, test);
	}

	// Checks constructor: NaturalNumber | odd number
	@Test
	public final void testNaturalNumberConstructorOdd() {

		// Set Up Variables:
		NaturalNumber sourceRef = this.constructorRef(5);
		NaturalNumber sourcePreserve = this.constructorRef(5);
		NaturalNumber test = this.constructorTest(sourceRef);

		// Check Postconditions
		assertEquals(sourceRef, test);
		assertEquals(sourceRef, sourcePreserve);
	}

	// Tests MultiplyBy10 | this = 0
	@Test
	public final void testMultiplyBy10ThisZero() {

		// Set Up Variables:
		NaturalNumber test = this.constructorTest(0);
		NaturalNumber ref = this.constructorRef(0);

		// Call method
		test.multiplyBy10(5);
		ref.multiplyBy10(5);

		// Check Postconditions
		assertEquals(ref, test);
	}

	// Tests MultiplyBy10 | Largest digit for k
	@Test
	public final void testMultiplyBy10_9() {

		// Set Up Variables:
		NaturalNumber test = this.constructorTest(2);
		NaturalNumber ref = this.constructorRef(2);

		// Test Boundary Case with 9
		test.multiplyBy10(9);
		ref.multiplyBy10(9);

		// Check Postconditions
		assertEquals(ref, test);
	}

	// Tests MultiplyBy10 | Smallest value for k
	@Test
	public final void testMultiplyBy10_0() {

		// Set Up Variables:
		NaturalNumber test = this.constructorTest(2);
		NaturalNumber ref = this.constructorRef(2);

		test.multiplyBy10(0);
		ref.multiplyBy10(0);

		// Check Postconditions
		assertEquals(ref, test);
	}

	// Tests MultiplyBy10 | multiple digits
	@Test
	public final void testMultiplyBy10MultipleDigit() {

		// Set Up Variables:
		NaturalNumber test = this.constructorTest(589076);
		NaturalNumber ref = this.constructorRef(589076);

		test.multiplyBy10(4);
		ref.multiplyBy10(4);

		// Check Postconditions
		assertEquals(ref, test);
	}

	// Tests DivideBY10 | This = 0
	@Test
	public final void testDivideBy10_0() {

		// Set Up Variables:
		NaturalNumber test = this.constructorTest(0);
		NaturalNumber ref = this.constructorRef(0);

		int remainderTest = test.divideBy10();
		int remainderRef = ref.divideBy10();

		// Check Postconditions
		assertEquals(ref, test);
		assertEquals(remainderTest, remainderRef);
	}

	// Tests DivideBy10 | singular digit
	@Test
	public final void testDivideBy10SingleDigit() {

		// Set Up Variables:
		NaturalNumber test = this.constructorTest(7);
		NaturalNumber ref = this.constructorRef(7);

		int remainderTest = test.divideBy10();
		int remainderRef = ref.divideBy10();

		// Check Postconditions
		assertEquals(ref, test);
		assertEquals(remainderTest, remainderRef);
	}

	// Tests isZero | this = maximum value
	@Test
	public final void testIsZeroIntMaxValue() {

		// Set Up Variables:
		NaturalNumber test = this.constructorTest(Integer.MAX_VALUE + "" + 5);
		NaturalNumber ref = this.constructorRef(Integer.MAX_VALUE + "" + 5);
		assertEquals(ref, test);

		boolean testIsZero = test.isZero();

		// Check Postconditions
		assertEquals(test, ref);
		assertEquals(testIsZero, false);
	}

	// Tests isZero | when this has a single digit
	@Test
	public final void testIsZeroSingleDigit() {

		// Set Up Variables:
		NaturalNumber test = this.constructorTest(3);
		NaturalNumber ref = this.constructorRef(3);
		assertEquals(ref, test);

		boolean testIsZero = test.isZero();

		// Check Postconditions
		assertEquals(test, ref);
		assertEquals(testIsZero, false);
	}

}
