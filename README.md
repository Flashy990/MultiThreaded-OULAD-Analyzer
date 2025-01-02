# MultiThreaded OULAD Analyzer

This project processes the Open University Learning Analytics Dataset (OULAD) to analyze student interactions with online course materials. It generates summary files for each course presentation, detailing the total number of clicks per day. The project includes both sequential and concurrent implementations, with the concurrent version utilizing a producer-consumer model for enhanced performance.

## Features

- **Sequential Processing**: Reads and processes `courses.csv` and `studentVle.csv` to produce summary files for each course presentation.

- **Concurrent Processing**: Employs a multithreaded producer-consumer approach to process the dataset concurrently, improving efficiency.

- **High Activity Days Identification**: Identifies days with high student activity based on a user-defined threshold and generates a summary file listing these days.

## Prerequisites

- **Java Development Kit (JDK)**: Ensure JDK 8 or higher is installed.

- **Gradle**: Build and dependency management tool.

- **OULAD Dataset**: Download the dataset from [OULAD](https://analyse.kmi.open.ac.uk/open_dataset) and place `courses.csv` and `studentVle.csv` in a directory on your system.

## Setup

1. **Clone the Repository**:

   ```bash
   git clone https://github.com/Flashy990/MultiThreaded-OULAD-Analyzer.git
   ```

2. **Navigate to the Project Directory**:

   ```bash
   cd MultiThreaded-OULAD-Analyzer
   ```

3. **Build the Project**:

   ```bash
   ./gradlew build
   ```

## Running the Program

The program can be executed using an Integrated Development Environment (IDE) like IntelliJ IDEA or via the command line.

### Using IntelliJ IDEA

1. **Open the Project**: Launch IntelliJ IDEA and open the project directory.

2. **Configure Run/Debug Configurations**:
   - Go to **Run > Edit Configurations**.
   - Add a new **Application** configuration.
   - Set the **Main Class** to the desired entry point:
     - For the sequential version: `sequentialSolution.SequentialMain`
     - For the concurrent version: `concurrentSolution.ConcurrentMain`
   - Set the **Program Arguments**:
     - First argument: Path to the directory containing `courses.csv` and `studentVle.csv`.
     - Second argument (optional): Activity threshold for identifying high activity days.

3. **Run the Program**: Click the **Run** button to execute the program.

### Using the Command Line

1. **Navigate to the Build Directory**:

   ```bash
   cd build/libs
   ```

2. **Execute the Program**:

   ```bash
   java -cp MultiThreaded-OULAD-Analyzer.jar packageName.MainClassName /path/to/data [threshold]
   ```

   - Replace `packageName.MainClassName` with the appropriate main class:
     - For the sequential version: `sequentialSolution.SequentialMain`
     - For the concurrent version: `concurrentSolution.ConcurrentMain`
   - Replace `/path/to/data` with the path to the directory containing `courses.csv` and `studentVle.csv`.
   - Replace `[threshold]` with the optional activity threshold value.

## Output

- **Summary Files**: For each course presentation, a CSV file named `code_module_code_presentation.csv` is generated, containing two columns: `date` and `total_clicks`.

- **High Activity Days File**: If a threshold is provided, a file named `activity-threshold.csv` is created, listing days where total clicks meet or exceed the threshold.

## Testing

- **Test Classes**: Located in the `src/test/java` directory, following the naming convention `ClassNameTest.java`.

- **Test Coverage**:  Above 70% code and branch coverage.

- **Running Tests**:

  ```bash
  ./gradlew test
  ```

## Documentation

- **Javadoc**: Generate API documentation using the following command:

  ```bash
  ./gradlew javadoc
  ```

- **UML Class Diagrams**: Included in the project directory as PDF or image files.

## Acknowledgments

This project utilizes the Open University Learning Analytics Dataset (OULAD).

For more information, visit the [OULAD website](https://analyse.kmi.open.ac.uk/open_dataset). 