import static general.Constants.TEMPORARY_DIRECTORY;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import concurrentSolution.ConTemporaryFileHandler;
import general.Constants;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sequentialSolution.SeqTemporaryFileHandler;

public class SeqTemporaryFileHandlerTest {
  private SeqTemporaryFileHandler tempFileHandler;

  @BeforeEach
  void setUp() {
    tempFileHandler = new SeqTemporaryFileHandler();
  }

  @Test
  void testWriteAndReadTempData() {
    String courseKey = "AAA_2014J";
    Map<Integer, Integer> dataToWrite = new HashMap<>();
    dataToWrite.put(5, 100);
    dataToWrite.put(6, 150);

    tempFileHandler.writeTempData(courseKey, dataToWrite);

    Map<Integer, Integer> dataRead = tempFileHandler.readTempData(courseKey);
    assertNotNull(dataRead);
    assertEquals(200, dataRead.get(5));
    assertEquals(150, dataRead.get(6));

    // Clean up
    tempFileHandler.deleteAllTempFiles();
  }

  @Test
  void testWriteTempDataAppendsAndReadAggregates() {
    String courseKey = "ABA";
    tempFileHandler.writeTempData(courseKey, Map.of(20, 50));
    tempFileHandler.writeTempData(courseKey, Map.of(20, 75, 25, 250));

    Map<Integer, Integer> expectedData = Map.of(
        20, 125,
        25, 250
    );
    assertEquals(expectedData, tempFileHandler.readTempData(courseKey));
  }

  @Test
  void testReadTempDataNonExistentFile() {
    Map<Integer, Integer> result = tempFileHandler.readTempData("CCC");
    assertTrue(result.isEmpty());
  }

  @Test
  void testConstructorCreatesTempDirectory() {
    File dir = new File(Constants.TEMPORARY_DIRECTORY);
    assertTrue(dir.exists());
  }

  @Test
  void testWriteTempDataHandlesIOException() {
    File dir = new File(Constants.TEMPORARY_DIRECTORY);
    // This will cause IOException
    dir.setWritable(false);

    String courseKey = "CBC";
    assertDoesNotThrow(() -> tempFileHandler.writeTempData(courseKey, Map.of(5, 200)));

    dir.setWritable(true);
  }


  @Test
  void testDeleteAllTempFiles() {
    String courseKey = "AAA_2014J";
    Map<Integer, Integer> dataToWrite = new HashMap<>();
    dataToWrite.put(5, 100);

    tempFileHandler.writeTempData(courseKey, dataToWrite);
    tempFileHandler.deleteAllTempFiles();

    File tempFile = new File("temp/" + courseKey + ".tmp");
    assertFalse(tempFile.exists());
  }

  @Test
  void testEqualsAndHashCode() {
    ConTemporaryFileHandler tempFileHandler1 = new ConTemporaryFileHandler();
    ConTemporaryFileHandler tempFileHandler2 = new ConTemporaryFileHandler();
    ConTemporaryFileHandler tempFileHandler3 = new ConTemporaryFileHandler();

    assertEquals(tempFileHandler1, tempFileHandler2);
    assertEquals(tempFileHandler1.hashCode(), tempFileHandler2.hashCode());

    assertEquals(tempFileHandler1, tempFileHandler2);
    assertEquals(tempFileHandler1.hashCode(), tempFileHandler2.hashCode());

    assertEquals(tempFileHandler2, tempFileHandler3);
    assertEquals(tempFileHandler2.hashCode(), tempFileHandler3.hashCode());

    assertEquals(tempFileHandler3, tempFileHandler1);
    assertEquals(tempFileHandler3.hashCode(), tempFileHandler1.hashCode());

    tempFileHandler1.writeTempData("CDC", Map.of(15, 120));
    assertNotEquals(tempFileHandler1, tempFileHandler2);
  }

  @Test
  void testEqualsNull() {
    assertFalse(tempFileHandler.equals(null));
  }

  @Test
  void testDifferentClassObject() {
    assertFalse(tempFileHandler.equals(new Object()));
  }

  @Test
  void testToString() {
    String expected = "TemporaryFileHandler{tempFiles={}, tempDir='temp'}";
    assertEquals(expected, tempFileHandler.toString());
  }
}
