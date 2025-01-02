package concurrentSolution;

import general.Constants;

import static general.Constants.SLASH;
import static general.Constants.COMMA;
import static general.Constants.TEMPORARY_DIRECTORY;
import static general.Constants.TMP;

import java.io.*;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Handles temporary files used during aggregation.
 */
public class ConTemporaryFileHandler {
    private ConcurrentMap<String, File> tempFiles;

    /**
     * Constructs a TemporaryFileHandler.
     */
    public ConTemporaryFileHandler() {
        tempFiles = new ConcurrentHashMap<>();
        File dir = new File(TEMPORARY_DIRECTORY);

        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    /**
     * Writes temporary data for a courseKey.
     *
     * @param courseKey      The course key.
     * @param dateClicksMap  The data to write.
     */
    public void writeTempData(String courseKey, Map<Integer, Integer> dateClicksMap) {
        try {
            File tempFile = tempFiles.computeIfAbsent(courseKey, k -> new File(TEMPORARY_DIRECTORY + SLASH + k + TMP));
            synchronized (tempFile) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile, true))) {
                    for (Map.Entry<Integer, Integer> entry : dateClicksMap.entrySet()) {
                        writer.write(entry.getKey() + COMMA + entry.getValue());
                        writer.newLine();
                    }
                }
            }
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads temporary data for a courseKey.
     *
     * @param courseKey The course key.
     * @return A map of date to total clicks.
     */
    public Map<Integer, Integer> readTempData(String courseKey) {
        Map<Integer, Integer> data = new ConcurrentHashMap<>();
        File tempFile = tempFiles.get(courseKey);

        if (tempFile != null && tempFile.exists()) {
            synchronized (tempFile) {
                try (BufferedReader reader = new BufferedReader(new FileReader(tempFile))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(COMMA);
                        int date = Integer.parseInt(parts[0]);
                        int sumClicks = Integer.parseInt(parts[1]);
                        data.merge(date, sumClicks, Integer::sum);
                    }
                }

                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return data;
    }

    /**
     * Deletes all temporary files.
     */
    public void deleteAllTempFiles() {
        for (File file : tempFiles.values()) {
            if (file.exists()) {
                file.delete();
            }
        }

        tempFiles.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConTemporaryFileHandler that = (ConTemporaryFileHandler) o;
        return Objects.equals(tempFiles, that.tempFiles) &&
                Objects.equals(TEMPORARY_DIRECTORY, TEMPORARY_DIRECTORY);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tempFiles, TEMPORARY_DIRECTORY);
    }

    @Override
    public String toString() {
        return "TemporaryFileHandler{" +
                "tempFiles=" + tempFiles +
                ", tempDir='" + TEMPORARY_DIRECTORY + '\'' +
                '}';
    }
}