import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.io.IOException;
import sequentialSolution.SeqAggregationManager;
import sequentialSolution.SeqStudentVleProcessor;

import static org.junit.jupiter.api.Assertions.*;

class SeqStudentVleProcessorTest {

  private SeqStudentVleProcessor processor;
  private SeqAggregationManager aggregationManager;

  @BeforeEach
  void setUp() {
    processor = new SeqStudentVleProcessor();
    aggregationManager = new SeqAggregationManager();
  }

  @Test
  void testProcessSequentially() throws IOException {
    String vleFilename = "input/studentVle.csv"; // Provide a small test CSV file

    processor.processSequentially(vleFilename, aggregationManager);

    Map<String, Map<Integer, Integer>> inMemoryData = aggregationManager.getInMemoryData();
    assertFalse(inMemoryData.isEmpty(), "Data should not be empty after processing");
  }
}
