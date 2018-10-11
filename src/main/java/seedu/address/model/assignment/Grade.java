package seedu.address.model.assignment;

import seedu.address.model.student.StudentId;

/**
 * Model for Grading details.
 */
public class Grade {
    private final String tutorialGroupId;
    private final String assignmentId;
    private final StudentId studentId;
    private final Double assignmentMarks;

    public Grade(String tutorialGroupId, String assignmentId, StudentId studentId, Double assignmentMarks) {
        this.tutorialGroupId = tutorialGroupId;
        this.assignmentId = assignmentId;
        this.studentId = studentId;
        this.assignmentMarks = assignmentMarks;
    }

    public String getTgId() {
        return tutorialGroupId;
    }

    public String getAsId() {
        return assignmentId;
    }

    public StudentId getStId() {
        return studentId;
    }

    public Double getMarks() {
        return assignmentMarks;
    }
}
