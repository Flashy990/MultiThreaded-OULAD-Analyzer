package sequentialSolution;

import static general.Constants.COMMA;
import static general.Constants.SLASH;
import static general.Constants.TEMPORARY_DIRECTORY;
import static general.Constants.TMP;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Handles temporary files used during aggregation.
 */
public class SeqTemporaryFileHandler {
    private Map<String, File> tempFiles;
    private final String TEMP = TEMPORARY_DIRECTORY;

    /**
     * Constructs a sequentialSolution.TemporaryFileHandler.
     */
    public SeqTemporaryFileHandler() {
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
    public synchronized void writeTempData(String courseKey, Map<Integer, Integer> dateClicksMap) {
        try {
            File tempFile = tempFiles.computeIfAbsent(courseKey, k -> new File(TEMPORARY_DIRECTORY + SLASH + k + TMP));
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile, true))) {
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

    /**
     * Reads temporary data for a courseKey.
     *
     * @param courseKey The course key.
     * @return A map of date to total clicks.
     */
    public Map<Integer, Integer> readTempData(String courseKey) {
        Map<Integer, Integer> data = new HashMap<>();
        File tempFile = tempFiles.get(courseKey);

        if (tempFile != null && tempFile.exists()) {
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

        SeqTemporaryFileHandler that = (SeqTemporaryFileHandler) o;
        return tempFiles.equals(that.tempFiles) &&
            TEMPORARY_DIRECTORY.equals(that.TEMP);
    }

    // Overridden hashCode method
    @Override
    public int hashCode() {
        int result = tempFiles.hashCode();
        result = 31 * result + TEMPORARY_DIRECTORY.hashCode();
        return result;
    }

    // Overridden toString method
    @Override
    public String toString() {
        return "TemporaryFileHandler{" +
            "tempFiles=" + tempFiles +
            ", tempDir='" + TEMPORARY_DIRECTORY + '\'' +
            '}';
    }
}