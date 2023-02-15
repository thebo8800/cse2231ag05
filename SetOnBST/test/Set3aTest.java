import components.set.Set;
import components.set.Set1L;

/**
 * Customized JUnit test fixture for {@code Set3a}.
 */
public class Set3aTest extends SetTest {

    @Override
    protected final Set<String> constructorTest() {
        return new Set3a<String>();
    }

    @Override
    protected final Set<String> constructorRef() {
        return new Set1L<String>();
    }

}
