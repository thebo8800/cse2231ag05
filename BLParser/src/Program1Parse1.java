import components.map.Map;
import components.program.Program;
import components.program.Program1;
import components.queue.Queue;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.statement.Statement;
import components.utilities.Reporter;
import components.utilities.Tokenizer;

/**
 * Layered implementation of secondary method {@code parse} for {@code Program}.
 *
 * @author Junbo Chen, Brett Emory
 *
 */
public final class Program1Parse1 extends Program1 {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Parses a single BL instruction from {@code tokens} returning the
     * instruction name as the value of the function and the body of the
     * instruction in {@code body}.
     *
     * @param tokens
     *            the input tokens
     * @param body
     *            the instruction body
     * @return the instruction name
     * @replaces body
     * @updates tokens
     * @requires <pre>
     * [<"INSTRUCTION"> is a prefix of tokens]  and
     *  [<Tokenizer.END_OF_INPUT> is a suffix of tokens]
     * </pre>
     * @ensures <pre>
     * if [an instruction string is a proper prefix of #tokens]  and
     *    [the beginning name of this instruction equals its ending name]  and
     *    [the name of this instruction does not equal the name of a primitive
     *     instruction in the BL language] then
     *  parseInstruction = [name of instruction at start of #tokens]  and
     *  body = [Statement corresponding to the block string that is the body of
     *          the instruction string at start of #tokens]  and
     *  #tokens = [instruction string at start of #tokens] * tokens
     * else
     *  [report an appropriate error message to the console and terminate client]
     * </pre>
     */
    private static String parseInstruction(Queue<String> tokens,
            Statement body) {
        assert tokens != null : "Violation of: tokens is not null";
        assert body != null : "Violation of: body is not null";
        assert tokens.length() > 0 && tokens.front().equals("INSTRUCTION") : ""
                + "Violation of: <\"INSTRUCTION\"> is proper prefix of tokens";

        //Get the name of the instruction and report
        //Valid or invalid
        tokens.dequeue();
        Reporter.assertElseFatalError(tokens.length() > 0,
                "Expected identifier ");
        String identifier = tokens.dequeue();
        Reporter.assertElseFatalError(Tokenizer.isIdentifier(identifier),
                "Instruction name " + identifier + " is invalid");

        //Take care of the Nonterminal symbol IS
        //Use assertions to determine validity
        Reporter.assertElseFatalError(
                tokens.length() > 0 && tokens.dequeue().equals("IS"),
                "Expected: IS");

        //Parse the body of the
        body.parseBlock(tokens);

        //Make sure the ending syntax is correct
        Reporter.assertElseFatalError(
                tokens.length() > 0 && tokens.dequeue().equals("END"),
                "Expected END");
        Reporter.assertElseFatalError(
                tokens.length() > 0 && tokens.dequeue().equals(identifier),
                "Expected " + identifier);

        return identifier;

    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Program1Parse1() {
        super();
    }

    /*
     * Public methods ---------------------------------------------------------
     */

    @Override
    public void parse(SimpleReader in) {
        assert in != null : "Violation of: in is not null";
        assert in.isOpen() : "Violation of: in.is_open";
        Queue<String> tokens = Tokenizer.tokens(in);
        this.parse(tokens);
    }

    @Override
    public void parse(Queue<String> tokens) {
        assert tokens != null : "Violation of: tokens is not null";
        assert tokens.length() > 0 : ""
                + "Violation of: Tokenizer.END_OF_INPUT is a suffix of tokens";

        //Check for empty tokens
        if (tokens.front().equals(Tokenizer.END_OF_INPUT)) {
            this.clear();
        } else {
            //Check for correct syntax, Must lead with PROGRAM
            Reporter.assertElseFatalError(tokens.dequeue().equals("PROGRAM"),
                    "Must lead with PROGAM");

            //Check if the name is an identifier
            Reporter.assertElseFatalError(
                    tokens.length() > 0
                            && Tokenizer.isIdentifier(tokens.front()),
                    "Program name must be an identifier");
            //Get the name
            String name = tokens.dequeue();

            //Check for correct "IS" syntax after name
            //When no error is thrown, dequeue the next token
            Reporter.assertElseFatalError(
                    tokens.length() > 0 && tokens.front().equals("IS"),
                    "Must begin with PROGRAM identifier IS");
            tokens.dequeue();

            //Get context and check syntax
            Map<String, Statement> context = this.newContext();
            while (tokens.front().equals("INSTRUCTION")) {
                Statement block = this.newBody();
                String identifier = parseInstruction(tokens, block);
                Reporter.assertElseFatalError(!context.hasKey(identifier),
                        "Instruction names have to be unique");
                context.add(identifier, block);
                Reporter.assertElseFatalError(tokens.length() > 0,
                        "Unexpected termination");
            }

            //Get the body of the program and check "BEGIN" syntax
            Reporter.assertElseFatalError(
                    tokens.length() > 0 && tokens.dequeue().equals("BEGIN"),
                    "Expected BEGIN");
            Statement body = this.newBody();
            body.parseBlock(tokens);

            //Check for the closing "END" syntax of the program body
            Reporter.assertElseFatalError(
                    tokens.length() > 0 && tokens.dequeue().equals("END"),
                    "Expected END");
            Reporter.assertElseFatalError(
                    tokens.length() > 0 && tokens.dequeue().equals(name),
                    "Expected " + name);

        }
    }

    /*
     * Main test method -------------------------------------------------------
     */

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        /*
         * Get input file name
         */
        out.print("Enter valid BL program file name: ");
        String fileName = in.nextLine();
        /*
         * Parse input file
         */
        out.println("*** Parsing input file ***");
        Program p = new Program1Parse1();
        SimpleReader file = new SimpleReader1L(fileName);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        p.parse(tokens);
        /*
         * Pretty print the program
         */
        out.println("*** Pretty print of parsed program ***");
        p.prettyPrint(out);

        in.close();
        out.close();
    }

}
