package general;

import java.util.Objects;

/**
 * Represents an entry from studentVle.csv.
 */
public class StudentVleEntry {
    private String codeModule;
    private String codePresentation;
    private int date;
    private int sumClicks;

    /**
     * Constructs a general.StudentVleEntry.
     *
     * @param codeModule       The code of the module.
     * @param codePresentation The code of the presentation.
     * @param date             The date relative to the start of the course.
     * @param sumClicks        The number of clicks.
     */
    public StudentVleEntry(String codeModule, String codePresentation, int date, int sumClicks) {
        this.codeModule = codeModule;
        this.codePresentation = codePresentation;
        this.date = date;
        this.sumClicks = sumClicks;
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
     * Gets the date relative to the start of the course.
     *
     * @return The date.
     */
    public int getDate() {
        return date;
    }

    /**
     * Gets the number of clicks.
     *
     * @return The sum of clicks.
     */
    public int getSumClicks() {
        return sumClicks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudentVleEntry that = (StudentVleEntry) o;
        return date == that.date &&
                sumClicks == that.sumClicks &&
                Objects.equals(codeModule, that.codeModule) &&
                Objects.equals(codePresentation, that.codePresentation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codeModule, codePresentation, date, sumClicks);
    }

    @Override
    public String toString() {
        return "StudentVleEntry{" +
                "codeModule='" + codeModule + '\'' +
                ", codePresentation='" + codePresentation + '\'' +
                ", date=" + date +
                ", sumClicks=" + sumClicks +
                '}';
    }
}