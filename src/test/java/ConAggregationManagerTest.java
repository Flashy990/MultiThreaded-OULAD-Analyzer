import concurrentSolution.ConAggregationManager;
import general.StudentVleEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class ConAggregationManagerTest {
    private ConAggregationManager aggregationManager;
    private ConAggregationManager aggregationManager1;
    private ConAggregationManager aggregationManager2;
    private ConAggregationManager aggregationManager3;

    @BeforeEach
    void setUp() {
        aggregationManager = new ConAggregationManager();
        aggregationManager1 = new ConAggregationManager();
        aggregationManager2 = new ConAggregationManager();
        aggregationManager3 = new ConAggregationManager();
    }

    @Test
    void testAggregateConcurrently() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        Runnable task1 = () -> {
            StudentVleEntry entry = new StudentVleEntry("AAA", "2014J", 5, 100);
            aggregationManager.aggregate(entry);
        };

        Runnable task2 = () -> {
            StudentVleEntry entry = new StudentVleEntry("AAA", "2014J", 5, 200);
            aggregationManager.aggregate(entry);
        };

        executorService.submit(task1);
        executorService.submit(task2);

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.SECONDS);

        // Access internal data for verification
        Map<String, ConcurrentMap<Integer, Integer>> inMemoryData = aggregationManager.getInMemoryData();

        ConcurrentMap<Integer, Integer> dateClicksMap = inMemoryData.get("AAA_2014J");
        assertNotNull(dateClicksMap);
        assertEquals(300, dateClicksMap.get(5)); // 100 + 200
    }

    @Test
    void testFinalizeAggregation() {
        // Test the finalizeAggregation method
        // Add entries and ensure data is properly flushed and cleared
    }

    @Test
    void testToString() {
        String toStringResult = aggregationManager.toString();
        assertNotNull(toStringResult);
        assertFalse(toStringResult.isEmpty());
        assertTrue(toStringResult.contains("AggregationManager{"));
    }

    @Test
    void testEqualsBasic() {
        assertTrue(aggregationManager1.equals(aggregationManager2));
    }

    @Test
    void testEqualsNull() {
        assertFalse(aggregationManager1.equals(null));
    }

    @Test
    void testEquals1() {
        assertTrue(aggregationManager1.equals(aggregationManager1));
    }

    @Test
    void testEquals2() {
        assertTrue(aggregationManager1.equals(aggregationManager2));
        assertTrue(aggregationManager2.equals(aggregationManager1));
    }

    @Test
    void testEquals3() {
        assertTrue(aggregationManager1.equals(aggregationManager2));
        assertTrue(aggregationManager2.equals(aggregationManager3));
        assertTrue(aggregationManager3.equals(aggregationManager1));
    }

    @Test
    void testDifferentClassObject() {
        assertFalse(aggregationManager1.equals(new Object()));
    }

    @Test
    void testHashCodeBasic() {
        assertEquals(aggregationManager1.hashCode(), aggregationManager2.hashCode());
    }
}