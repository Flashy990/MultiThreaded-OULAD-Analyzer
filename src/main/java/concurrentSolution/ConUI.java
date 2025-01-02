package concurrentSolution;

import static general.Constants.ACTIVITY;
import static general.Constants.CONCURRENCY_CAP;
import static general.Constants.CONCURRENCY_SUCCESS;
import static general.Constants.CONCURRENCY_USAGE;
import static general.Constants.COURSES_DIRECTORY;
import static general.Constants.CSV;
import static general.Constants.INVALID_THRESHOLD;
import static general.Constants.MAIN_ARGS_CAP;
import static general.Constants.STUDENTS_DIRECTORY;

import general.ActivityThresholdProcessor;
import general.CourseManager;

import java.io.IOException;

/**
 * Handles the UI and main logic for the concurrent solution.
 */
public class ConUI {
    /**
     * Runs the concurrent application.
     *
     * @param args Command-line arguments.
     *             args[0] - Input directory containing the CSV files.
     *             args[1] - Output directory for the result files.
     *             args[2] - (Optional) Threshold value for Part 3.
     */
    public void run(String[] args) {
        if (args.length < MAIN_ARGS_CAP) {
            System.out.println(CONCURRENCY_USAGE);
            return;
        }

        String inputDir = args[0];
        String outputDir = args[1];
        Integer threshold = null;

        if (args.length >= CONCURRENCY_CAP) {
            try {
                threshold = Integer.parseInt(args[MAIN_ARGS_CAP]);
            }

            catch (NumberFormatException e) {
                System.out.println(INVALID_THRESHOLD);
                return;
            }
        }

        try {
            // Initialize CourseManager and load courses.csv
            CourseManager courseManager = new CourseManager();
            courseManager.loadCourses(inputDir + COURSES_DIRECTORY);

            // Initialize AggregationManager
            ConAggregationManager aggregationManager = new ConAggregationManager();

            // Process studentVle.csv using concurrent processing
            ConStudentVleProcessor processor = new ConStudentVleProcessor();
            processor.processConcurrently(inputDir + STUDENTS_DIRECTORY, aggregationManager);

            // Finalize and write aggregated data
            aggregationManager.finalizeAggregation();
            aggregationManager.writeAggregatedData(outputDir);

            // Part 3: Identify high activity days
            if (threshold != null) {
                ActivityThresholdProcessor thresholdProcessor = new ActivityThresholdProcessor();
                thresholdProcessor.process(threshold, outputDir, outputDir + ACTIVITY + threshold + CSV);
            }

            System.out.println(CONCURRENCY_SUCCESS);
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }
}