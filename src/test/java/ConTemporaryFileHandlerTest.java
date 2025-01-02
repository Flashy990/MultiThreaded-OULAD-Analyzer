import static general.Constants.TEMPORARY_DIRECTORY;
import static org.junit.jupiter.api.Assertions.*;

import concurrentSolution.ConTemporaryFileHandler;
import general.Constants;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConTemporaryFileHandlerTest {


  private ConTemporaryFileHandler tempFileHandler;


  @BeforeEach
  void setup() {
    tempFileHandler = new ConTemporaryFileHandler();
  }

  @AfterEach
  void cleanup() throws IOException {
    // Clean up the temporary test directory
    File dir = new File(TEMPORARY_DIRECTORY);
    if (dir.exists()) {
      for (File file : dir.listFiles()) {
        file.delete();
      }
      dir.delete();
    }
  }

  @Test
  void testWriteTempDataAndReadTempData() {
    String courseKey = "BBB";
    Map<Integer, Integer> dataToWrite = Map.of(
        15, 150,
        20, 250
    );

    tempFileHandler.deleteAllTempFiles();
    tempFileHandler.writeTempData(courseKey, dataToWrite);

    Map<Integer, Integer> readData = tempFileHandler.readTempData(courseKey);
    assertEquals(dataToWrite, readData);

    File tempFile = new File(TEMPORARY_DIRECTORY + "/" + courseKey + Constants.TMP);
    assertTrue(tempFile.exists());
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
  void testDeleteAllTempFiles() {
    tempFileHandler.writeTempData("ABB", Map.of(30, 300));
    tempFileHandler.writeTempData("ABC", Map.of(40, 400));

    tempFileHandler.deleteAllTempFiles();

    File dir = new File(TEMPORARY_DIRECTORY);
    assertEquals(0, dir.listFiles().length);
    assertTrue(tempFileHandler.readTempData("ABB").isEmpty());
    assertTrue(tempFileHandler.readTempData("ABC").isEmpty());
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
    String result = tempFileHandler.toString();
    assertTrue(result.contains("tempFiles="));
    assertTrue(result.contains("tempDir="));
  }

}