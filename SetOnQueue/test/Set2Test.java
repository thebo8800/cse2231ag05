import components.set.Set;
import components.set.Set1L;

/**
 * Customized JUnit test fixture for {@code Set2}.
 */
public class Set2Test extends SetTest {

    @Override
    protected final Set<String> constructorTest() {
        return new Set2<String>();
    }

    @Override
    protected final Set<String> constructorRef() {
        return new Set1L<String>();
    }

}
