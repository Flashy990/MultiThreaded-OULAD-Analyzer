import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import sequentialSolution.SeqDateRange;

public class SeqDateRangeTest {
  @Test
  void testUpdateDate() {
    SeqDateRange dateRange = new SeqDateRange();
    dateRange.updateDate(10);
    dateRange.updateDate(5);
    dateRange.updateDate(15);

    assertEquals(5, dateRange.getMinDate());
    assertEquals(15, dateRange.getMaxDate());
  }

  @Test
  void testEqualsAndHashCode() {
    SeqDateRange dr1 = new SeqDateRange();
    dr1.updateDate(5);
    dr1.updateDate(15);

    SeqDateRange dr2 = new SeqDateRange();
    dr2.updateDate(5);
    dr2.updateDate(15);

    SeqDateRange dr3 = new SeqDateRange();
    dr3.updateDate(6);
    dr3.updateDate(14);

    assertEquals(dr1, dr2);
    assertEquals(dr1.hashCode(), dr2.hashCode());
    assertNotEquals(dr1, dr3);
  }

  @Test
  void testToString() {
    SeqDateRange dateRange = new SeqDateRange();
    dateRange.updateDate(5);
    dateRange.updateDate(15);
    String expected = "SeqDateRange{minDate=5, maxDate=15}";
    assertEquals(expected, dateRange.toString());
  }
}
