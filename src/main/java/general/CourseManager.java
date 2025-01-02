package general;

import static general.Constants.CODE_MODULE;
import static general.Constants.CODE_PRESENTATION;
import static general.Constants.MODULE_PRES_LENGTH;
import static general.Constants.UNDERSCORE;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Manages general.Course objects loaded from courses.csv.
 */
public class CourseManager {
    private Map<String, Course> courses;

    /**
     * Constructs a general.CourseManager.
     */
    public CourseManager() {
        courses = new HashMap<>();
    }

    /**
     * Loads courses from the given CSV file.
     *
     * @param filename The path to courses.csv.
     * @throws IOException If an I/O error occurs.
     */
    public void loadCourses(String filename) throws IOException {
        CSVParser parser = new CSVParser(filename);
        parser.readHeader();

        String[] record;
        while ((record = parser.readNextRecord()) != null) {
            String codeModule = record[parser.getColumnIndex(CODE_MODULE)];
            String codePresentation = record[parser.getColumnIndex(CODE_PRESENTATION)];
            int length = Integer.parseInt(record[parser.getColumnIndex(MODULE_PRES_LENGTH)]);

            Course course = new Course(codeModule, codePresentation, length);
            String key = codeModule + UNDERSCORE + codePresentation;
            courses.put(key, course);
        }

        parser.close();
    }

    /**
     * Retrieves a general.Course object based on module and presentation codes.
     *
     * @param codeModule        The module code.
     * @param codePresentation  The presentation code.
     * @return The general.Course object, or null if not found.
     */
    public Course getCourse(String codeModule, String codePresentation) {
        String key = codeModule + UNDERSCORE + codePresentation;
        return courses.get(key);
    }

    /**
     * Gets all loaded courses.
     *
     * @return A map of course keys to general.Course objects.
     */
    public Map<String, Course> getCourses() {
        return courses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CourseManager that = (CourseManager) o;
        return Objects.equals(courses, that.courses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courses);
    }

    @Override
    public String toString() {
        return "CourseManager{" +
                "courses=" + courses +
                '}';
    }
}