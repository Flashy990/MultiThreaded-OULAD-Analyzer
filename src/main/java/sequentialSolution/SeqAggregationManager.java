package sequentialSolution;

import static general.Constants.COMMA;
import static general.Constants.CSV;
import static general.Constants.MAX_MEMORY_USAGE;
import static general.Constants.SLASH;
import static general.Constants.UNDERSCORE;

import general.StudentVleEntry;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Manages aggregation of click data.
 */
public class SeqAggregationManager {
    private Map<String, SeqDateRange> courseDateRanges;
    private Map<String, Map<Integer, Integer>> inMemoryData;
    private SeqTemporaryFileHandler tempFileHandler;
    private long MAX_MEMORY = MAX_MEMORY_USAGE;

    /**
     * Constructs an sequentialSolution.AggregationManager.
     */
    public SeqAggregationManager() {
        courseDateRanges = new ConcurrentHashMap<>();
        inMemoryData = new ConcurrentHashMap<>();
        tempFileHandler = new SeqTemporaryFileHandler();
    }

    /**
     * Aggregates a general.StudentVleEntry.
     *
     * @param entry The entry to aggregate.
     */
    public void aggregate(StudentVleEntry entry) {
        String courseKey = entry.getCodeModule() + UNDERSCORE + entry.getCodePresentation();

        // Update the date range
        SeqDateRange dateRange = courseDateRanges.computeIfAbsent(courseKey, k -> new SeqDateRange());
        dateRange.updateDate(entry.getDate());

        // Update click counts
        Map<Integer, Integer> dateClicksMap = inMemoryData.computeIfAbsent(courseKey, k -> new HashMap<>());
        dateClicksMap.merge(entry.getDate(), entry.getSumClicks(), Integer::sum);

        if (getCurrentMemoryUsage() > MAX_MEMORY_USAGE) {
            tempFileHandler.writeTempData(courseKey, dateClicksMap);
            inMemoryData.remove(courseKey);
        }
    }

    /**
     * Calculates the current memory usage of in-memory data.
     *
     * @return The estimated memory usage in bytes.
     */
    private long getCurrentMemoryUsage() {
        return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    }

    /**
     * Finalizes the aggregation by flushing in-memory data to temporary files.
     */
    public void finalizeAggregation() {
        for (Map.Entry<String, Map<Integer, Integer>> entry : inMemoryData.entrySet()) {
            tempFileHandler.writeTempData(entry.getKey(), entry.getValue());
        }

        inMemoryData.clear();
    }

    /**
     * Writes the aggregated data to output files.
     *
     * @param outputDir The directory to write output files.
     */
    public void writeAggregatedData(String outputDir) {
        for (String courseKey : courseDateRanges.keySet()) {
            try {
                // Retrieve date range
                SeqDateRange dateRange = courseDateRanges.get(courseKey);
                int minDate = dateRange.getMinDate();
                int maxDate = dateRange.getMaxDate();

                // Initialize date map with zeros
                Map<Integer, Integer> dateClicksMap = new TreeMap<>();
                for (int date = minDate; date <= maxDate; date++) {
                    dateClicksMap.put(date, 0);
                }

                // Merge data from temporary files
                Map<Integer, Integer> tempData = tempFileHandler.readTempData(courseKey);
                if (tempData != null) {
                    for (Map.Entry<Integer, Integer> entry : tempData.entrySet()) {
                        dateClicksMap.put(entry.getKey(), entry.getValue());
                    }
                }

                // Write to output file
                String outputFilename = outputDir + SLASH + courseKey + CSV;
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilename))) {
                    for (Map.Entry<Integer, Integer> entry : dateClicksMap.entrySet()) {
                        writer.write(entry.getKey() + COMMA + entry.getValue());
                        writer.newLine();
                    }
                }
            }

            catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Clean up temporary files
        tempFileHandler.deleteAllTempFiles();
    }

    /**
     * Returns the in-memory data for testing purposes.
     *
     * @return The in-memory data map.
     */
    public Map<String, Map<Integer, Integer>> getInMemoryData() {
        return inMemoryData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SeqAggregationManager that = (SeqAggregationManager) o;
        return MAX_MEMORY_USAGE == that.MAX_MEMORY &&
            courseDateRanges.equals(that.courseDateRanges) &&
            inMemoryData.equals(that.inMemoryData) &&
            tempFileHandler.equals(that.tempFileHandler);
    }

    // Overridden hashCode method
    @Override
    public int hashCode() {
        int result = courseDateRanges.hashCode();
        result = 31 * result + inMemoryData.hashCode();
        result = 31 * result + tempFileHandler.hashCode();
        result = 31 * result + Long.hashCode(MAX_MEMORY_USAGE);
        return result;
    }

    // Overridden toString method
    @Override
    public String toString() {
        return "AggregationManager{" +
            "courseDateRanges=" + courseDateRanges +
            ", inMemoryData=" + inMemoryData +
            ", tempFileHandler=" + tempFileHandler +
            ", MAX_MEMORY_USAGE=" + MAX_MEMORY_USAGE +
            '}';
    }
}