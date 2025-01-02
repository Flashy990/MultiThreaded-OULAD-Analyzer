import general.StudentVleEntry;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StudentVleEntryTest {
  @Test
  void testEntryCreation() {
    StudentVleEntry entry = new StudentVleEntry("AAA", "2014J", 5, 100);
    assertEquals("AAA", entry.getCodeModule());
    assertEquals("2014J", entry.getCodePresentation());
    assertEquals(5, entry.getDate());
    assertEquals(100, entry.getSumClicks());
  }

  @Test
  void testEntryWithNegativeSumClicks() {
  }

  @Test
  void testEqualsAndHashCode() {
    StudentVleEntry entry1 = new StudentVleEntry("AAA", "2014J", 5, 100);
    StudentVleEntry entry2 = new StudentVleEntry("AAA", "2014J", 5, 100);
    assertEquals(entry1, entry2);
    assertEquals(entry1.hashCode(), entry2.hashCode());
  }

  @Test
  void testToString() {
    StudentVleEntry entry = new StudentVleEntry("AAA", "2014J", 5, 100);
    String expected = "StudentVleEntry{codeModule='AAA', codePresentation='2014J', date=5, sumClicks=100}";
    assertEquals(expected, entry.toString());
  }
}