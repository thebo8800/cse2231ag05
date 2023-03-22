import components.statement.Statement;
import components.statement.Statement1;

/**
 * Customized JUnit test fixture for {@code Statement2}.
 */
public class Statement2Test extends StatementTest {

    @Override
    protected final Statement constructorTest() {
        return new Statement2();
    }

    @Override
    protected final Statement constructorRef() {
        return new Statement1();
    }

}
