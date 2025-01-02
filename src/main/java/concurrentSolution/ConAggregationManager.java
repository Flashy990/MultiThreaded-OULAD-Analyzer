package concurrentSolution;

import static general.Constants.COMMA;
import static general.Constants.CSV;
import static general.Constants.MAX_MEMORY_USAGE;
import static general.Constants.SLASH;
import static general.Constants.UNDERSCORE;

import general.StudentVleEntry;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Manages aggregation of click data.
 */
public class ConAggregationManager {
    private ConcurrentMap<String, ConDateRange> courseDateRanges;
    private ConcurrentMap<String, ConcurrentMap<Integer, Integer>> inMemoryData;
    private ConTemporaryFileHandler tempFileHandler;
    private long MAX_MEMORY = MAX_MEMORY_USAGE;

    /**
     * Constructs an AggregationManager.
     */
    public ConAggregationManager() {
        courseDateRanges = new ConcurrentHashMap<>();
        inMemoryData = new ConcurrentHashMap<>();
        tempFileHandler = new ConTemporaryFileHandler();
    }

    /**
     * Aggregates a StudentVleEntry.
     *
     * @param entry The entry to aggregate.
     */
    public void aggregate(StudentVleEntry entry) {
        String courseKey = entry.getCodeModule() + UNDERSCORE + entry.getCodePresentation();

        // Update date range
        courseDateRanges.computeIfAbsent(courseKey, k -> new ConDateRange()).updateDate(entry.getDate());

        // Aggregate sumClicks
        ConcurrentMap<Integer, Integer> dateClicksMap = inMemoryData.computeIfAbsent(courseKey, k -> new ConcurrentHashMap<>());
        dateClicksMap.merge(entry.getDate(), entry.getSumClicks(), Integer::sum);

        // Check memory limit and write to temp file if necessary
        if (getCurrentMemoryUsage() > MAX_MEMORY_USAGE) {
            tempFileHandler.writeTempData(courseKey, dateClicksMap);
            inMemoryData.remove(courseKey);
        }
    }

    /**
     * Estimates the current memory usage of the in-memory data.
     *
     * @return The estimated memory usage in bytes.
     */
    private long getCurrentMemoryUsage() {
        // Simplified estimation
        return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    }

    /**
     * Finalizes the aggregation by flushing in-memory data to temporary files.
     */
    public void finalizeAggregation() {
        for (Map.Entry<String, ConcurrentMap<Integer, Integer>> entry : inMemoryData.entrySet()) {
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
                ConDateRange dateRange = courseDateRanges.get(courseKey);
                int minDate = dateRange.getMinDate();
                int maxDate = dateRange.getMaxDate();

                // Initialize date map with zeros
                Map<Integer, Integer> dateClicksMap = new ConcurrentHashMap<>();
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
                    for (int date = minDate; date <= maxDate; date++) {
                        int totalClicks = dateClicksMap.getOrDefault(date, 0);
                        writer.write(date + COMMA + totalClicks);
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
    public ConcurrentMap<String, ConcurrentMap<Integer, Integer>> getInMemoryData() {
        return inMemoryData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConAggregationManager that = (ConAggregationManager) o;
        return MAX_MEMORY_USAGE == that.MAX_MEMORY &&
                Objects.equals(courseDateRanges, that.courseDateRanges) &&
                Objects.equals(inMemoryData, that.inMemoryData) &&
                Objects.equals(tempFileHandler, that.tempFileHandler);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseDateRanges, inMemoryData, tempFileHandler, MAX_MEMORY_USAGE);
    }

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