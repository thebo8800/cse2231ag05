import components.queue.Queue;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.statement.Statement;
import components.statement.Statement1;
import components.utilities.Reporter;
import components.utilities.Tokenizer;

/**
 * Layered implementation of secondary methods {@code parse} and
 * {@code parseBlock} for {@code Statement}.
 *
 * @author Junbo Chen, Brett Emory
 *
 */
public final class Statement1Parse1 extends Statement1 {

	/*
	 * Private members --------------------------------------------------------
	 */

	/**
	 * Converts {@code c} into the corresponding {@code Condition}.
	 *
	 * @param c the condition to convert
	 * @return the {@code Condition} corresponding to {@code c}
	 * @requires [c is a condition string]
	 * @ensures parseCondition = [Condition corresponding to c]
	 */
	private static Condition parseCondition(String c) {
		assert c != null : "Violation of: c is not null";
		assert Tokenizer.isCondition(c) : "Violation of: c is a condition string";
		return Condition.valueOf(c.replace('-', '_').toUpperCase());
	}

	/**
	 * Parses an IF or IF_ELSE statement from {@code tokens} into {@code s}.
	 *
	 * @param tokens the input tokens
	 * @param s      the parsed statement
	 * @replaces s
	 * @updates tokens
	 * @requires
	 *
	 *           <pre>
	 * [<"IF"> is a prefix of tokens]  and
	 *  [<Tokenizer.END_OF_INPUT> is a suffix of tokens]
	 *           </pre>
	 *
	 * @ensures
	 *
	 *          <pre>
	 * if [an if string is a proper prefix of #tokens] then
	 *  s = [IF or IF_ELSE Statement corresponding to if string at start of #tokens]  and
	 *  #tokens = [if string at start of #tokens] * tokens
	 * else
	 *  [reports an appropriate error message to the console and terminates client]
	 *          </pre>
	 */
	private static void parseIf(Queue<String> tokens, Statement s) {
		assert tokens != null : "Violation of: tokens is not null";
		assert s != null : "Violation of: s is not null";
		assert tokens.length() > 0 && tokens.front().equals("IF")
				: "" + "Violation of: <\"IF\"> is proper prefix of tokens";

		// Starts with IF condition
		tokens.dequeue();
		Reporter.assertElseFatalError(tokens.length() > 0 && Tokenizer.isCondition(tokens.front()),
				"Expected condition");
		// Get the condition token
		String conditionToken = tokens.dequeue();
		Condition condition = parseCondition(conditionToken);

		// After IF condition syntactically comes THEN block
		Reporter.assertElseFatalError(tokens.length() > 0 && tokens.front().equals("THEN"), "Expected THEN");
		tokens.dequeue();
		Statement ifBlock = s.newInstance();
		Reporter.assertElseFatalError(tokens.length() > 0, "Unexpected termination");
		ifBlock.parseBlock(tokens);

		// Checks for ending syntax END IF or continue with ELSE syntax
		Reporter.assertElseFatalError(
				tokens.length() > 0 && (tokens.front().equals("ELSE") || tokens.front().equals("END")),
				"Expected ELSE or END IF");

		// COntinue to parse ELSE after IF condition ends
		if (tokens.front().equals("ELSE")) {
			tokens.dequeue();
			Statement elseBlock = s.newInstance();
			Reporter.assertElseFatalError(tokens.length() > 0, "Unexpected termination");
			elseBlock.parseBlock(tokens);
			Reporter.assertElseFatalError(
					tokens.length() > 1 && tokens.dequeue().equals("END") && tokens.dequeue().equals("IF"),
					"Expected END IF");
			s.assembleIfElse(condition, ifBlock, elseBlock);

		} else {
			// Only runs when it is Case IF ELSE, otherwise will terminate earlier in code
			tokens.dequeue();
			Reporter.assertElseFatalError(tokens.length() > 0 && tokens.dequeue().equals("IF"), "Expected END IF");
			s.assembleIf(condition, ifBlock);
		}
	}

	/**
	 * Parses a WHILE statement from {@code tokens} into {@code s}.
	 *
	 * @param tokens the input tokens
	 * @param s      the parsed statement
	 * @replaces s
	 * @updates tokens
	 * @requires
	 *
	 *           <pre>
	 * [<"WHILE"> is a prefix of tokens]  and
	 *  [<Tokenizer.END_OF_INPUT> is a suffix of tokens]
	 *           </pre>
	 *
	 * @ensures
	 *
	 *          <pre>
	 * if [a while string is a proper prefix of #tokens] then
	 *  s = [WHILE Statement corresponding to while string at start of #tokens]  and
	 *  #tokens = [while string at start of #tokens] * tokens
	 * else
	 *  [reports an appropriate error message to the console and terminates client]
	 *          </pre>
	 */
	private static void parseWhile(Queue<String> tokens, Statement s) {
		assert tokens != null : "Violation of: tokens is not null";
		assert s != null : "Violation of: s is not null";
		assert tokens.length() > 0 && tokens.front().equals("WHILE")
				: "" + "Violation of: <\"WHILE\"> is proper prefix of tokens";

		// Has 4 parts --> WHILE --> DO --> end --> build
		// WHILE condition
		tokens.dequeue();
		Reporter.assertElseFatalError(tokens.length() > 0 && Tokenizer.isCondition(tokens.front()),
				"Expected condition");
		String conditionToken = tokens.dequeue();
		Condition condition = parseCondition(conditionToken);

		// DO
		Reporter.assertElseFatalError(tokens.length() > 0 && tokens.front().equals("DO"), "Expected DO");
		tokens.dequeue();
		Statement whileBlock = s.newInstance();
		Reporter.assertElseFatalError(tokens.length() > 0, "Unexpected termination");
		whileBlock.parseBlock(tokens);

		// Ending syntax
		Reporter.assertElseFatalError(
				tokens.length() > 1 && tokens.dequeue().equals("END") && tokens.dequeue().equals("WHILE"),
				"Expected WHILE");

		// Build while
		s.assembleWhile(condition, whileBlock);

	}

	/**
	 * Parses a CALL statement from {@code tokens} into {@code s}.
	 *
	 * @param tokens the input tokens
	 * @param s      the parsed statement
	 * @replaces s
	 * @updates tokens
	 * @requires [identifier string is a proper prefix of tokens]
	 * @ensures
	 *
	 *          <pre>
	 * s =
	 *   [CALL Statement corresponding to identifier string at start of #tokens]  and
	 *  #tokens = [identifier string at start of #tokens] * tokens
	 *          </pre>
	 */
	private static void parseCall(Queue<String> tokens, Statement s) {
		assert tokens != null : "Violation of: tokens is not null";
		assert s != null : "Violation of: s is not null";
		assert tokens.length() > 0 && Tokenizer.isIdentifier(tokens.front())
				: "" + "Violation of: identifier string is proper prefix of tokens";

		// Get the name/identifier of the call and assemble
		String identifier = tokens.dequeue();
		s.assembleCall(identifier);

	}

	/*
	 * Constructors -----------------------------------------------------------
	 */

	/**
	 * No-argument constructor.
	 */
	public Statement1Parse1() {
		super();
	}

	/*
	 * Public methods ---------------------------------------------------------
	 */

	@Override
	public void parse(Queue<String> tokens) {
		assert tokens != null : "Violation of: tokens is not null";
		assert tokens.length() > 0 : "" + "Violation of: Tokenizer.END_OF_INPUT is a suffix of tokens";

		// No need to use switch based off kind because there are only three cases
		if (tokens.front().equals("IF")) {
			parseIf(tokens, this);
		} else if (tokens.front().equals("WHILE")) {
			parseWhile(tokens, this);
		} else if (Tokenizer.isIdentifier(tokens.front())) {
			parseCall(tokens, this);
		} else {
			Reporter.fatalErrorToConsole("Expected statement");
		}

	}

	@Override
	public void parseBlock(Queue<String> tokens) {
		assert tokens != null : "Violation of: tokens is not null";
		assert tokens.length() > 0 : "" + "Violation of: Tokenizer.END_OF_INPUT is a suffix of tokens";

		// this must be replaced
		this.newInstance();

		String nextToken = tokens.front();
		int pos = 0;

		// Statements begins with an identifier, if, or while
		while (Tokenizer.isIdentifier(nextToken) || nextToken.equals("IF") || nextToken.equals("WHILE")) {
			Statement toAdd = this.newInstance();

			// Parse and add each statement to block
			toAdd.parse(tokens);
			this.addToBlock(pos, toAdd);
			nextToken = tokens.front();
			pos++;
		}

	}

	/*
	 * Main test method -------------------------------------------------------
	 */

	/**
	 * Main method.
	 *
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		SimpleReader in = new SimpleReader1L();
		SimpleWriter out = new SimpleWriter1L();
		/*
		 * Get input file name
		 */
		out.print("Enter valid BL statement(s) file name: ");
		String fileName = in.nextLine();
		/*
		 * Parse input file
		 */
		out.println("*** Parsing input file ***");
		Statement s = new Statement1Parse1();
		SimpleReader file = new SimpleReader1L(fileName);
		Queue<String> tokens = Tokenizer.tokens(file);
		file.close();
		s.parse(tokens); // replace with parseBlock to test other method
		/*
		 * Pretty print the statement(s)
		 */
		out.println("*** Pretty print of parsed statement(s) ***");
		s.prettyPrint(out, 0);

		in.close();
		out.close();
	}

}
