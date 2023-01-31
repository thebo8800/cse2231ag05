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
	
	/*
     * Tests int constructor, boundary case: largest possible int
     */
    @Test
    public final void testIntConstructorIntMaxValue() {
       
        NaturalNumber test = this.constructorTest(Integer.MAX_VALUE);
        NaturalNumber ref = this.constructorRef(Integer.MAX_VALUE);
       
        assertEquals(ref, test);
    }
    
    @Test
    public final void testIntConstructorSingleDigitEven() {
        
        NaturalNumber test = this.constructorTest(4);
        NaturalNumber ref = this.constructorRef(4);
        
        assertEquals(ref, test);
    }
    
    @Test
    public final void testIntConstructorSingleDigitOdd() {
       
        NaturalNumber test = this.constructorTest(7);
        NaturalNumber ref = this.constructorRef(7);
        
        assertEquals(ref, test);
    }
    
    @Test
    public final void testMultiplyBy10ThisZero() {
        
        NaturalNumber test = this.constructorTest(0);
        NaturalNumber ref = this.constructorRef(0);
        
        // Call method
        test.multiplyBy10(5);
        ref.multiplyBy10(5);
        
        assertEquals(ref, test);
    }
    
    @Test
    public final void testMultiplyBy10_9() {
        
        NaturalNumber test = this.constructorTest(2);
        NaturalNumber ref = this.constructorRef(2);
       
        // Test Boundary Case with 9
        test.multiplyBy10(9);
        ref.multiplyBy10(9);
        
        assertEquals(ref, test);
    }
    
    @Test
    public final void testMultiplyBy10_0() {
        
        NaturalNumber test = this.constructorTest(2);
        NaturalNumber ref = this.constructorRef(2);
        
        // Test boundary case with 0
        test.multiplyBy10(0);
        ref.multiplyBy10(0);
        
        assertEquals(ref, test);
    }
    
    @Test
    public final void testMultiplyBy10MultipleDigit() {
        
        NaturalNumber test = this.constructorTest(589076);
        NaturalNumber ref = this.constructorRef(589076);
        
        test.multiplyBy10(4);
        ref.multiplyBy10(4);
        
        assertEquals(ref, test);
    }
    
    @Test
    public final void testDivideBy10_0() {
        
        NaturalNumber test = this.constructorTest(0);
        NaturalNumber ref = this.constructorRef(0);
        
        int remainderTest = test.divideBy10();
        int remainderRef = ref.divideBy10();
        
        assertEquals(ref, test);
        assertEquals(remainderTest, remainderRef);
    }
    
    @Test
    public final void testDivideBy10SingleDigit() {
        
        NaturalNumber test = this.constructorTest(7);
        NaturalNumber ref = this.constructorRef(7);
        
        int remainderTest = test.divideBy10();
        int remainderRef = ref.divideBy10();
        
        assertEquals(ref, test);
        assertEquals(remainderTest, remainderRef);
    }
    
    @Test
    public final void testIsZeroIntMaxValue() {
        
        NaturalNumber test = this.constructorTest(Integer.MAX_VALUE + "" + 5);
        NaturalNumber ref = this.constructorRef(Integer.MAX_VALUE + "" + 5);
        assertEquals(ref, test);
        
        boolean testIsZero = test.isZero();
        
        assertEquals(test, ref);
        assertEquals(testIsZero, false);
    }

    
    @Test
    public final void testIsZeroLongMaxValue() {
        
        NaturalNumber test = this.constructorTest(Long.MAX_VALUE + "" + 5);
        NaturalNumber ref = this.constructorRef(Long.MAX_VALUE + "" + 5);
        assertEquals(ref, test);
        
        boolean testIsZero = test.isZero();
        
        assertEquals(test, ref);
        assertEquals(testIsZero, false);
    }

    
    @Test
    public final void testIsZeroSingleDigit() {
        
        NaturalNumber test = this.constructorTest(3);
        NaturalNumber ref = this.constructorRef(3);
        assertEquals(ref, test);
        
        boolean testIsZero = test.isZero();
        
        assertEquals(test, ref);
        assertEquals(testIsZero, false);
    }

}
