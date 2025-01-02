import general.StudentVleEntry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.util.Map;
import sequentialSolution.SeqAggregationManager;

import static org.junit.jupiter.api.Assertions.*;

class SeqAggregationManagerTest {

  private SeqAggregationManager aggregationManager;

  @BeforeEach
  void setUp() {
    aggregationManager = new SeqAggregationManager();
  }

  @Test
  void testAggregate() {
    StudentVleEntry entry1 = new StudentVleEntry("AAA", "2014J", 5, 100);
    StudentVleEntry entry2 = new StudentVleEntry("AAA", "2014J", 5, 150);

    aggregationManager.aggregate(entry1);
    aggregationManager.aggregate(entry2);

    Map<String, Map<Integer, Integer>> inMemoryData = aggregationManager.getInMemoryData();
    Map<Integer, Integer> dateClicksMap = inMemoryData.get("AAA_2014J");

    assertNotNull(dateClicksMap);
    assertEquals(250, dateClicksMap.get(5));
  }

  @Test
  void testFinalizeAggregation() {
    StudentVleEntry entry = new StudentVleEntry("AAA", "2014J", 5, 100);
    aggregationManager.aggregate(entry);

    aggregationManager.finalizeAggregation();

    Map<String, Map<Integer, Integer>> inMemoryData = aggregationManager.getInMemoryData();
    assertTrue(inMemoryData.isEmpty(), "inMemoryData should be empty after finalizeAggregation");
  }

  @Test
  void testEqualsAndHashCode() {
    SeqAggregationManager am1 = new SeqAggregationManager();
    SeqAggregationManager am2 = new SeqAggregationManager();

    assertEquals(am1, am2);
    assertEquals(am1.hashCode(), am2.hashCode());
  }

  @Test
  void testToString() {
    String expected = "AggregationManager{courseDateRanges={}, inMemoryData={}, tempFileHandler=TemporaryFileHandler{tempFiles={}, tempDir='temp'}, MAX_MEMORY_USAGE=524288000}";
    assertEquals(expected, aggregationManager.toString());
  }
}
