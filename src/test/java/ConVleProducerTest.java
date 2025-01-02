import concurrentSolution.ConAggregationManager;
import concurrentSolution.ConVleConsumer;
import concurrentSolution.ConVleProducer;
import general.StudentVleEntry;
import java.util.concurrent.ArrayBlockingQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.jupiter.api.Assertions.*;

public class ConVleProducerTest {

    private ConVleProducer producer1;
    private ConVleProducer producer2;
    private ConVleProducer producer3;
    private ConVleProducer producerNotEquals1;
    private ConVleProducer producerNotEquals2;
    private ConVleProducer producerNotEquals3;

    @BeforeEach
    void setUp() {
        String filename = "filename1";
        String filenameNotEquals = "filenameNotEquals";
        BlockingQueue<StudentVleEntry> queue = new LinkedBlockingQueue<>();
        BlockingQueue<StudentVleEntry> queueNotEquals = new ArrayBlockingQueue<>(10);
        int numConsumers = 1;
        int numConsumersNotEquals = 10;

        producer1 = new ConVleProducer(filename, queue, numConsumers);
        producer2 = new ConVleProducer(filename, queue, numConsumers);
        producer3 = new ConVleProducer(filename, queue, numConsumers);
        producerNotEquals1 = new ConVleProducer(filenameNotEquals, queue, numConsumers);
        producerNotEquals2 = new ConVleProducer(filename, queueNotEquals, numConsumers);
        producerNotEquals3 = new ConVleProducer(filename, queue, numConsumersNotEquals);
    }

    @Test
    void testProducer() throws InterruptedException {
        BlockingQueue<StudentVleEntry> queue = new LinkedBlockingQueue<>();
        String filename = "input/studentVle.csv"; // Use a small test file
        int numConsumers = 1;

        ConVleProducer producer = new ConVleProducer(filename, queue, numConsumers);
        producer.start();
        producer.join();

        // Check that the queue contains entries and the poison pills
        int poisonPillCount = 0;
        while (!queue.isEmpty()) {
            StudentVleEntry entry = queue.take();
            if ("POISON".equals(entry.getCodeModule())) {
                poisonPillCount++;
            } else {
                assertNotNull(entry.getCodeModule());
            }
        }
        assertNotEquals(numConsumers, poisonPillCount, "Number of poison pills should equal the number of consumers");
    }

    @Test
    void testEqualsBasic() {
        assertTrue(producer1.equals(producer2));
        assertFalse(producer1.equals(producerNotEquals1));
    }

    @Test
    void testEqualsNull() {
        assertFalse(producer1.equals(null));
    }

    @Test
    void testEquals1() {
        assertTrue(producer1.equals(producer1));
    }

    @Test
    void testEquals2() {
        assertTrue(producer1.equals(producer2));
        assertTrue(producer2.equals(producer1));
    }

    @Test
    void testEquals3() {
        assertTrue(producer1.equals(producer2));
        assertTrue(producer2.equals(producer3));
        assertTrue(producer3.equals(producer1));
    }

    @Test
    void testDifferentClassObject() {
        assertFalse(producer1.equals(new Object()));
    }

    @Test
    void testDifferentArgs() {
        assertFalse(producer1.equals(producerNotEquals1));
        assertFalse(producer1.equals(producerNotEquals2));
        assertFalse(producer1.equals(producerNotEquals3));
    }

    @Test
    void testHashCodeBasic() {
        assertEquals(producer1.hashCode(), producer2.hashCode());
        assertNotEquals(producer1.hashCode(), producerNotEquals1.hashCode());
    }

    @Test
    void testToStringBasic() {
        assertEquals(producer1.toString(), producer2.toString());
        assertNotEquals(producer1.toString(), producerNotEquals1.toString());
    }
    @Test
    void testToString() {
      String expectedNumComsumers = "numConsumers=1";
      String expectedFilename = "filename='filename1'";
      assertTrue(producer1.toString().contains(expectedNumComsumers));
      assertTrue(producer1.toString().contains(expectedFilename));
    }
}
