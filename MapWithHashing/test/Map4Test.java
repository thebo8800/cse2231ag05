import components.map.Map;
import components.map.Map1L;

/**
 * Customized JUnit test fixture for {@code Map4} using default constructor.
 */
public class Map4Test extends MapTest {

    @Override
    protected final Map<String, String> constructorTest() {
        return new Map4<String, String>();
    }

    @Override
    protected final Map<String, String> constructorRef() {
        return new Map1L<String, String>();
    }

}
