import components.program.Program;
import components.program.Program1;

/**
 * Customized JUnit test fixture for {@code Program2}.
 */
public class Program2Test extends ProgramTest {

    @Override
    protected final Program constructorTest() {
        return new Program2();
    }

    @Override
    protected final Program constructorRef() {
        return new Program1();
    }

}
