import concurrentSolution.ConAggregationManager;
import concurrentSolution.ConVleConsumer;
import general.StudentVleEntry;
import java.util.concurrent.ArrayBlockingQueue;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ConVleConsumerTest {
    @Test
    void testConsumer() throws InterruptedException {
        BlockingQueue<StudentVleEntry> queue = new LinkedBlockingQueue<>();
        ConAggregationManager aggregationManager = new ConAggregationManager();

        // Add entries to the queue
        queue.put(new StudentVleEntry("AAA", "2014J", 5, 100));
        queue.put(new StudentVleEntry("AAA", "2014J", 6, 200));
        queue.put(new StudentVleEntry("POISON", "POISON", -1, -1));

        ConVleConsumer consumer = new ConVleConsumer(queue, aggregationManager);
        consumer.start();
        consumer.join();

        // Verify the aggregation
        Map<String, ConcurrentMap<Integer, Integer>> inMemoryData = aggregationManager.getInMemoryData();
        ConcurrentMap<Integer, Integer> dateClicksMap = inMemoryData.get("AAA_2014J");
        assertNotNull(dateClicksMap);
        assertEquals(100, dateClicksMap.get(5));
        assertEquals(200, dateClicksMap.get(6));
    }
    @Test
    void testEqualsAndHashCode() {
        BlockingQueue<StudentVleEntry> queue = new LinkedBlockingQueue<>();
        ConAggregationManager aggregationManager1 = new ConAggregationManager();

        ConVleConsumer consumer1 = new ConVleConsumer(queue, aggregationManager1);
        ConVleConsumer consumer2 = new ConVleConsumer(queue, aggregationManager1);

        assertEquals(consumer1, consumer2);
        assertEquals(consumer1.hashCode(), consumer2.hashCode());

        BlockingQueue<StudentVleEntry> queueNotEquals = new ArrayBlockingQueue<>(10);
        ConAggregationManager aggregationManager2 = new ConAggregationManager();


        ConVleConsumer consumerNotEquals = new ConVleConsumer(queueNotEquals, aggregationManager1);

        assertNotEquals(consumer1, consumerNotEquals);
        assertNotEquals(consumer1.hashCode(), consumerNotEquals.hashCode());
    }

    @Test
    void testToString() {
        BlockingQueue<StudentVleEntry> queue = new LinkedBlockingQueue<>();
        ConAggregationManager aggregationManager = new ConAggregationManager();
        ConVleConsumer consumer = new ConVleConsumer(queue, aggregationManager);
        String expected = "VleConsumer{queue=" + queue + ", aggManager=" + aggregationManager + "}";
        assertEquals(expected, consumer.toString());
    }
}
