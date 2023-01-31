import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.naturalnumber.NaturalNumber;

/**
 * JUnit test fixture for {@code NaturalNumber}'s constructors and kernel
 * methods.
 *
 * @author Put your name here
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
	 * @ensures constructorTest = i
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

	// TODO - add test cases for four constructors, multiplyBy10, divideBy10, isZero

	@Test
	public final void testDefault() {
		NaturalNumber m = this.constructorTest();
		NaturalNumber n = this.constructorRef();

		assertEquals(m, n);
	}

	@Test
	public final void testConstructorIntZero() {

		NaturalNumber n = this.constructorTest(0);
		NaturalNumber m = this.constructorRef(0);

		assertEquals(n, m);
	}

	@Test
	public final void testConstructorStringZero() {

		NaturalNumber n = this.constructorTest("0");
		NaturalNumber m = this.constructorRef("0");

		assertEquals(n, m);
	}

	@Test
	public final void testConstructorNaturalNumberZero() {

		NaturalNumber l = this.constructorRef();
		NaturalNumber n = this.constructorRef();
		NaturalNumber m = this.constructorTest(l);

		assertEquals(n, m);
		assertEquals(n, l);
	}

	@Test
	public final void testConstructorIntNumber() {

		NaturalNumber n = this.constructorTest(69420);
		NaturalNumber m = this.constructorRef(69420);

		assertEquals(n, m);
	}

	@Test
	public final void testConstructorStringNumber() {

		NaturalNumber n = this.constructorTest("69420");
		NaturalNumber m = this.constructorRef("69420");

		assertEquals(n, m);

	}

	@Test
	public final void testConstructorNaturalNumberNumber() {
		NaturalNumber l = this.constructorTest("69420");
		NaturalNumber m = this.constructorRef("69420");
		NaturalNumber n = this.constructorRef();

		assertEquals(n, m);
		assertEquals(n, l);
	}

}
