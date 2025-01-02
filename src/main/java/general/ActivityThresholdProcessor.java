package general;

import static general.Constants.COMMA;
import static general.Constants.CSV;
import static general.Constants.NOTHING;

import java.io.*;

/**
 * Processes summary files to identify high-activity days.
 */
public class ActivityThresholdProcessor {
    /**
     * Processes the summary files to find days when total clicks exceed the threshold.
     *
     * @param threshold       The activity threshold value.
     * @param summaryFilesDir The directory containing summary files.
     * @param outputFilename  The output file path.
     * @throws IOException If an I/O error occurs.
     */
    public void process(int threshold, String summaryFilesDir, String outputFilename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilename))) {

            File dir = new File(summaryFilesDir);
            File[] summaryFiles = dir.listFiles((d, name) -> name.endsWith(CSV));

            if (summaryFiles != null) {
                for (File file : summaryFiles) {
                    String modulePresentation = file.getName().replace(CSV, NOTHING);
                    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                        String line;

                        while ((line = reader.readLine()) != null) {
                            String[] parts = line.split(COMMA);
                            int date = Integer.parseInt(parts[0]);
                            int totalClicks = Integer.parseInt(parts[1]);

                            if (totalClicks >= threshold) {
                                writer.write(modulePresentation + COMMA + date + COMMA + totalClicks);
                                writer.newLine();
                            }
                        }
                    }
                }
            }
        }
    }
}