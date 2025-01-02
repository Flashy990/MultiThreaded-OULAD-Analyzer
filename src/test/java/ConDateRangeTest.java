import concurrentSolution.ConDateRange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConDateRangeTest {
    ConDateRange dateRange1;
    ConDateRange dateRange2;
    ConDateRange dateRange3;
    ConDateRange dateRangeNotEquals;
    @BeforeEach
    void setUp() {
        dateRange1 = new ConDateRange();
        dateRange2 = new ConDateRange();
        dateRange3 = new ConDateRange();
        dateRange1.updateDate(5);
        dateRange1.updateDate(15);

        dateRange2.updateDate(5);
        dateRange2.updateDate(15);

        dateRange3.updateDate(5);
        dateRange3.updateDate(15);

        dateRangeNotEquals = new ConDateRange();
    }

    @Test
    void testUpdateDateConcurrently() throws InterruptedException {
        ConDateRange dateRange = new ConDateRange();

        Runnable task1 = () -> {
            for (int i = 10; i >= 1; i--) {
                dateRange.updateDate(i);
            }
        };

        Runnable task2 = () -> {
            for (int i = 11; i <= 20; i++) {
                dateRange.updateDate(i);
            }
        };

        Thread thread1 = new Thread(task1);
        Thread thread2 = new Thread(task2);

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        assertEquals(1, dateRange.getMinDate());
        assertEquals(20, dateRange.getMaxDate());
    }

    @Test
    void testEqualsAndHashCode() {
        ConDateRange dr1 = new ConDateRange();
        dr1.updateDate(5);
        dr1.updateDate(15);

        ConDateRange dr2 = new ConDateRange();
        dr2.updateDate(5);
        dr2.updateDate(15);

        assertEquals(dr1, dr2);
        assertEquals(dr1.hashCode(), dr2.hashCode());
    }

    @Test
    void testEqualsBasic() {
        assertTrue(dateRange1.equals(dateRange2));
        assertFalse(dateRange1.equals(dateRangeNotEquals));
    }

    @Test
    void testEqualsNull() {
        assertFalse(dateRange1.equals(null));
    }

    @Test
    void testEquals1() {
        assertTrue(dateRange1.equals(dateRange1));
    }

    @Test
    void testEquals2() {
        assertTrue(dateRange1.equals(dateRange2));
        assertTrue(dateRange2.equals(dateRange1));
    }

    @Test
    void testEquals3() {
        assertTrue(dateRange1.equals(dateRange2));
        assertTrue(dateRange2.equals(dateRange3));
        assertTrue(dateRange3.equals(dateRange1));
    }

    @Test
    void testDifferentClassObject() {
        assertFalse(dateRange1.equals(new Object()));
    }

    @Test
    void testDifferentArgs() {
        assertFalse(dateRange1.equals(dateRangeNotEquals));
    }

    @Test
    void testHashCodeBasic() {
        assertEquals(dateRange1.hashCode(), dateRange2.hashCode());
        assertNotEquals(dateRange1.hashCode(), dateRangeNotEquals.hashCode());
    }


    @Test
    void testToString() {
        ConDateRange dateRange = new ConDateRange();
        dateRange.updateDate(5);
        dateRange.updateDate(15);

        String expected = "DateRange{minDate=5, maxDate=15}";
        assertEquals(expected, dateRange.toString());
    }
}
