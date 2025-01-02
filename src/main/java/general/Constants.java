package general;

/**
 * A class containing constant values used throughout the application
 */
public final class Constants {
  /** Maximum memory usage allowed in bytes (500 MB). */
  public static final long MAX_MEMORY_USAGE = 500 * 1024 * 1024;

  /** A string constant representing an underscore ("_"). */
  public static final String UNDERSCORE = "_";

  /** A string constant representing a comma (","). */
  public static final String COMMA = ",";

  /** A string constant representing a forward slash ("/"). */
  public static final String SLASH = "/";

  /** A string constant representing the CSV file extension (".csv"). */
  public static final String CSV = ".csv";

  /** A string constant representing the temporary file extension (".tmp"). */
  public static final String TMP = ".tmp";

  /** Default size for a blocking queue. */
  public static final int BLOCKING_QUEUE_SIZE = 10000;

  /** Message indicating that processing was interrupted. */
  public static final String PROCESSING_INTERRUPTED = "Processing interrupted";

  /** Directory name used for storing temporary files. */
  public static final String TEMPORARY_DIRECTORY = "temp";

  /** The expected number of command-line arguments for the main program. */
  public static final int MAIN_ARGS_CAP = 2;

  /** Usage instructions for running the concurrent version of the program. */
  public static final String CONCURRENCY_USAGE = "Usage: java concurrent.MainClass <inputDir> <outputDir> [threshold]";

  /** Usage instructions for running the sequential version of the program. */
  public static final String SEQUENTIAL_USAGE = "Usage: java sequential.MainClass <inputDir> <outputDir>";

  /** Maximum allowable number of threads for concurrent processing. */
  public static final int CONCURRENCY_CAP = 3;

  /** Error message for invalid threshold values. */
  public static final String INVALID_THRESHOLD = "Invalid threshold value. It must be an integer.";

  /** Path to the courses directory file ("/courses.csv"). */
  public static final String COURSES_DIRECTORY = "/courses.csv";

  /** Path to the students directory file ("/studentVle.csv"). */
  public static final String STUDENTS_DIRECTORY = "/studentVle.csv";

  /** Message indicating successful completion of concurrent processing. */
  public static final String CONCURRENCY_SUCCESS = "Concurrent processing completed successfully.";

  /** Message indicating successful completion of sequential processing. */
  public static final String SEQUENTIAL_SUCCESS = "Sequential processing completed successfully.";

  /** Prefix for activity-related files ("/activity-"). */
  public static final String ACTIVITY = "/activity-";

  /** Special "poison" value used to signal termination in concurrent processing. */
  public static final String POISON = "POISON";

  /** Special temporary value used to represent a poison marker in queues. */
  public static final int QUEUE_TEMP_POISON = -1;

  /** Column name for "code_module" in CSV files. */
  public static final String CODE_MODULE = "code_module";

  /** Column name for "code_presentation" in CSV files. */
  public static final String CODE_PRESENTATION = "code_presentation";

  /** Column name for "date" in virtual learning environment (VLE) data. */
  public static final String VLE_DATE = "date";

  /** Column name for "sum_click" in VLE data, representing total clicks. */
  public static final String VLE_CLICKS = "sum_click";

  /** An empty string constant. */
  public static final String NOTHING = "";

  /** Column name for "module_presentation_length" in CSV files. */
  public static final String MODULE_PRES_LENGTH = "module_presentation_length";

  /** A string constant representing a backslash ("\""). */
  public static final String BACKSLASH = "\"";

  /** Part 1 of the error message for a missing column in CSV headers. */
  public static final String COL_EXCEPTION_P1 = "Column '";

  /** Part 2 of the error message for a missing column in CSV headers. */
  public static final String COL_EXCEPTION_P2 = "' not found in CSV header";

  /** Message indicating that a file is empty. */
  public static final String FILE_EMPTY = "File is empty.";
}