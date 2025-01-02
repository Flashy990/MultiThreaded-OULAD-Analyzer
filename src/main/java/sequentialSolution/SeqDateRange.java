package sequentialSolution;

/**
 * Represents the date range (min and max dates) for a course presentation.
 */
public class SeqDateRange {
    private int minDate = Integer.MAX_VALUE;
    private int maxDate = Integer.MIN_VALUE;

    /**
     * Updates the date range based on the given date.
     *
     * @param date The date to consider.
     */
    public synchronized void updateDate(int date) {
        if (date < minDate) {
            minDate = date;
        }

        if (date > maxDate) {
            maxDate = date;
        }
    }

    /**
     * Gets the minimum date in the range.
     *
     * @return The minimum date.
     */
    public int getMinDate() {
        return minDate;
    }

    /**
     * Gets the maximum date in the range.
     *
     * @return The maximum date.
     */
    public int getMaxDate() {
        return maxDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SeqDateRange dateRange = (SeqDateRange) o;
        return minDate == dateRange.minDate && maxDate == dateRange.maxDate;
    }

    // Overridden hashCode method
    @Override
    public int hashCode() {
        int result = minDate;
        result = 31 * result + maxDate;
        return result;
    }

    // Overridden toString method
    @Override
    public String toString() {
        return "SeqDateRange{" +
                "minDate=" + minDate +
                ", maxDate=" + maxDate +
                '}';
    }
}