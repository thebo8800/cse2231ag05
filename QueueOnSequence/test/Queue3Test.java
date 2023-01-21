import components.queue.Queue;
import components.queue.Queue1L;

/**
 * Customized JUnit test fixture for {@code Queue3}.
 */
public class Queue3Test extends QueueTest {

    @Override
    protected final Queue<String> constructorTest() {
        return new Queue3<String>();
    }

    @Override
    protected final Queue<String> constructorRef() {
        return new Queue1L<String>();
    }

}
