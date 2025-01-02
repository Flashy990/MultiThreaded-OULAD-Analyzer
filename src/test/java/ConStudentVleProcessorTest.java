import concurrentSolution.ConAggregationManager;
import concurrentSolution.ConStudentVleProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class ConStudentVleProcessorTest {
    private ConStudentVleProcessor processor;
    private ConAggregationManager aggregationManager;

    @BeforeEach
    void setUp() {
        processor = new ConStudentVleProcessor();
        aggregationManager = new ConAggregationManager();
    }

    @Test
    void testProcessConcurrently() throws IOException {
        String vleFilename = "input/studentVle.csv"; // Provide a small test CSV file

        processor.processConcurrently(vleFilename, aggregationManager);

        Map<String, ConcurrentMap<Integer, Integer>> inMemoryData = aggregationManager.getInMemoryData();

        assertFalse(inMemoryData.isEmpty(), "Data should not be empty after processing");
    }
}