import general.CSVParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

class CSVParserTest {

  private CSVParser parser;

  @BeforeEach
  void setUp() throws IOException {
    parser = new CSVParser("input/courses.csv");
  }

  @Test
  void testReadHeader() throws IOException {
    parser.readHeader();
    assertEquals(0, parser.getColumnIndex("code_module"));
    assertEquals(1, parser.getColumnIndex("code_presentation"));
  }

  @Test
  void testReadNextRecord() throws IOException {
    parser.readHeader();
    String[] record = parser.readNextRecord();
    assertNotNull(record);
    assertEquals("AAA", record[parser.getColumnIndex("code_module")]);
  }

  @Test
  void testReadPastEndOfFile() throws IOException {
    parser.readHeader();
    while (parser.readNextRecord() != null) {
      // Read all records
    }
    assertNull(parser.readNextRecord());
  }

  @Test
  void testCloseParser() throws IOException {
    parser.close();
    assertThrows(IOException.class, () -> {
      parser.readNextRecord();
    });
  }
}
