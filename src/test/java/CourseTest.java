import general.Course;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class CourseTest {
    @Test
    void testCourseCreation() {
        Course course = new Course("AAA", "2014J", 269);
        assertEquals("AAA", course.getCodeModule());
        assertEquals("2014J", course.getCodePresentation());
        assertEquals(269, course.getModulePresentationLength());
    }

    @Test
    void testEqualsAndHashCode() {
        Course course1 = new Course("AAA", "2014J", 269);
        Course course2 = new Course("AAA", "2014J", 269);
        Course course3 = new Course("BBB", "2014J", 269);

        assertEquals(course1, course2);
        assertEquals(course1.hashCode(), course2.hashCode());
        assertNotEquals(course1, course3);
    }

    @Test
    void testToString() {
        Course course = new Course("AAA", "2014J", 269);
        String expected = "Course{codeModule='AAA', codePresentation='2014J', modulePresentationLength=269}";
        assertEquals(expected, course.toString());
    }
}