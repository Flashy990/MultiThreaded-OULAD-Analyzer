import general.Course;
import general.CourseManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class CourseManagerTest {
    private CourseManager courseManager;
    private CourseManager courseManager1;
    private CourseManager courseManager2;
    private CourseManager courseManager3;
    private CourseManager courseManagerNotEquals;

    @BeforeEach
    void setUp() throws IOException {
        courseManager = new CourseManager();
        courseManager1 = new CourseManager();
        courseManager2 = new CourseManager();
        courseManager3 = new CourseManager();
        courseManagerNotEquals = new CourseManager();
        courseManagerNotEquals.loadCourses("input/courses.csv");
    }

    @Test
    void testLoadCourses() throws IOException {
        courseManager.loadCourses("input/courses.csv");
        Course course = courseManager.getCourse("AAA", "2014J");
        assertNotNull(course);
        assertEquals("AAA", course.getCodeModule());
        assertEquals("2014J", course.getCodePresentation());
        assertEquals(269, course.getModulePresentationLength());
    }

    @Test
    void testLoadCoursesWithInvalidFile() {
        Exception exception = assertThrows(IOException.class, () -> {
            courseManager.loadCourses("test/nonexistent.csv");
        });
        assertFalse(exception.getMessage().contains("No such file or directory"));
    }

    @Test
    void testGetNonExistentCourse() throws IOException {
        courseManager.loadCourses("input/courses.csv");
        Course course = courseManager.getCourse("NON_EXISTENT", "2014J");
        assertNull(course);
    }


    @Test
    void testEqualsBasic() {
        assertTrue(courseManager1.equals(courseManager2));
        assertFalse(courseManager1.equals(courseManagerNotEquals));
    }

    @Test
    void testEqualsNull() {
        assertFalse(courseManager1.equals(null));
    }

    @Test
    void testEquals1() {
        assertTrue(courseManager1.equals(courseManager1));
    }

    @Test
    void testEquals2() {
        assertTrue(courseManager1.equals(courseManager2));
        assertTrue(courseManager2.equals(courseManager1));
    }

    @Test
    void testEquals3() {
        assertTrue(courseManager1.equals(courseManager2));
        assertTrue(courseManager2.equals(courseManager3));
        assertTrue(courseManager3.equals(courseManager1));
    }

    @Test
    void testDifferentClassObject() {
        assertFalse(courseManager1.equals(new Object()));
    }

    @Test
    void testDifferentArgs() {
        assertFalse(courseManager1.equals(courseManagerNotEquals));
    }

    @Test
    void testHashCodeBasic() {
        assertEquals(courseManager1.hashCode(), courseManager2.hashCode());
        assertNotEquals(courseManager1.hashCode(), courseManagerNotEquals.hashCode());
    }

    @Test
    void testToString() {
        String expected = "CourseManager{courses={}}";
        assertEquals(expected, courseManager.toString());
    }
}