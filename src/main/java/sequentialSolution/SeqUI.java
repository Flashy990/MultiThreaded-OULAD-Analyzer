package sequentialSolution;

import static general.Constants.COURSES_DIRECTORY;
import static general.Constants.MAIN_ARGS_CAP;
import static general.Constants.SEQUENTIAL_SUCCESS;
import static general.Constants.SEQUENTIAL_USAGE;
import static general.Constants.STUDENTS_DIRECTORY;

import general.CourseManager;

import java.io.IOException;

/**
 * Handles the UI and main logic for the sequential solution.
 */
public class SeqUI {
    /**
     * Runs the sequential application.
     *
     * @param args Command-line arguments.
     *             args[0] - Input directory containing the CSV files.
     *             args[1] - Output directory for the result files.
     */
    public void run(String[] args) {
        if (args.length != MAIN_ARGS_CAP) {
            System.out.println(SEQUENTIAL_USAGE);
            return;
        }

        String inputDir = args[0];
        String outputDir = args[1];

        try {
            // Initialize CourseManager and load courses.csv
            CourseManager courseManager = new CourseManager();
            courseManager.loadCourses(inputDir + COURSES_DIRECTORY);

            // Initialize AggregationManager
            SeqAggregationManager aggregationManager = new SeqAggregationManager();

            // Process studentVle.csv sequentially
            SeqStudentVleProcessor processor = new SeqStudentVleProcessor();
            processor.processSequentially(inputDir + STUDENTS_DIRECTORY, aggregationManager);

            // Finalize and write aggregated data
            aggregationManager.finalizeAggregation();
            aggregationManager.writeAggregatedData(outputDir);

            System.out.println(SEQUENTIAL_SUCCESS);
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }
}