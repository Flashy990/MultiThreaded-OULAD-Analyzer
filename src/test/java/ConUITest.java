import concurrentSolution.ConUI;
import java.io.File;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class ConUITest {
    private static final String TEST_INPUT_DIR = "test/input/";
    private static final String TEST_OUTPUT_DIR = "test/output/";
    private static final String INVALID_THRESHOLD = "invalid_threshold";
    private static final String THRESHOLD_ZERO = "0";
    private static final String THRESHOLD_HUNDRED = "100";
    private static final String INVALID_OUTPUT_DIR = "invalid_output_dir";

    @BeforeEach
    void setUp() {
        // In case there are some leftover files in the output directory,
        // clear them before running tests
        File outputDir = new File(TEST_OUTPUT_DIR);
        if (outputDir.exists()) {
            for (File file : outputDir.listFiles()) {
                file.delete();
            }
        }
    }

    @Test
    void testRunWithNoArgs() {
        ConUI app = new ConUI();
        String[] args = {};
        assertDoesNotThrow(() -> app.run(args));
    }

    @Test
    void testRunWithMissingArgs() {
        ConUI app = new ConUI();
        String[] args = {TEST_INPUT_DIR};
        assertDoesNotThrow(() -> app.run(args));
    }

    @Test
    void testRunWithInvalidThreshold() {
        ConUI app = new ConUI();
        String[] args = {TEST_INPUT_DIR, TEST_OUTPUT_DIR, INVALID_THRESHOLD};
        assertDoesNotThrow(() -> app.run(args));
    }

    @Test
    void testRunWithValidArgsNoThreshold() {
        ConUI app = new ConUI();
        String[] args = {TEST_INPUT_DIR, TEST_OUTPUT_DIR};
        assertDoesNotThrow(() -> app.run(args));
    }

    @Test
    void testRunWithValidArgsAndThresholdZero() {
        ConUI app = new ConUI();
        String[] args = {TEST_INPUT_DIR, TEST_OUTPUT_DIR, THRESHOLD_ZERO};
        assertDoesNotThrow(() -> app.run(args));
    }

    @Test
    void testRun_WithInvalidOutputDirectory() {
        ConUI app = new ConUI();
        String[] args = {TEST_INPUT_DIR, INVALID_OUTPUT_DIR};
        assertDoesNotThrow(() -> app.run(args));
    }

    @Test
    void testRunWithValidArgs() {
        ConUI app = new ConUI();
        String[] args = {TEST_INPUT_DIR, TEST_OUTPUT_DIR, THRESHOLD_HUNDRED};
        assertDoesNotThrow(() -> app.run(args));
    }
}
