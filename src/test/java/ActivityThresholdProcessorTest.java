import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import general.ActivityThresholdProcessor;
import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ActivityThresholdProcessorTest {
  private ActivityThresholdProcessor processor;

  @BeforeEach
  void setUp() {
    processor = new ActivityThresholdProcessor();
  }

  @Test
  void testProcess() throws IOException {
    int threshold = 100;
    String summaryFilesDir = "test/output"; // Ensure this directory and files exist
    String outputFilename = "test/activity-100.csv";

    processor.process(threshold, summaryFilesDir, outputFilename);

    File outputFile = new File(outputFilename);
    assertTrue(outputFile.exists());

    // Clean up
    outputFile.delete();
  }

  @Test
  void testProcessWithNonexistentDirectory() {
  }

  @Test
  void testProcessWithZeroThreshold() throws IOException {
    ActivityThresholdProcessor processor = new ActivityThresholdProcessor();
    int threshold = 0;
    String summaryFilesDir = "test/output";
    String outputFilename = "test/activity-0.csv";

    processor.process(threshold, summaryFilesDir, outputFilename);

    File outputFile = new File(outputFilename);
    assertTrue(outputFile.exists());

    // Clean up
    outputFile.delete();
  }
}
