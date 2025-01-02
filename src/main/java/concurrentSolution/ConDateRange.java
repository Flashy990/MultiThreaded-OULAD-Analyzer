package concurrentSolution;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Represents the date range (min and max dates) for a course presentation.
 */
public class ConDateRange {
    private final AtomicInteger minDate = new AtomicInteger(Integer.MAX_VALUE);
    private final AtomicInteger maxDate = new AtomicInteger(Integer.MIN_VALUE);

    /**
     * Updates the date range based on the given date.
     *
     * @param date The date to consider.
     */
    public void updateDate(int date) {
        minDate.updateAndGet(current -> Math.min(current, date));
        maxDate.updateAndGet(current -> Math.max(current, date));
    }

    /**
     * Gets the minimum date in the range.
     *
     * @return The minimum date.
     */
    public int getMinDate() {
        return minDate.get();
    }

    /**
     * Gets the maximum date in the range.
     *
     * @return The maximum date.
     */
    public int getMaxDate() {
        return maxDate.get();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConDateRange dateRange = (ConDateRange) o;
        return minDate.get() == dateRange.minDate.get() && maxDate.get() == dateRange.maxDate.get();
    }

    // Overridden hashCode method
    @Override
    public int hashCode() {
        int result = minDate.get();
        result = 31 * result + maxDate.get();
        return result;
    }

    // Overridden toString method
    @Override
    public String toString() {
        return "DateRange{" +
                "minDate=" + minDate +
                ", maxDate=" + maxDate +
                '}';
    }
}