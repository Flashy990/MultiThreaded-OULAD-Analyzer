import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import sequentialSolution.SeqUI;

public class SeqUITest {
  @Test
  void testRunWithInvalidArguments() {
    SeqUI app = new SeqUI();
    String[] args = {};
    // Since the run method doesn't return anything, we might need to capture the output
    // For simplicity, we can assert that no exceptions are thrown
    assertDoesNotThrow(() -> app.run(args));
  }

  @Test
  void testRunWithValidArguments() {
    SeqUI app = new SeqUI();
    String[] args = {"test/input", "test/output"};
    assertDoesNotThrow(() -> app.run(args));
  }
}
