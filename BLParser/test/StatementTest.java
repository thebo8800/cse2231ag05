import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.queue.Queue;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.statement.Statement;
import components.utilities.Tokenizer;

/**
 * JUnit test fixture for {@code Statement}'s constructor and kernel methods.
 *
 * @author Junbo Chen, Brett Emory
 *
 */
public abstract class StatementTest {

	/**
	 * The name of a file containing a sequence of BL statements.
	 */
	private static final String FILE_NAME_1 = "test/statement1.bl", FILE_NAME_2 = "test/statement2.bl";
	private static final String FILE_NAME_5 = "test/statement5.bl";
	private static final String FILE_NAME_4 = "test/statement4.bl";
	private static final String FILE_NAME_3 = "test/statement3.bl";

	/**
	 * Invokes the {@code Statement} constructor for the implementation under test
	 * and returns the result.
	 *
	 * @return the new statement
	 * @ensures constructorTest = compose((BLOCK, ?, ?), <>)
	 */
	protected abstract Statement constructorTest();

	/**
	 * Invokes the {@code Statement} constructor for the reference implementation
	 * and returns the result.
	 *
	 * @return the new statement
	 * @ensures constructorRef = compose((BLOCK, ?, ?), <>)
	 */
	protected abstract Statement constructorRef();

	/**
	 * Test of parse on syntactically valid input.
	 */
	@Test
	public final void testParseValidExample() {
		/*
		 * Setup
		 */
		Statement sRef = this.constructorRef();
		SimpleReader file = new SimpleReader1L(FILE_NAME_1);
		Queue<String> tokens = Tokenizer.tokens(file);
		sRef.parse(tokens);
		file.close();
		Statement sTest = this.constructorTest();
		file = new SimpleReader1L(FILE_NAME_1);
		tokens = Tokenizer.tokens(file);
		file.close();
		/*
		 * The call
		 */
		sTest.parse(tokens);
		/*
		 * Evaluation
		 */
		assertEquals(sRef, sTest);
	}

	/**
	 * Test of parse on syntactically invalid input.
	 */
	@Test(expected = RuntimeException.class)
	public final void testParseInvalid2() {
		/*
		 * Setup
		 */
		Statement sTest = this.constructorTest();
		SimpleReader file = new SimpleReader1L(FILE_NAME_2);
		Queue<String> tokens = Tokenizer.tokens(file);
		file.close();
		/*
		 * The call--should result in an error being caught
		 */
		sTest.parse(tokens);
	}

	@Test(expected = RuntimeException.class)
	public final void testParseInvalid3() {

		Statement sTest = this.constructorTest();
		SimpleReader file = new SimpleReader1L(FILE_NAME_3);
		Queue<String> tokens = Tokenizer.tokens(file);
		file.close();

		sTest.parse(tokens);

	}

	@Test
	public final void testParseValid4() {

		Statement sRef = this.constructorRef();
		SimpleReader file = new SimpleReader1L(FILE_NAME_4);
		Queue<String> tokens = Tokenizer.tokens(file);
		sRef.parse(tokens);
		file.close();
		Statement sTest = this.constructorTest();
		file = new SimpleReader1L(FILE_NAME_4);
		tokens = Tokenizer.tokens(file);
		file.close();

		sTest.parse(tokens);

		assertEquals(sRef, sTest);

	}

	@Test(expected = RuntimeException.class)
	public final void testParseInvalid5() {

		Statement sTest = this.constructorTest();
		SimpleReader file = new SimpleReader1L(FILE_NAME_5);
		Queue<String> tokens = Tokenizer.tokens(file);
		file.close();

		sTest.parse(tokens);
	}

}
