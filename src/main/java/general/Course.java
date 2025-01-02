package general;

import java.util.Objects;

/**
 * Represents a course offering with its module, presentation, and length.
 */
public class Course {
    private String codeModule;
    private String codePresentation;
    private int modulePresentationLength;

    /**
     * Constructs a general.Course object.
     *
     * @param codeModule              The code of the module.
     * @param codePresentation        The code of the presentation.
     * @param modulePresentationLength The length of the module presentation.
     */
    public Course(String codeModule, String codePresentation, int modulePresentationLength) {
        this.codeModule = codeModule;
        this.codePresentation = codePresentation;
        this.modulePresentationLength = modulePresentationLength;
    }

    /**
     * Gets the code of the module.
     *
     * @return The module code.
     */
    public String getCodeModule() {
        return codeModule;
    }

    /**
     * Gets the code of the presentation.
     *
     * @return The presentation code.
     */
    public String getCodePresentation() {
        return codePresentation;
    }

    /**
     * Gets the length of the module presentation.
     *
     * @return The presentation length.
     */
    public int getModulePresentationLength() {
        return modulePresentationLength;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;
        return modulePresentationLength == course.modulePresentationLength &&
                Objects.equals(codeModule, course.codeModule) &&
                Objects.equals(codePresentation, course.codePresentation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codeModule, codePresentation, modulePresentationLength);
    }

    @Override
    public String toString() {
        return "Course{" +
                "codeModule='" + codeModule + '\'' +
                ", codePresentation='" + codePresentation + '\'' +
                ", modulePresentationLength=" + modulePresentationLength +
                '}';
    }
}